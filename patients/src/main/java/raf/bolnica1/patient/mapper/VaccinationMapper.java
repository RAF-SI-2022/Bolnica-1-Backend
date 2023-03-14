package raf.bolnica1.patient.mapper;


import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.dto.VaccinationDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    public VaccinationDto toDto(Object[] vaccination){
        if(vaccination==null)return null;

        VaccinationDto dto=new VaccinationDto();

        dto.setName(((Vaccination)vaccination[0]).getName());
        dto.setType(((Vaccination)vaccination[0]).getType());
        dto.setDescription(((Vaccination)vaccination[0]).getDescription());
        dto.setManufacturer(((Vaccination)vaccination[0]).getManufacturer());
        dto.setVaccinationDate((Date)vaccination[1]);

        return dto;

    }

    /*public List<VaccinationDto> toDto(List<Vaccination> vaccinations){
        if(vaccinations==null)return null;

        List<VaccinationDto> dto=new ArrayList<>();

        for(Vaccination vaccination:vaccinations)
            dto.add(toDto(vaccination));

        return dto;
    }*/

    public List<VaccinationDto> toDto(List<Object[]> vaccinations){
        if(vaccinations==null)return null;

        List<VaccinationDto> dto=new ArrayList<>();

        for(Object[] vaccination:vaccinations)
            dto.add(toDto(vaccination));

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
