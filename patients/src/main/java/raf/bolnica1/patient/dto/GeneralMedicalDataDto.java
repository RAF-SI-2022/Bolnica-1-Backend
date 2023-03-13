package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.AllergyData;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.VaccinationData;

import java.util.List;

@Getter
@Setter
public class GeneralMedicalDataDto {

    private String bloodType;
    private char rH;
    private List<VaccinationDto> vaccinationData;
    private List<AllergyDto> allergyData;

}
