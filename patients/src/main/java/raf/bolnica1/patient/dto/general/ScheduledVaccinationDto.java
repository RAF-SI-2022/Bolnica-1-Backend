package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class ScheduledVaccinationDto implements Serializable {

    private Long id;
    private Timestamp dateAndTime;
    private PatientArrival arrivalStatus;
    private String note;
    private String lbz;
    private VaccinationDto vaccination;
    private PatientDto patient;

}
