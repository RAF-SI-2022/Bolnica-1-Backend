package raf.bolnica1.patient.domain;

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
    private Timestamp patientAdmission;
    @ManyToOne
    private MedicalRecord medicalRecord;
    private String note;
    private boolean active;
    private boolean covid;
    @OneToOne
    private DischargeList dischargeList;

}
