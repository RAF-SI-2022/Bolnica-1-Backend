package raf.bolnica1.patient.dto.prescription.lab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionAnalysisDataDto {
    private String analysisName;
    private List<String> parametersName;
}
