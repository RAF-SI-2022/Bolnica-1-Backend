package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.dto.GeneralMedicalDataDto;

@Component
public class GeneralMedicalDataMapper {

    public GeneralMedicalDataDto toDto(GeneralMedicalData generalMedicalData){
        if(generalMedicalData==null)return null;

        GeneralMedicalDataDto dto=new GeneralMedicalDataDto();

        dto.setBloodType(generalMedicalData.getBloodType());
        dto.setRH(generalMedicalData.getRH());

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
