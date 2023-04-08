package raf.bolnica1.infirmary.domain;
import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name="prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doctorLbz;
    private Long idDepartmentFrom;
    private Long idDepartmentTo;
    private String lbp;
    private Timestamp creation;
    @Enumerated(EnumType.STRING)
    private PrescriptionType prescriptionType;
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus prescriptionStatus;
    private String referralDiagnosis;
    private String referralReason;

}
