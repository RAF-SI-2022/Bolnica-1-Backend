package raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParameterAnalysisResultDto implements Serializable {
    private List<AnalysisParameterDto> analysesParameters;
    private String result;
    private Timestamp dateTime;
    private String biochemistLbz;
}
