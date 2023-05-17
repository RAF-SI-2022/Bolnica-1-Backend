package raf.bolnica1.laboratory.dto.lab.prescription;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabResultDto implements Serializable {

    private String result;
    private String parameterName;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
    private String analysisName;

}
