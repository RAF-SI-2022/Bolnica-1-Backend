package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;

@Component
@AllArgsConstructor
public class AnalysisParameterMapper {

    private final LabAnalysisMapper labAnalysisMapper;
    private final ParameterMapper parameterMapper;

    public AnalysisParameterDto toDto(AnalysisParameter entity) {
        AnalysisParameterDto dto = new AnalysisParameterDto();
        dto.setId(entity.getId());
        dto.setParameter(parameterMapper.toDto(entity.getParameter()));
        dto.setLabAnalysis(labAnalysisMapper.toDto(entity.getLabAnalysis()));
        return dto;
    }

    public AnalysisParameter toEntity(AnalysisParameterDto dto){
        AnalysisParameter entity=new AnalysisParameter();

        entity.setId(dto.getId());
        entity.setParameter(parameterMapper.toEntity(dto.getParameter()));
        entity.setLabAnalysis(labAnalysisMapper.toEntity(dto.getLabAnalysis()));

        return entity;
    }
}
