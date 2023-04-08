package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralMedicalDataDto {

    private Long id;
    private String bloodType;
    private String rH;
    private List<VaccinationDto> vaccinationDtos;
    private List<AllergyDto> allergyDtos;

}