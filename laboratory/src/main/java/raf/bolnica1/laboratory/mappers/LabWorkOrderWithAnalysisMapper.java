package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderWithAnalysisDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LabWorkOrderWithAnalysisMapper {

    private final ParameterAnalysisResultWithDetailsMapper parameterAnalysisResultWithDetailsMapper;

    public LabWorkOrderWithAnalysisDto toDto(LabWorkOrder labWorkOrder, List<ParameterAnalysisResult> parameterAnalysisResults) {
        LabWorkOrderWithAnalysisDto dto = new LabWorkOrderWithAnalysisDto();
        dto.setId(labWorkOrder.getId());
        dto.setPrescriptionId(labWorkOrder.getPrescription().getId());
        dto.setLbp(labWorkOrder.getLbp());
        dto.setCreationDateTime(labWorkOrder.getCreationDateTime());
        dto.setStatus(labWorkOrder.getStatus());
        dto.setTechnicianLbz(labWorkOrder.getTechnicianLbz());
        dto.setBiochemistLbz(labWorkOrder.getBiochemistLbz());
        dto.setParameterAnalysisResults(parameterAnalysisResults.stream()
                .map(parameterAnalysisResultWithDetailsMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
