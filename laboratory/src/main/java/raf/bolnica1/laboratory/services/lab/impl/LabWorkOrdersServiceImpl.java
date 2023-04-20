package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderWithAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.exceptions.workOrder.CantVerifyLabWorkOrderException;
import raf.bolnica1.laboratory.exceptions.workOrder.DateParseException;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.exceptions.workOrder.NoParameterAnalysisResultFound;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LabWorkOrdersServiceImpl implements LabWorkOrdersService {

    private AuthenticationUtils authenticationUtils;
    private final LabWorkOrderRepository labWorkOrderRepository;
    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private final LabWorkOrderWithAnalysisMapper labWorkOrderWithAnalysisMapper;
    private final ParameterAnalysisResultMapper parameterAnalysisResultMapper;
    private final LabWorkOrderMapper labWorkOrderMapper;
    private final PrescriptionService prescriptionService;
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public LabWorkOrder createWorkOrder(Prescription prescription) {

        LabWorkOrder labWorkOrder = new LabWorkOrder();

        labWorkOrder.setPrescription(prescription);
        labWorkOrder.setLbp(prescription.getLbp());
        labWorkOrder.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
        labWorkOrder = labWorkOrderRepository.save(labWorkOrder);

        return labWorkOrder;
    }

    @Override
    public MessageDto registerPatient(String lbp) {
        List<Prescription> prescriptions = prescriptionRepository.findPrescriptionsByLbp(lbp);
        for (Prescription prescription : prescriptions) {
            LabWorkOrder labWorkOrder = labWorkOrderRepository.findByPrescription(prescription.getId()).orElse(null);
            if (labWorkOrder != null) {
                labWorkOrder.setStatus(OrderStatus.U_OBRADI);
                labWorkOrder.setTechnicianLbz(authenticationUtils.getLbzFromAuthentication());
                labWorkOrderRepository.save(labWorkOrder);
            }
        }
        return new MessageDto("Uspesno izvrseno");
    }


    @Override
    public LabWorkOrderDto updateWorkOrder(Object dto) {
        return null;
    }

    @Override
    public LabWorkOrderMessageDto verifyWorkOrder(Long id) {
        String lbz = authenticationUtils.getLbzFromAuthentication();

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findById(id).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No laboratory work order with id %s", id))
        );
        // check for results of parameter analysis
        ParameterAnalysisResult par = parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(id).stream().filter(result -> result.getResult() == null).findAny().orElse(null);
        if (par != null) {
            throw new CantVerifyLabWorkOrderException("Cant verify lab work order, as not all results have been entered.");
        }

        //labWorkOrder.setTechnicianLbz(lbz); // da li ovde setovati ili
        labWorkOrder.setBiochemistLbz(lbz); // ili ovde
        labWorkOrder.setStatus(OrderStatus.OBRADJEN);

        labWorkOrderRepository.save(labWorkOrder);

        // uput, too coupled?
        prescriptionService.updatePrescriptionStatus(labWorkOrder.getPrescription().getId(), PrescriptionStatus.REALIZOVAN);
        return new LabWorkOrderMessageDto("Verifikovan radni nalog", labWorkOrderMapper.toDto(labWorkOrder));
    }

    @Override
    @Transactional
    public UpdateParameterAnalysisResultMessageDto updateAnalysisParameters(Long workOrderId, Long analysisParameterId, String result) {
        String lbz = authenticationUtils.getLbzFromAuthentication();

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findById(workOrderId).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No laboratory work order with id %s", workOrderId))
        );

        ParameterAnalysisResult par = parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameterId).orElseThrow(() ->
                new NoParameterAnalysisResultFound(String.format("No parameter analysis result found for lab work order id %s and analysis param id %s", workOrderId, analysisParameterId)));

        if (labWorkOrder.getStatus() == OrderStatus.NEOBRADJEN) {
            labWorkOrder.setStatus(OrderStatus.U_OBRADI);
        }

        labWorkOrderRepository.save(labWorkOrder);

        par.setBiochemistLbz(lbz);
        par.setDateTime(new Timestamp(System.currentTimeMillis()));
        par.setResult(result);

        parameterAnalysisResultRepository.save(par);

        return parameterAnalysisResultMapper.toDto(par);
    }

    /// vraca samo obradjene
    public Page<LabWorkOrder> workOrdersHistory(String lbp, Date fromDate, Date toDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if(toDate!=null)toDate=new Date(toDate.getTime()+24*60*60*1000);
        return labWorkOrderRepository.workOrdersHistory(pageable, lbp, fromDate, toDate);
    }

    @Override
    public Page<LabWorkOrder> findWorkOrdersByLab(String lbp, Date fromDate, Date toDate, OrderStatus status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if(toDate!=null)toDate=new Date(toDate.getTime()+24*60*60*1000);
        return labWorkOrderRepository.findWorkOrdersByLab(pageable, lbp, fromDate, toDate, status);
    }

    @Override
    public LabWorkOrderWithAnalysisDto findParameterAnalysisResultsForWorkOrder(Long id) {
        List<OrderStatus> allowedStatuses = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_MED_BIOHEMICAR") || authority.getAuthority().equals("ROLE_SPEC_MED_BIOHEMIJE"))) {
            allowedStatuses.add(OrderStatus.NEOBRADJEN);
            allowedStatuses.add(OrderStatus.U_OBRADI);
        }
        allowedStatuses.add(OrderStatus.OBRADJEN);

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findById(id).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No laboratory work order with id %s", id))
        );

        List<ParameterAnalysisResult> parameterAnalysisResults = parameterAnalysisResultRepository.findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(id, allowedStatuses);

        return labWorkOrderWithAnalysisMapper.toDto(labWorkOrder, parameterAnalysisResults);
    }

    @Override
    public void deleteWorkOrder(LabWorkOrder labWorkOrder) {
        parameterAnalysisResultRepository.deleteAll(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrder.getId()));
        labWorkOrderRepository.delete(labWorkOrder);
           parameterAnalysisResultRepository.deleteAll(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrder.getId()));
           labWorkOrderRepository.delete(labWorkOrder);
    }

    @Override
    public MessageDto updateLabWorkOrderStatus(Long id,OrderStatus orderStatus) {
        LabWorkOrder labWorkOrder=labWorkOrderRepository.findLabWorkOrderById(id);
        labWorkOrder.setStatus(orderStatus);
        labWorkOrder=labWorkOrderRepository.save(labWorkOrder);
        return new MessageDto("LabWorkOrder with ID "+labWorkOrder.getId()+" changed OrderStatus to "+orderStatus.toString()+". ");
    }

    /**
    public Date lastSecondOfTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
     */
}
