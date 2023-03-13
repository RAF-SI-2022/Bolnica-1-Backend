package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class VaccinationDto {

    private String name;
    private String type;
    private String description;
    private String manufacturer;
    private Date vaccinationDate;

}
