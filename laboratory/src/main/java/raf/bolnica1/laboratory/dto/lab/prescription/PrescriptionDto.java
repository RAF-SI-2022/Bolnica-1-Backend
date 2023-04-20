package raf.bolnica1.laboratory.dto.lab.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;

import java.sql.Timestamp;

@Getter
@Setter
public class PrescriptionDto {
    private PrescriptionType type;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;
    private String requestedTests;
    private String comment;
    private String referralDiagnosis;
    private String referralReason;
}
