package raf.bolnica1.infirmary.domain;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idDoctor;
    private Long idDepartmentFrom;
    private Long getIdDepartmentTo;
    private String lbp;
    private Timestamp creation;
    @Enumerated(EnumType.STRING)
    private PrescriptionType prescriptionType;
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus prescriptionStatus;
    private String requestedAnalysis;
    private String comment;
    private String referralDiagnosis;
    private String getReferralReason;

}
