package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.*;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionUpdateDto;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;

@Component
@AllArgsConstructor
public class PrescriptionMapper {

    private LabWorkOrderRepository labWorkOrderRepository;
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private AnalysisParameterRepository analysisParameterRepository;

    public PrescriptionDto toDto(Prescription entity) {
        PrescriptionDto dto = new PrescriptionDto();
        dto.setLbp(entity.getLbp());
        dto.setStatus(entity.getStatus());
        dto.setComment(entity.getComment());
        dto.setType(entity.getType());
        dto.setCreationDateTime(entity.getCreationDateTime());
        dto.setDepartmentFromId(entity.getDepartmentFromId()); // naci
        dto.setDepartmentToId(entity.getDepartmentToId()); // naci
        dto.setDoctorLbz(entity.getDoctorLbz()); // naci

        return dto;
    }

    public Prescription toEntity(PrescriptionCreateDto prescriptionCreateDto){
        Prescription prescription = new Prescription();
        prescription.setStatus(prescriptionCreateDto.getStatus());
        prescription.setComment(prescriptionCreateDto.getComment());
        prescription.setCreationDateTime(prescriptionCreateDto.getCreationDateTime());
        prescription.setDoctorLbz(prescriptionCreateDto.getDoctorLbz());
        prescription.setLbp(prescriptionCreateDto.getLbp());
        prescription.setDepartmentFromId(prescriptionCreateDto.getDepartmentFromId());
        prescription.setDepartmentToId(prescriptionCreateDto.getDepartmentToId());
        prescription.setType(prescriptionCreateDto.getType());

        return prescription;
    }

    public Prescription toEntityUpdate(PrescriptionUpdateDto prescriptionUpdateDto, Prescription prescription){
        prescription.setComment(prescriptionUpdateDto.getComment());
        prescription.setDepartmentFromId(prescriptionUpdateDto.getDepartmentFromId());
        prescription.setDepartmentToId(prescriptionUpdateDto.getDepartmentToId());
        prescription.setCreationDateTime(prescriptionUpdateDto.getCreationDateTime());

        return prescription;
    }

}
