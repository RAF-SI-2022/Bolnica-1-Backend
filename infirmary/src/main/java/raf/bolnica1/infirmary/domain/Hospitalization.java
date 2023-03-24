package raf.bolnica1.infirmary.domain;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private String note;

}
