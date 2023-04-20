package raf.bolnica1.patient.dto.prescription.general;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PrescriptionUpdateDto {
    private Long id;
    private Long departmentFromId;
    private Long departmentToId;
    private Timestamp creationDateTime;
}
