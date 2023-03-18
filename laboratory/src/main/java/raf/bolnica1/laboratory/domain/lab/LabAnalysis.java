package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "lab_analysis", indexes = {@Index(columnList = "abbreviation")})
public class LabAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String analysisName;
    @NotBlank
    private String abbreviation;
}
