package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;

@AllArgsConstructor
@Component
public class DischargeListMapper {

    public DischargeListDto toDto(DischargeList entity){
        if(entity==null)return null;

        DischargeListDto dto=new DischargeListDto();

        dto.setId(entity.getId());
        dto.setAnamnesis(entity.getAnamnesis());
        dto.setAnalysis(entity.getAnalysis());
        dto.setCreation(entity.getCreation());
        dto.setSummary(entity.getSummary());
        dto.setTherapy(entity.getTherapy());
        dto.setFollowingDiagnosis(entity.getFollowingDiagnosis());
        dto.setLbzDepartment(entity.getLbzDepartment());
        dto.setLbzPrescribing(entity.getLbzPrescribing());
        dto.setHospitalizationId(entity.getHospitalization().getId());
        dto.setCourseOfDisease(entity.getCourseOfDisease());

        return dto;
    }


    /// kreiranje
    public DischargeList toEntity(DischargeListDto dto, HospitalizationRepository hospitalizationRepository){

        if(dto==null)return null;

        DischargeList entity=new DischargeList();

        entity.setAnamnesis(dto.getAnamnesis());
        entity.setAnalysis(dto.getAnalysis());
        entity.setCreation(dto.getCreation());
        entity.setSummary(dto.getSummary());
        entity.setTherapy(dto.getTherapy());
        entity.setFollowingDiagnosis(dto.getFollowingDiagnosis());
        entity.setLbzDepartment(dto.getLbzDepartment());
        entity.setLbzPrescribing(dto.getLbzPrescribing());
        entity.setHospitalization(hospitalizationRepository.findHospitalizationById(dto.getHospitalizationId()));
        entity.setCourseOfDisease(dto.getCourseOfDisease());

        return entity;
    }

}
