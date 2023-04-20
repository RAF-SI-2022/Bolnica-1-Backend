package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.workOrder.ParameterAnalysisResultWithDetailsDto;

@Component
@AllArgsConstructor
public class ParameterAnalysisResultWithDetailsMapper {

    private final LabAnalysisMapper labAnalysisMapper;
    private final ParameterMapper parameterMapper;

    public ParameterAnalysisResultWithDetailsDto toDto(ParameterAnalysisResult entity) {
        ParameterAnalysisResultWithDetailsDto dto = new ParameterAnalysisResultWithDetailsDto();
        dto.setId(entity.getId());
        dto.setResult(entity.getResult());
        dto.setDateTime(entity.getDateTime());
        dto.setLbzBiochemist(entity.getBiochemistLbz());
        dto.setLabAnalysis(labAnalysisMapper.toDto(entity.getAnalysisParameter().getLabAnalysis()));
        dto.setParameter(parameterMapper.toDto(entity.getAnalysisParameter().getParameter()));
        return dto;
    }
}
