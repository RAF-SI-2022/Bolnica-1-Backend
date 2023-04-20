package raf.bolnica1.laboratory.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {
    private String parameterName;
    private String result;
    private Double lowerLimit;
    private Double upperLimit;
}
