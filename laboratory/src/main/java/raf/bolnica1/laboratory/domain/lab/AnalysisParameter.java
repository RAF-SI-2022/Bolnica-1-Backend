package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "analysis_parameter")
public class AnalysisParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotEmpty
    private LabAnalysis labAnalysis;
    @ManyToOne
    @NotEmpty
    private Parameter parameter;
}
