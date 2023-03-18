package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "prescription", indexes = {@Index(columnList = "doctorId, departmentFromId")})
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private PrescriptionType type;
    @NotEmpty
    private Long doctorId;
    @NotEmpty
    private Long departmentFromId;
    @NotEmpty
    private Long departmentToId;
    @NotEmpty
    private Long lbp;
    @NotEmpty
    private Timestamp creationDateTime;
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status = PrescriptionStatus.NEREALIZOVAN;
    private String requestedTests;
    private String comment;
    private String referralDiagnosis;
    private String referralReason;
}
