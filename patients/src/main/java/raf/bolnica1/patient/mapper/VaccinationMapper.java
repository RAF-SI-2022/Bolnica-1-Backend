package raf.bolnica1.patient.mapper;


import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.dto.VaccinationDto;

@Component
public class VaccinationMapper {

    public VaccinationDto toDto(Vaccination vaccination){
        if(vaccination==null)return null;

        VaccinationDto dto=new VaccinationDto();

        dto.setName(vaccination.getName());
        dto.setType(vaccination.getType());
        dto.setDescription(vaccination.getDescription());
        dto.setManufacturer(vaccination.getManufacturer());

        return dto;

    }

    public Vaccination toEntity(VaccinationDto vaccinationDto){
        if(vaccinationDto==null)return null;

        Vaccination entity=new Vaccination();

        entity.setName(vaccinationDto.getName());
        entity.setType(vaccinationDto.getType());
        entity.setDescription(vaccinationDto.getDescription());
        entity.setManufacturer(vaccinationDto.getManufacturer());

        return entity;

    }



}
