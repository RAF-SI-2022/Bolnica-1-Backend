package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MedicalRecordCreateDto {
    private String lbp;
    private Date registrationDate;
}
