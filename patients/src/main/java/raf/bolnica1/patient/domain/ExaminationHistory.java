package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "examination_history")
@Getter
@Setter
public class ExaminationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date examDate;
    private String lbz;
    private boolean confidential;
    private String objectiveFinding;
    private String advice;
    @ManyToOne
    private DiagnosisCode diagnosisCode;
    @ManyToOne
    private Anamnesis anamnesis;
    @ManyToOne
    private Therapy therapy;
    @ManyToOne
    private MedicalRecord medicalRecord;
}
