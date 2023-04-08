package raf.bolnica1.infirmary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name="visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String visitorName;
    private String visitorSurname;
    private String visitorJmbg;
    private String lbzRegister;
    private Timestamp visitTime;
    private String note;
    @ManyToOne
    private Hospitalization hospitalization;

}
