package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.prescription.PrescriptionSendDto;

import java.sql.Timestamp;

@Component
public class PrescriptionMapper {

    public PrescriptionSendDto getPrescriptionSendDto(PrescriptionCreateDto prescriptionCreateDto){
        PrescriptionSendDto prescriptionSendDto = new PrescriptionSendDto();
        prescriptionSendDto.setLbp(prescriptionCreateDto.getLbp());
        prescriptionSendDto.setDepartmentFromId(prescriptionCreateDto.getDepartmentFromId());
        prescriptionSendDto.setDepartmentToId(prescriptionCreateDto.getDepartmentToId());
        prescriptionSendDto.setDoctorId(prescriptionCreateDto.getDoctorId());
        prescriptionSendDto.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescriptionSendDto.setType(prescriptionCreateDto.getType());
        prescriptionSendDto.setComment(prescriptionCreateDto.getComment());
        prescriptionSendDto.setReferralDiagnosis(prescriptionCreateDto.getReferralDiagnosis());
        prescriptionSendDto.setReferralReason(prescriptionCreateDto.getReferralReason());
        prescriptionSendDto.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
        prescriptionSendDto.setPrescriptionAnalysisDtos(prescriptionCreateDto.getPrescriptionAnalysisDtos());
        return prescriptionSendDto;
    }
}
