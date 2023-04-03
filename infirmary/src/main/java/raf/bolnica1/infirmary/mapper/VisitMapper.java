package raf.bolnica1.infirmary.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.VisitDto;

@Component
public class VisitMapper {

    public VisitDto toDto(Visit entity){
        if(entity == null)
            return null;

        VisitDto dto = new VisitDto();
        dto.setVisitorJmbg(entity.getVisitorJmbg());
        dto.setVisitorName(entity.getVisitorName());
        dto.setVisitorSurname(entity.getVisitorSurname());
        dto.setNote(entity.getNote());
        dto.setLbzRegister(entity.getLbzRegister());

        return dto;
    }

}
