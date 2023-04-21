package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;
import raf.bolnica1.patient.repository.AllergyDataRepository;
import raf.bolnica1.patient.repository.VaccinationDataRepository;
import raf.bolnica1.patient.repository.VaccinationRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class GeneralMedicalDataMapper {

    private AllergyMapper allergyMapper;
    private VaccinationMapper vaccinationMapper;

    private VaccinationDataRepository vaccinationDataRepository;
    private AllergyDataRepository allergyDataRepository;

    public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData){
        if(generalMedicalData==null)return null;

        GeneralMedicalDataDto dto=new GeneralMedicalDataDto();

        dto.setId(generalMedicalData.getId());
        dto.setBloodType(generalMedicalData.getBloodType());
        dto.setRH(generalMedicalData.getRH());

        dto.setVaccinationDtos(vaccinationMapper.toDtoVaccList(vaccinationDataRepository.findAllByGeneralMedicalDataId(generalMedicalData.getId())));
        dto.setAllergyDtos(allergyMapper.toDto(allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData)));
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

    public GeneralMedicalData toEntity(GeneralMedicalDataCreateDto generalMedicalDataCreateDto, GeneralMedicalData entity){
        if(generalMedicalDataCreateDto == null)return null;

        entity.setBloodType(generalMedicalDataCreateDto.getBloodType());
        entity.setRH(generalMedicalDataCreateDto.getRH());

        return entity;
    }



}
