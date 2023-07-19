package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "covid_examination_history")
@Getter
@Setter
public class CovidExaminationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date examDate;
    private String lbz;
    @ManyToOne
    private MedicalRecord medicalRecord;

    private String lbp;
    private String symptoms;
    private String duration;
    private Double bodyTemperature;
    private Double bloodPressure;
    private Double saturation;
    private String lungCondition;
    private String therapy;
}
