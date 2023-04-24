package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;

@Component
@AllArgsConstructor
public class ParameterAnalysisResultMapper {

    private final AnalysisParameterMapper analysisParameterMapper;
    private final LabWorkOrderMapper labWorkOrderMapper;

    public UpdateParameterAnalysisResultMessageDto toDto(ParameterAnalysisResult entity) {
        UpdateParameterAnalysisResultMessageDto dto = new UpdateParameterAnalysisResultMessageDto();
        dto.setResult(entity.getResult());
        dto.setDateTime(entity.getDateTime());
        dto.setBiochemistLbz(entity.getBiochemistLbz());
        dto.setWorkOrderDto(labWorkOrderMapper.toDto(entity.getLabWorkOrder()));
        dto.setAnalysisParameterDto(analysisParameterMapper.toDto(entity.getAnalysisParameter()));
        return dto;
    }
}
