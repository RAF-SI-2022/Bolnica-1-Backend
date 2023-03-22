package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;


@Component
@AllArgsConstructor
public class PrescriptionMapper {

    public PrescriptionDto toDto(Prescription entity) {
        PrescriptionDto dto = new PrescriptionDto();
        dto.setLbp(entity.getLbp());
        dto.setStatus(entity.getStatus());
        dto.setComment(entity.getComment());
        dto.setType(entity.getType());
        dto.setReferralReason(entity.getReferralReason());
        dto.setReferralDiagnosis(entity.getReferralDiagnosis());
        dto.setCreationDateTime(entity.getCreationDateTime());
        dto.setDepartmentFromId(entity.getDepartmentFromId()); // naci
        dto.setDepartmentToId(entity.getDepartmentToId()); // naci
        dto.setDoctorId(entity.getDoctorId()); // naci
        return dto;
    }

}
