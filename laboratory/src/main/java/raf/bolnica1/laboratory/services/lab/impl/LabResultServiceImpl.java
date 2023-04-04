package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.lab.LabResultService;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;

@Service
@AllArgsConstructor
public class LabResultServiceImpl implements LabResultService {

    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private final LabWorkOrderRepository labWorkOrderRepository;
    private final LabWorkOrdersService labWorkOrdersService;


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
}
