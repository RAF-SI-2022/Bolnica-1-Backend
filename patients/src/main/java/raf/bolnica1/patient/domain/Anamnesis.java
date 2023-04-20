package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "anamnesis")
@Getter
@Setter
public class Anamnesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainProblems;
    private String currDisease;
    private String personalAnamnesis;
    private String familyAnamnesis;
    private String patientOpinion;
}
