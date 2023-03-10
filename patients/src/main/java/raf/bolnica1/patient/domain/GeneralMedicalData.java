package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "general_medical_data")
@Getter
@Setter
public class GeneralMedicalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^(A|B|AB|O)$")
    private String bloodType;
    @Pattern(regexp = "[+-]")
    private char rH;
}
