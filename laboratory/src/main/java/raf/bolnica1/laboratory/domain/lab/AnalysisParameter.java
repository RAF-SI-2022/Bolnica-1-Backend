package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "analysis_parameter")
public class AnalysisParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private LabAnalysis labAnalysis;
    @ManyToOne
    private Parameter parameter;
}
