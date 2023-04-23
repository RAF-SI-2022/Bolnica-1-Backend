package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import java.sql.Timestamp;

@Getter
@Setter
public class ScheduleExamDto {
    private Long id;
    private Timestamp dateAndTime;
    private PatientArrival patientArrival;
    private String doctorLbz;
    private String lbz;
    private String lbp;
}
