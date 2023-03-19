package raf.bolnica1.laboratory.dto.lab.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ParameterValueType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto {
    private String parameterName;
    private ParameterValueType type;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
}
