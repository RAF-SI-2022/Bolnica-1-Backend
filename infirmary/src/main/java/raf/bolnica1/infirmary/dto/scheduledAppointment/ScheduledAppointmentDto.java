package raf.bolnica1.infirmary.dto.scheduledAppointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledAppointmentDto {

    private Long id;
    private Timestamp patientAdmission;
    private AdmissionStatus admissionStatus;
    private String note;
    private String lbzScheduler;
    private String lbp;
    private Long prescriptionId;

}
