package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.general.AllergyDto;
import raf.bolnica1.patient.dto.general.VaccinationDto;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GeneralMedicalDataCreateDto implements Serializable {

    private String bloodType;
    private String rH;
    private List<VaccinationDto> vaccinationDtos;
    private List<AllergyDto> allergyDtos;

}
