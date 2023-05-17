package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class VaccinationDataDto implements Serializable {

    private String vaccinationName;
    private Date vaccinationDate;
}
