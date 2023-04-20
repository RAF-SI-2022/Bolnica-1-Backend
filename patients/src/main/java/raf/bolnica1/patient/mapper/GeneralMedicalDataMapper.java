package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;

import java.util.List;

@Component
public class GeneralMedicalDataMapper {

    private AllergyMapper allergyMapper;
    private VaccinationMapper vaccinationMapper;

    public GeneralMedicalDataMapper(AllergyMapper allergyMapper, VaccinationMapper vaccinationMapper){
        this.allergyMapper=allergyMapper;
        this.vaccinationMapper=vaccinationMapper;
    }

    public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData){
        if(generalMedicalData==null)return null;

        GeneralMedicalDataDto dto=new GeneralMedicalDataDto();

        dto.setId(generalMedicalData.getId());
        dto.setBloodType(generalMedicalData.getBloodType());
        dto.setRH(generalMedicalData.getRH());

        return dto;
    }

    /*public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData, List<Vaccination> vaccinations, List<Allergy> allergies){
        GeneralMedicalDataDto dto= toDto(generalMedicalData);
        if(dto==null)return null;

        dto.setVaccinationDtos(vaccinationMapper.toDto(vaccinations));
        dto.setAllergyDtos(allergyMapper.toDto(allergies));

        return dto;
    }*/

    public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData, List<Object[]> vaccinations, List<Allergy> allergies) {
        GeneralMedicalDataDto dto= toDto(generalMedicalData);
        if(dto==null)return null;

        dto.setVaccinationDtos(vaccinationMapper.toDto(vaccinations));
        dto.setAllergyDtos(allergyMapper.toDto(allergies));

        return dto;
    }

    public GeneralMedicalData toEntity(GeneralMedicalDataCreateDto generalMedicalDataCreateDto){
        if(generalMedicalDataCreateDto == null)return null;

        GeneralMedicalData entity=new GeneralMedicalData();

        entity.setBloodType(generalMedicalDataCreateDto.getBloodType());
        entity.setRH(generalMedicalDataCreateDto.getRH());

        return entity;
    }



}
