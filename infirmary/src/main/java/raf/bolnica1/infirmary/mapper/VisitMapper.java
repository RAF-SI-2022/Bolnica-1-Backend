package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;

@Component
@AllArgsConstructor
public class VisitMapper {

    private final AuthenticationUtils authenticationUtils;


    public VisitDto toDto(Visit entity){

        if(entity==null)return null;

        VisitDto dto=new VisitDto();

        dto.setId(entity.getId());
        dto.setNote(entity.getNote());
        dto.setHospitalizationId(entity.getHospitalization().getId());
        dto.setVisitTime(entity.getVisitTime());
        dto.setVisitorJmbg(entity.getVisitorJmbg());
        dto.setLbzRegister(entity.getLbzRegister());
        dto.setVisitorName(entity.getVisitorName());
        dto.setVisitorSurname(entity.getVisitorSurname());

        return dto;
    }


    /// kreiranje
    public Visit toEntity(VisitCreateDto dto, HospitalizationRepository hospitalizationRepository){
        if(dto==null)return null;

        Visit entity=new Visit();

        entity.setHospitalization(hospitalizationRepository.findHospitalizationById(dto.getHospitalizationId()));
        entity.setNote(dto.getNote());
        entity.setVisitTime(dto.getVisitTime());
        entity.setVisitorJmbg(dto.getVisitorJmbg());
        entity.setVisitorName(dto.getVisitorName());
        entity.setVisitorSurname(dto.getVisitorSurname());
        entity.setLbzRegister(authenticationUtils.getLbzFromAuthentication());

        return entity;
    }

}
