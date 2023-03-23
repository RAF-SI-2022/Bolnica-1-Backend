package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.TreatmentResult;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Date startDate;
    private Date endDate;
    private boolean confidential;
    @Enumerated(EnumType.STRING)
    private TreatmentResult treatmentResult;
    private String currStateDesc;
    private Date validFrom;
    private Date validTo = Date.valueOf("9999-12-31");
    private boolean valid;
    @ManyToOne
    private MedicalRecord medicalRecord;
    @ManyToOne
    private DiagnosisCode diagnosisCode;

}
