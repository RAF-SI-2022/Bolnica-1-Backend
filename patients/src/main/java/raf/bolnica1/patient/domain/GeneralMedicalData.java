package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "general_medical_data")
@Getter
@Setter
public class GeneralMedicalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private char[] bloodType = new char[2];
    private boolean rH;
}
