package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;

@Component
@AllArgsConstructor
public class LabWorkOrderMapper {

    private final PrescriptionMapper prescriptionMapper;

    public LabWorkOrderDto toDto(LabWorkOrder labWorkOrder) {
        LabWorkOrderDto dto = new LabWorkOrderDto();
        dto.setPrescription(prescriptionMapper.toDto(labWorkOrder.getPrescription()));
        dto.setCreationDateTime(labWorkOrder.getCreationDateTime());
        dto.setStatus(labWorkOrder.getStatus());
        dto.setTechnicianLbz(labWorkOrder.getTechnicianLbz());
        dto.setBiochemistLbz(labWorkOrder.getBiochemistLbz());
        dto.setLbp(labWorkOrder.getLbp());
        return dto;
    }
}
