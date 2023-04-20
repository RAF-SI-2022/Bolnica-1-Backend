package raf.bolnica1.infirmary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name="hospitalization")
public class Hospitalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lbzDoctor;
    private Timestamp patientAdmission;
    @ManyToOne
    private HospitalRoom hospitalRoom;
    private String lbzRegister;
    private Timestamp dischargeDateAndTime;
    @OneToOne
    private Prescription prescription;
    private String name;
    private String surname;
    private String jmbg;
    private String note;

}
