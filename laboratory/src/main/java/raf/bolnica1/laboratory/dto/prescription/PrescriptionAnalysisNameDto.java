package raf.bolnica1.laboratory.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionAnalysisNameDto {
    private String analysisName;
    private List<ParameterDto> parameters;
}