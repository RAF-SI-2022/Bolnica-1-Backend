package raf.bolnica1.patient.dto.prescription.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.domain.constants.PrescriptionType;

import java.sql.Timestamp;

@Getter
@Setter
public class PrescriptionSendDto {
    private PrescriptionType type;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;
}
