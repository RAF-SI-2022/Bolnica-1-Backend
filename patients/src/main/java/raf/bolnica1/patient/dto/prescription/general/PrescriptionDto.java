package raf.bolnica1.patient.dto.prescription.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.domain.constants.PrescriptionType;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PrescriptionDto {
    private Long id;
    private PrescriptionType type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private String doctorLbz;
    private String comment;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;
}
