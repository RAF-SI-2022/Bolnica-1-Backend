package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Anamnesis;
import raf.bolnica1.patient.dto.AnamnesisDto;

@Component
public class AnamnesisMapper {

    public AnamnesisDto toDto(Anamnesis entity){
        if(entity==null)return null;

        AnamnesisDto dto=new AnamnesisDto();

        dto.setCurrDisease(entity.getCurrDisease());
        dto.setFamilyAnamnesis(entity.getFamilyAnamnesis());
        dto.setPersonalAnamnesis(entity.getPersonalAnamnesis());
        dto.setMainProblems(entity.getMainProblems());
        dto.setPatientOpinion(entity.getPatientOpinion());

        return dto;
    }

}
