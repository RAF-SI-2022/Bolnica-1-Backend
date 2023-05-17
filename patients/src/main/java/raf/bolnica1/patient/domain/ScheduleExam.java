package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "schedule_exam",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dateAndTime", "doctorLbz"})})
@Getter
@Setter
public class ScheduleExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp dateAndTime;
    @Enumerated(EnumType.STRING)
    private PatientArrival arrivalStatus;
    private String note;
    private String doctorLbz;
    private String lbz;
    @ManyToOne
    private Patient patient;

}
