package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionMapper {

    public static PrescriptionMapper getInstance(){
        return new PrescriptionMapper();
    }


    public PrescriptionDto toDto(Prescription entity){
        if(entity==null)return null;

        PrescriptionDto dto=new PrescriptionDto();

        dto.setId(entity.getId());
        dto.setCreationDateTime(entity.getCreationDateTime());
        dto.setLbp(entity.getLbp());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setDoctorLbz(entity.getDoctorLbz());
        dto.setReferralReason(entity.getReferralReason());
        dto.setReferralDiagnosis(entity.getReferralDiagnosis());
        dto.setDepartmentToId(entity.getDepartmentToId());
        dto.setDepartmentFromId(entity.getDepartmentFromId());

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

        entity.setCreationDateTime(dto.getCreationDateTime());
        entity.setDoctorLbz(dto.getDoctorLbz());
        entity.setLbp(dto.getLbp());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setReferralReason(dto.getReferralReason());
        entity.setReferralDiagnosis(dto.getReferralDiagnosis());
        entity.setDepartmentFromId(dto.getDepartmentFromId());
        entity.setDepartmentToId(dto.getDepartmentToId());

        return entity;
    }

}
