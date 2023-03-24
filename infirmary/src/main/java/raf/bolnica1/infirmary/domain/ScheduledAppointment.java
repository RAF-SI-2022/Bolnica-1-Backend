package raf.bolnica1.infirmary.domain;

import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="scheduled_appointment")
public class ScheduledAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp patientAdmission;
    @Enumerated(EnumType.STRING)
    private AdmissionStatus admissionStatus;
    private String note;
    private String lbzScheduler;
    @OneToOne
    private Prescription prescription;
}
