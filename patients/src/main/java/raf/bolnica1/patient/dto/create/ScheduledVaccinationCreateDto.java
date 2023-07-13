package raf.bolnica1.patient.dto.create;


import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.general.VaccinationDto;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class ScheduledVaccinationCreateDto implements Serializable {

    private Timestamp dateAndTime;
    private String note;
    private String lbz;
    private Long vaccinationId;
    private String lbp;

}
