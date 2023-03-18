package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ParameterValueType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "parameter", indexes = {@Index(columnList = "parameterName")})
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String parameterName;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private ParameterValueType type;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
}
