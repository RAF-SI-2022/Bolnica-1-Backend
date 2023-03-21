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
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.*;
import raf.bolnica1.laboratory.exceptions.workOrder.*;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LabWorkOrdersServiceImpl implements LabWorkOrdersService {

    private final LabWorkOrderRepository labWorkOrderRepository;
    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private final LabWorkOrderWithAnalysisMapper labWorkOrderWithAnalysisMapper;
    private final ParameterAnalysisResultMapper parameterAnalysisResultMapper;
    private final LabWorkOrderMapper labWorkOrderMapper;
    private final PrescriptionService prescriptionService;

    @Override
    public LabWorkOrderDto createWorkOrder(Object dto) {
        return null;
    }

    @Override
    public LabWorkOrderDto updateWorkOrder(Object dto) {
        return null;
    }

    @Override
    public LabWorkOrderMessageDto verifyWorkOrder(Long id) {
        String lbz = getLbzFromAuthentication();

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findById(id).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No laboratory work order with id %s", id))
        );
        // check for results of parameter analysis
        ParameterAnalysisResult par = parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(id).stream().filter(result -> result.getResult() == null).findAny().orElse(null);
        if(par != null){
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
        String lbz = getLbzFromAuthentication();

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findById(workOrderId).orElseThrow(() ->
                new LabWorkOrderNotFoundException(String.format("No laboratory work order with id %s", workOrderId))
        );

        ParameterAnalysisResult par = parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameterId).orElseThrow(() ->
                new NoParameterAnalysisResultFound(String.format("No parameter analysis result found for lab work order id %s and analysis param id %s", workOrderId, analysisParameterId)));

        if(labWorkOrder.getStatus() == OrderStatus.NEOBRADJEN){
            labWorkOrder.setStatus(OrderStatus.U_OBRADI);
        }

        labWorkOrderRepository.save(labWorkOrder);

        par.setBiochemistLbz(lbz);
        par.setDateTime(new Timestamp(System.currentTimeMillis()));
        par.setResult(result);

        parameterAnalysisResultRepository.save(par);

        return parameterAnalysisResultMapper.toDto(par);
    }


    @Override
    public Object workOrdersHistory(Object object) {
        return null;
    }

    @Override
    public Page<LabWorkOrder> findWorkOrdersByLab(String lbp, String fromDate, String toDate, OrderStatus status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Date from = null;
        Date to = null;

        if(fromDate != null && toDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                from = dateFormat.parse(fromDate);
                to = lastSecondOfTheDay(dateFormat.parse(toDate));
            } catch (Exception e) {
                throw new DateParseException(String.format("Given date is not parsed correctly: %s or %s", fromDate, toDate));
            }
        }
        return labWorkOrderRepository.findWorkOrdersByLab(pageable, lbp, from, to, status);
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

        List<ParameterAnalysisResult> parameterAnalysisResults =  parameterAnalysisResultRepository.findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(id, allowedStatuses);

        return labWorkOrderWithAnalysisMapper.toDto(labWorkOrder, parameterAnalysisResults);
    }

    private String getLbzFromAuthentication(){
        String lbz = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            lbz = (String) authentication.getPrincipal();
        }
        // temp linija, treba malo refaktorisati
        if(lbz == null) throw new NotAuthenticatedException("Something went wrong.");
        return lbz;
    }

    private Date lastSecondOfTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
}
