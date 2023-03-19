package raf.bolnica1.laboratory.dto.lab.workOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParameterAnalysisResultWithDetailsDto {
    private Long id;
    private String result;
    private Timestamp dateTime;
    private String lbzBiochemist;
    private LabAnalysisDto labAnalysis;
    private ParameterDto parameter;
}
