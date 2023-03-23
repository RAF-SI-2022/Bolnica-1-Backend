package raf.bolnica1.patient.dto.prescription;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionAnalysisDataDto {
    private String analysisName;
    private List<String> parametersName;
}
