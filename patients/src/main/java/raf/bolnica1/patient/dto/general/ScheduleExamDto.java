package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.ExaminationStatus;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import java.sql.Timestamp;

@Getter
@Setter
public class ScheduleExamDto {
    private Long id;
    private Timestamp dateAndTime;
    private PatientArrival patientArrival;
    private ExaminationStatus examinationStatus;
    private Long doctorId;
    private Long lbz;
    private String lbp;
}
