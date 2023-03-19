package raf.bolnica1.laboratory.dto.lab.analysisParameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisParameterDto {

    private LabAnalysisDto labAnalysis;
    private ParameterDto parameter;

}
