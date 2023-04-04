package raf.bolnica1.patient.dto.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabResultDto {

    private String result;
    private String parameterName;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
    private String analysisName;

}
