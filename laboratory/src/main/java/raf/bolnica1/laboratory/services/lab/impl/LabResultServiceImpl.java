package raf.bolnica1.laboratory.services.lab.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.listener.helper.MessageHelper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.lab.LabResultService;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;

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
    }

    @Override
    public MessageDto updateResults(ResultUpdateDto resultUpdateDto) {

        ParameterAnalysisResult parameterAnalysisResult= parameterAnalysisResultRepository.findParameterAnalysisResultByLabWorkOrderIdAndAnalysisParameterId(resultUpdateDto.getLabWorkOrderId(), resultUpdateDto.getAnalysisParameterId());

        if(parameterAnalysisResult==null)
            throw new RuntimeException();

        parameterAnalysisResult.setResult(resultUpdateDto.getResult());
        parameterAnalysisResult.setDateTime(resultUpdateDto.getDateTime());
        parameterAnalysisResult.setBiochemistLbz(resultUpdateDto.getBiochemistLbz());

        parameterAnalysisResult=parameterAnalysisResultRepository.save(parameterAnalysisResult);

        MessageDto statusMessage=new MessageDto("");
        if(parameterAnalysisResultRepository.countParameterAnalysisResultWithNullResultByLabWorkOrderId(resultUpdateDto.getLabWorkOrderId())==0){
            statusMessage=labWorkOrdersService.updateLabWorkOrderStatus(resultUpdateDto.getLabWorkOrderId(), OrderStatus.OBRADJEN);

        }

        return new MessageDto(statusMessage+"Succesfully added result to LabWorkOrder with ID "+parameterAnalysisResult.getLabWorkOrder().getId()+". ");
    }

    @Override
    public MessageDto commitResults(Long workOrderId) {
        LabWorkOrder labWorkOrder = labWorkOrderRepository.findLabWorkOrderById(workOrderId);
        if(labWorkOrder.getStatus().equals(OrderStatus.OBRADJEN)){
            labWorkOrder.getPrescription().setStatus(PrescriptionStatus.REALIZOVAN);
            /// jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage());
        }
        return null;
    }
}
