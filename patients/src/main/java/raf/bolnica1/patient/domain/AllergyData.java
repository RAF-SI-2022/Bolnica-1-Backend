package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "allergy_data")
@Getter
@Setter
public class AllergyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private GeneralMedicalData generalMedicalData;
    @ManyToOne
    private Allergy allergy;
}
