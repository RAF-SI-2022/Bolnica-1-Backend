package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GeneralMedicalDataDto implements Serializable {

    private Long id;
    private String bloodType;
    private String rH;
    private List<VaccinationDto> vaccinationDtos;
    private List<AllergyDto> allergyDtos;

}
