package raf.bolnica1.infirmary.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

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
