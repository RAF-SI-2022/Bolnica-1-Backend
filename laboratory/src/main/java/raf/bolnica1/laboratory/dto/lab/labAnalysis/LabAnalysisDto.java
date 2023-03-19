package raf.bolnica1.laboratory.dto.lab.labAnalysis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabAnalysisDto {
    private String analysisName;
    private String abbreviation;
}
