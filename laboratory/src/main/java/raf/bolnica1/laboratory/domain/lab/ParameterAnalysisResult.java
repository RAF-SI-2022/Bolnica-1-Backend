package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "parameter_analysis_result")
public class ParameterAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @ManyToOne
    private LabWorkOrder workOrder;
    @NotEmpty
    @ManyToOne
    private AnalysisParameter analysisParameter;
    private String result = null;
    private Timestamp dateTime = null;
    private Integer lbzBiochemist = null;
}
