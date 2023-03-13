package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.domain.Vaccination;

import java.util.List;

@Getter
@Setter
public class PatientDetailsDto {

    private String bloodType;
    private char rH;
    private List<Allergy> allergies;
    private List<Vaccination> vaccinations;
}
