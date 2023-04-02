package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;

@Component
public class PrescriptionMapper {

    public PrescriptionLabSendDto getPrescriptionSendDto(PrescriptionLabSendDto prescriptionCreateDto){
        PrescriptionLabSendDto prescriptionSendDto = new PrescriptionLabSendDto();
        prescriptionSendDto.setLbp(prescriptionCreateDto.getLbp());
        prescriptionSendDto.setDepartmentFromId(prescriptionCreateDto.getDepartmentFromId());
        prescriptionSendDto.setDepartmentToId(prescriptionCreateDto.getDepartmentToId());
        prescriptionSendDto.setDoctorLbz(prescriptionCreateDto.getDoctorLbz());
        prescriptionSendDto.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescriptionSendDto.setType(prescriptionCreateDto.getType());
        prescriptionSendDto.setComment(prescriptionCreateDto.getComment());
        prescriptionSendDto.setCreationDateTime(prescriptionCreateDto.getCreationDateTime());
        prescriptionSendDto.setPrescriptionAnalysisDtos(prescriptionCreateDto.getPrescriptionAnalysisDtos());
        return prescriptionSendDto;
    }
}
