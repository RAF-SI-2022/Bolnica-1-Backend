package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.general.AllergyDto;
import raf.bolnica1.patient.dto.general.VaccinationDto;

import java.util.List;

@Getter
@Setter
public class GeneralMedicalDataCreateDto {

    private String bloodType;
    private String rH;
    private List<VaccinationDto> vaccinationDtos;
    private List<AllergyDto> allergyDtos;

}
