package raf.bolnica1.patient.domain.prescription;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lab_results")
@Getter
@Setter
public class LabResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String result;
    private String parameterName;
    private String unitOfMeasure;
    private Double lowerLimit;
    private Double upperLimit;
    private String analysisName;
    @ManyToOne
    private LabPrescription labPrescription;
}
