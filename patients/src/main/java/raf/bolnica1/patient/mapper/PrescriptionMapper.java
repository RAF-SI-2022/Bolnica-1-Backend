package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDoneDto;
import raf.bolnica1.patient.dto.prescription.lab.ParameterDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionAnalysisNameDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionDoneLabDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.PrescriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionMapper {

    private PrescriptionRepository prescriptionRepository;
    private LabResultsRepository labResultsRepository;

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

    public PrescriptionDoneDto toDto(Prescription prescription){
        PrescriptionDoneDto prescriptionDoneDto = null;
        if(prescription.getDecriminatorValue().equals("LABORATORIJA")){
            prescriptionDoneDto = new PrescriptionDoneLabDto();
            prescriptionDoneDto.setDate(prescription.getDate());
            prescriptionDoneDto.setType("LABORATORIJA");
            prescriptionDoneDto.setDoctorLbz(prescription.getDoctorLbz());
            prescriptionDoneDto.setDepartmentToId(prescription.getDepartmentToId());
            prescriptionDoneDto.setDepartmentFromId(prescription.getDepartmentFromId());
            prescriptionDoneDto.setLbp(prescription.getMedicalRecord().getPatient().getLbp());
            List<String> analysisList = labResultsRepository.findAnalysisForPrescription(prescription.getId());
            for(String analysis : analysisList) {
                List<LabResults> labResults = labResultsRepository.findResultsForPrescription(prescription.getId(), analysis);
                List<ParameterDto> parameters = new ArrayList<>();
                for(LabResults labResult : labResults){
                    parameters.add(new ParameterDto(labResult.getParameterName(), labResult.getResult(), labResult.getLowerLimit(), labResult.getUpperLimit()));
                }
                ((PrescriptionDoneLabDto) prescriptionDoneDto).getParameters().add(new PrescriptionAnalysisNameDto(analysis, parameters));
            }
        }
        return prescriptionDoneDto;
    }
}
