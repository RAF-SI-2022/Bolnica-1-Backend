package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis_code")
@Getter
@Setter
public class DiagnosisCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String description;
    private String latinDescription;
}
