package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ParameterValueType;
import raf.bolnica1.laboratory.domain.constants.validation.EnumNotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "parameter", indexes = {@Index(columnList = "parameterName")})
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String parameterName;
    @EnumNotBlank
    @Enumerated(EnumType.STRING)
    private ParameterValueType type;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
}
