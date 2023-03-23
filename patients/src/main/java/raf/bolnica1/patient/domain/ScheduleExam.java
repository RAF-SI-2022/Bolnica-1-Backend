package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "schedule_exam")
@Getter
@Setter
public class ScheduleExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp dateAndTime;
    private boolean arrived;
    private Long doctorId;
    private Long lbz;
    @ManyToOne
    private Patient patient;

}
