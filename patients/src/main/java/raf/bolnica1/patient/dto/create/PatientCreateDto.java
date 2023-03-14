package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PatientCreateDto extends PatientGeneralDto{
    private Date registerDate;
}
