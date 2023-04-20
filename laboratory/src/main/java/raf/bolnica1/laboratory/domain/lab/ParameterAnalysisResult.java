package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "parameter_analysis_result")
public class ParameterAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private LabWorkOrder labWorkOrder;
    @ManyToOne
    private AnalysisParameter analysisParameter;
    private String result = null;
    private Timestamp dateTime = null;
    private String biochemistLbz = null;
}
