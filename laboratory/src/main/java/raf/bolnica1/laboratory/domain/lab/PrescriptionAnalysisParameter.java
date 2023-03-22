package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "prescription_analysis_parameter")
public class PrescriptionAnalysisParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Prescription prescription;
    @ManyToOne
    private AnalysisParameter analysisParameter;

}
