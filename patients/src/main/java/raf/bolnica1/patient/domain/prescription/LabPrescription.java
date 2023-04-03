package raf.bolnica1.patient.domain.prescription;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LABORATORIJA")
public class LabPrescription extends Prescription{
    private String comment;
}
