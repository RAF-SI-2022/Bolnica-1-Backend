package raf.bolnica1.patient.domain.prescription;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LABORATORIJA")
@Getter
@Setter
public class LabPrescription extends Prescription{
    private String comment;
}
