package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.VaccinationType;

import javax.persistence.*;

@Entity
@Table(name = "vaccination")
@Getter
@Setter
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private VaccinationType type;
    private String description;
    private String manufacturer;
}
