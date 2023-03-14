package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medical_record")
@Getter
@Setter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="lbp", referencedColumnName="lbp")
    private Patient patient;
    private Date registrationDate;
    private boolean deleted = false;
    @OneToOne
    private GeneralMedicalData generalMedicalData;

}
