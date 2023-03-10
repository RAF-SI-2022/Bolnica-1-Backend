package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "vaccination_data")
@Getter
@Setter
public class VaccinationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date vaccinationDate;
    private boolean deleted = false;
    @ManyToOne
    private Vaccination vaccination;
    @ManyToOne
    private GeneralMedicalData generalMedicalData;
}
