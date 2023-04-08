package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class VaccinationDto {

    private String name;
    private VaccinationType type;
    private String description;
    private String manufacturer;
    private Date vaccinationDate;

}
