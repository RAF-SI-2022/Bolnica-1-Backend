package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.DiagnosisCode;
import raf.bolnica1.patient.dto.DiagnosisCodeDto;

@Component
public class DiagnosisCodeMapper {

    public DiagnosisCodeDto toDto(DiagnosisCode entity){
        if(entity==null)return null;

        DiagnosisCodeDto dto=new DiagnosisCodeDto();

        dto.setDescription(entity.getDescription());
        dto.setLatinDescription(entity.getLatinDescription());

        return dto;
    }

    public DiagnosisCode toEntity(DiagnosisCodeDto dto){
        if(dto==null)return null;

        DiagnosisCode entity=new DiagnosisCode();

        entity.setDescription(dto.getDescription());
        entity.setLatinDescription(dto.getLatinDescription());

        return entity;
    }

}
