package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class VaccinationDto implements Serializable {

    private String name;
    private VaccinationType type;
    private String description;
    private String manufacturer;
    private Date vaccinationDate;

}
