package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Therapy;
import raf.bolnica1.patient.dto.TherapyDto;

@Component
public class TherapyMapper {

    public TherapyDto toDto(Therapy entity){
        if(entity==null)return null;

        TherapyDto dto=new TherapyDto();

        dto.setAdvice(entity.getAdvice());
        dto.setTherapy(entity.getTherapy());

        return dto;
    }

}
