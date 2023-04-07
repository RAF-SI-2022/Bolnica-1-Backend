package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionMapper {


    public PrescriptionDto toDto(Prescription entity){
        if(entity==null)return null;

        PrescriptionDto dto=new PrescriptionDto();

        dto.setId(entity.getId());
        dto.setCreation(entity.getCreation());
        dto.setLbp(entity.getLbp());
        dto.setPrescriptionStatus(entity.getPrescriptionStatus());
        dto.setPrescriptionType(entity.getPrescriptionType());
        dto.setDoctorLbz(entity.getDoctorLbz());
        dto.setReferralReason(entity.getReferralReason());
        dto.setReferralDiagnosis(entity.getReferralDiagnosis());
        dto.setIdDepartmentTo(entity.getIdDepartmentTo());
        dto.setIdDepartmentFrom(entity.getIdDepartmentFrom());

        return dto;
    }

    public List<PrescriptionDto> toDto(List<Prescription> entity){
        if(entity==null)return null;

        List<PrescriptionDto> dto=new ArrayList<>();

        for(Prescription prescription:entity)
            dto.add(toDto(prescription));

        return dto;
    }

    public Prescription toEntity(PrescriptionReceiveDto dto){
        if(dto==null)return null;

        Prescription entity=new Prescription();

        entity.setCreation(dto.getCreationDateTime());
        entity.setDoctorLbz(dto.getDoctorLbz());
        entity.setLbp(dto.getLbp());
        entity.setPrescriptionStatus(dto.getStatus());
        entity.setPrescriptionType(dto.getType());
        entity.setReferralReason(dto.getReferralReason());
        entity.setReferralDiagnosis(dto.getReferralDiagnosis());
        entity.setIdDepartmentFrom(dto.getDepartmentFromId());
        entity.setIdDepartmentTo(dto.getDepartmentToId());

        return entity;
    }

}
