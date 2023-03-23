package raf.bolnica1.laboratory.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDataDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionRecieveMapper {

    private LabWorkOrderRepository labWorkOrderRepository;
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private AnalysisParameterRepository analysisParameterRepository;

    public PrescriptionDto toPrescriptionDto(Prescription entity) {
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

        LabWorkOrder labWorkOrder = labWorkOrderRepository.findByPrescription(entity.getId()).orElse(null);
        if(labWorkOrder != null){
            boolean found = false;
            List<PrescriptionAnalysisDataDto> prescriptionAnalysisDataDtoList = new ArrayList<>();
            List<ParameterAnalysisResult> parameterAnalysisResults = parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrder.getId());
            for(ParameterAnalysisResult parameterAnalysisResult : parameterAnalysisResults){
                LabAnalysis labAnalysis = parameterAnalysisResult.getAnalysisParameter().getLabAnalysis();
                for(PrescriptionAnalysisDataDto prescriptionAnalysisDataDto : prescriptionAnalysisDataDtoList){
                    if(prescriptionAnalysisDataDto.getAnalysisName().equals(labAnalysis.getAnalysisName())) {
                        found = true;
                        break;
                    }
                }
                if(!found){
                    prescriptionAnalysisDataDtoList.add(new PrescriptionAnalysisDataDto(labAnalysis.getAnalysisName(), analysisParameterRepository.findAnalysisParameterByAnalysisId(labAnalysis.getId())));
                }
                found = false;
            }
            dto.setPrescriptionAnalysisDataDtoList(prescriptionAnalysisDataDtoList);
        }

        return dto;
    }
}
