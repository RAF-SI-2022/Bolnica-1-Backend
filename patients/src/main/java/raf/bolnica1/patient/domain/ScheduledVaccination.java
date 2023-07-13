package raf.bolnica1.patient.domain;


import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class ScheduledVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp dateAndTime;
    @Enumerated(EnumType.STRING)
    private PatientArrival arrivalStatus;
    private String note;
    private String lbz;
    @ManyToOne
    private Vaccination vaccination;
    @ManyToOne
    private Patient patient;

}
