package raf.bolnica1.infirmary.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.DtoHospitalization;
@Component
public class HospitalizationMapper {
    public DtoHospitalization toDto(Hospitalization hospitalization){

        DtoHospitalization dto = new DtoHospitalization();
        dto.setId(hospitalization.getId());
        dto.setLbzDoctor(hospitalization.getLbzDoctor());
        dto.setPatientAdmission(hospitalization.getPatientAdmission());
        dto.setHospitalRoomId(hospitalization.getHospitalRoom().getId());
        dto.setLbzRegister(hospitalization.getLbzRegister());
        dto.setDischargeDateAndTime(hospitalization.getDischargeDateAndTime());
        dto.setPrescriptionId(hospitalization.getPrescription().getId());
        dto.setNote(hospitalization.getNote());

        return dto;
    }
}
