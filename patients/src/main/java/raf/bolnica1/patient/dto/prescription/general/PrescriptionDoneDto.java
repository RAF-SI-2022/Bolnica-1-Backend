package raf.bolnica1.patient.dto.prescription.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;

import java.sql.Date;
@Getter
@Setter
public class PrescriptionDoneDto {
    private Long id;
    private String type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private String doctorLbz;
    private Date date;
    private PrescriptionStatus prescriptionStatus;
}
