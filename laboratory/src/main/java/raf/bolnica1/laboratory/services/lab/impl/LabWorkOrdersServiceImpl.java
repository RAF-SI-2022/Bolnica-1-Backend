package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
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
import raf.bolnica1.laboratory.dto.lab.workOrder.*;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.exceptions.workOrder.CantVerifyLabWorkOrderException;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.exceptions.workOrder.NoParameterAnalysisResultFound;
import raf.bolnica1.laboratory.exceptions.workOrder.NotAuthenticatedException;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public MessageDto createWorkOrder(Long prescriptionId) {

        String lbz=getLbzFromAuthentication();

        Prescription prescription= prescriptionRepository.findPrescriptionById(prescriptionId);

        LabWorkOrder labWorkOrder=new LabWorkOrder();

        labWorkOrder.setPrescription(prescription);
        labWorkOrder.setLbp(prescription.getLbp());
        labWorkOrder.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
        labWorkOrder.setTechnicianLbz(lbz);
        labWorkOrder=labWorkOrderRepository.save(labWorkOrder);




        return new MessageDto(String.format("Uspesno napravljen laboratorijski radni nalog na osonovu uputa %lld",prescriptionId));
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
    public Object findWorkOrdersByLab(Object object) {
        return null;
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

}
