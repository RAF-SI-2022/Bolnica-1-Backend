package raf.bolnica1.infirmary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
@Getter
@Setter
@Entity
@Table(name="patient_state")
public class PatientState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateExamState;
    private Time timeExamState;
    private float temperature;
    private int systolicPressure;
    private int diastolicPressure;
    private int pulse;
    private String therapy;
    private String description;
    private String lbz;
    @ManyToOne
    private Hospitalization hospitalization;
}
