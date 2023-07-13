package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LabAnalysisMapper {

    public LabAnalysisDto toDto(LabAnalysis entity) {
        LabAnalysisDto dto = new LabAnalysisDto();
        dto.setId(entity.getId());
        dto.setAnalysisName(entity.getAnalysisName());
        dto.setAbbreviation(entity.getAbbreviation());
        dto.setCovid(entity.isCovid());
        return dto;
    }

    public List<LabAnalysisDto> toDto(List<LabAnalysis> entity){

        List<LabAnalysisDto>dto=new ArrayList<>();

        for(LabAnalysis labAnalysis:entity)
            dto.add(toDto(labAnalysis));

        return dto;
    }

    public LabAnalysis toEntity(LabAnalysisDto dto){

        LabAnalysis entity=new LabAnalysis();

        entity.setAnalysisName(dto.getAnalysisName());
        entity.setAbbreviation(dto.getAbbreviation());
        entity.setCovid(dto.isCovid());
        entity.setId(dto.getId());

        return entity;
    }

}
