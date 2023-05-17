package raf.bolnica1.infirmary.dto.scheduledAppointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledAppointmentCreateDto implements Serializable {

    private Timestamp patientAdmission;
    private String note;
    private Long prescriptionId;

}
