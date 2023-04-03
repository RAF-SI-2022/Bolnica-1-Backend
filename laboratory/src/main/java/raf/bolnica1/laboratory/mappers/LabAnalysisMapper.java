package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;

@Component
@AllArgsConstructor
public class LabAnalysisMapper {

    public LabAnalysisDto toDto(LabAnalysis entity) {
        LabAnalysisDto dto = new LabAnalysisDto();
        dto.setId(entity.getId());
        dto.setAnalysisName(entity.getAnalysisName());
        dto.setAbbreviation(entity.getAbbreviation());
        return dto;
    }

}
