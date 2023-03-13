package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.domain.VaccinationData;
import raf.bolnica1.patient.dto.AllergyDto;
import raf.bolnica1.patient.dto.GeneralMedicalDataDto;
import raf.bolnica1.patient.dto.VaccinationDto;

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

        dto.setBloodType(generalMedicalData.getBloodType());
        dto.setRH(generalMedicalData.getRH());

        return dto;
    }

    public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData, List<Vaccination> vaccinations, List<Allergy> allergies){
        GeneralMedicalDataDto dto= toDto(generalMedicalData);
        if(dto==null)return null;

        dto.setVaccinationDtos(vaccinationMapper.toDto(vaccinations));
        dto.setAllergyDtos(allergyMapper.toDto(allergies));

        return dto;
    }

    public GeneralMedicalData toEntity(GeneralMedicalDataDto generalMedicalDataDto){
        if(generalMedicalDataDto==null)return null;

        GeneralMedicalData entity=new GeneralMedicalData();

        entity.setBloodType(generalMedicalDataDto.getBloodType());
        entity.setRH(generalMedicalDataDto.getRH());

        return entity;
    }



}
