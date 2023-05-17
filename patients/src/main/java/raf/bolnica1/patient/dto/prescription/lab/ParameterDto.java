package raf.bolnica1.patient.dto.prescription.lab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto implements Serializable {
    private String parameterName;
    private String result;
    private Double lowerLimit;
    private Double upperLimit;
}
