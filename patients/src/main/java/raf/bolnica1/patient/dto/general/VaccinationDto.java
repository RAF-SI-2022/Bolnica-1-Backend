package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.VaccinationType;

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
