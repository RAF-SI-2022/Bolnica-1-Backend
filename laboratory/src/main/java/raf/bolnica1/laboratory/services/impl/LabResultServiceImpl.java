package raf.bolnica1.laboratory.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.LabResultDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.listener.helper.MessageHelper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.LabResultService;
import raf.bolnica1.laboratory.services.LabWorkOrdersService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabResultServiceImpl implements LabResultService {

    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private final LabWorkOrderRepository labWorkOrderRepository;
    private final LabWorkOrdersService labWorkOrdersService;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;

    public LabResultServiceImpl(ParameterAnalysisResultRepository parameterAnalysisResultRepository,
                                LabWorkOrderRepository labWorkOrderRepository,
                                LabWorkOrdersService labWorkOrdersService,
                                JmsTemplate jmsTemplate,
                                @Value("${destination.send.completed}") String destination,
                                MessageHelper messageHelper) {
        this.parameterAnalysisResultRepository = parameterAnalysisResultRepository;
        this.labWorkOrderRepository = labWorkOrderRepository;
        this.labWorkOrdersService = labWorkOrdersService;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageHelper = messageHelper;
    }

    @Override
    public MessageDto updateResults(ResultUpdateDto resultUpdateDto) {

        ///ParameterAnalysisResult parameterAnalysisResult = parameterAnalysisResultRepository.findParameterAnalysisResultByLabWorkOrderIdAndAnalysisParameterId(resultUpdateDto.getLabWorkOrderId(), resultUpdateDto.getAnalysisParameterId());
        ParameterAnalysisResult parameterAnalysisResult = parameterAnalysisResultRepository.findById(resultUpdateDto.getAnalysisParameterId()).orElse(null);

        if (parameterAnalysisResult == null)
            throw new RuntimeException();

        parameterAnalysisResult.setResult(resultUpdateDto.getResult());
        parameterAnalysisResult.setDateTime(resultUpdateDto.getDateTime());
        parameterAnalysisResult.setBiochemistLbz(resultUpdateDto.getBiochemistLbz());

        parameterAnalysisResult = parameterAnalysisResultRepository.save(parameterAnalysisResult);

        MessageDto statusMessage = new MessageDto("");
        if (parameterAnalysisResultRepository.countParameterAnalysisResultWithNullResultByLabWorkOrderId(resultUpdateDto.getLabWorkOrderId()) == 0) {
            statusMessage = labWorkOrdersService.updateLabWorkOrderStatus(resultUpdateDto.getLabWorkOrderId(), OrderStatus.OBRADJEN);

        }

        return new MessageDto(statusMessage + "Succesfully added result to LabWorkOrder with ID " + parameterAnalysisResult.getLabWorkOrder().getId() + ". ");
    }

    @Override
    public MessageDto commitResults(Long workOrderId) {
        LabWorkOrder labWorkOrder = labWorkOrderRepository.findLabWorkOrderById(workOrderId);
        if (labWorkOrder.getStatus().equals(OrderStatus.OBRADJEN)) {
            labWorkOrder.getPrescription().setStatus(PrescriptionStatus.REALIZOVAN);
            PrescriptionCreateDto prescriptionCreateDto = new PrescriptionCreateDto();
            prescriptionCreateDto.setComment(labWorkOrder.getPrescription().getComment());
            prescriptionCreateDto.setDate(new Date(System.currentTimeMillis()));
            prescriptionCreateDto.setDepartmentFromId(labWorkOrder.getPrescription().getDepartmentFromId());
            prescriptionCreateDto.setDepartmentToId(labWorkOrder.getPrescription().getDepartmentToId());
            prescriptionCreateDto.setDoctorLbz(labWorkOrder.getPrescription().getDoctorLbz());
            prescriptionCreateDto.setLbp(labWorkOrder.getPrescription().getLbp());
            prescriptionCreateDto.setType("LABORATORIJA");
            prescriptionCreateDto.setLabResultDtoList(new ArrayList<>());
            List<ParameterAnalysisResult> parameterAnalysisResultList = parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrder.getId());
            for (ParameterAnalysisResult parameterAnalysisResult : parameterAnalysisResultList) {
                LabResultDto labResultDto = new LabResultDto();
                labResultDto.setResult(parameterAnalysisResult.getResult());
                labResultDto.setAnalysisName(parameterAnalysisResult.getAnalysisParameter().getLabAnalysis().getAnalysisName());
                labResultDto.setParameterName(parameterAnalysisResult.getAnalysisParameter().getParameter().getParameterName());
                labResultDto.setLowerLimit(parameterAnalysisResult.getAnalysisParameter().getParameter().getLowerLimit());
                labResultDto.setUpperLimit(parameterAnalysisResult.getAnalysisParameter().getParameter().getUpperLimit());
                labResultDto.setUnitOfMeasure(parameterAnalysisResult.getAnalysisParameter().getParameter().getUnitOfMeasure());
                prescriptionCreateDto.getLabResultDtoList().add(labResultDto);
            }
            jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(prescriptionCreateDto));
        }
        return null;
    }
}
