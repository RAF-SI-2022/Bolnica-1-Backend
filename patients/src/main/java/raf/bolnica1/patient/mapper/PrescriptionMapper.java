package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;
import raf.bolnica1.patient.domain.prescription.LabPrescription;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.create.LabResultDto;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDoneDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionSendDto;
import raf.bolnica1.patient.dto.prescription.lab.ParameterDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionAnalysisNameDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionDoneLabDto;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.PrescriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionMapper {

    private LabResultsRepository labResultsRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private PatientRepository patientRepository;


    public PrescriptionDoneDto toDto(Prescription prescription){
        PrescriptionDoneDto prescriptionDoneDto = null;
        if(prescription.getDecriminatorValue().equals("LABORATORIJA") && prescription instanceof LabPrescription){
            prescriptionDoneDto = new PrescriptionDoneLabDto();
            prescriptionDoneDto.setDate(prescription.getDate());
            prescriptionDoneDto.setType("LABORATORIJA");
            prescriptionDoneDto.setDoctorLbz(prescription.getDoctorLbz());
            prescriptionDoneDto.setDepartmentToId(prescription.getDepartmentToId());
            prescriptionDoneDto.setDepartmentFromId(prescription.getDepartmentFromId());
            prescriptionDoneDto.setLbp(prescription.getMedicalRecord().getPatient().getLbp());
            ((PrescriptionDoneLabDto) prescriptionDoneDto).setComment(((LabPrescription) prescription).getComment());
            prescriptionDoneDto.setPrescriptionStatus(PrescriptionStatus.REALIZOVAN);
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

    public Prescription toEntity(PrescriptionCreateDto dto){

        Prescription prescription=null;

        if(dto.getType().equals("LABORATORIJA")){
            prescription=new LabPrescription();
            prescription.setDate(dto.getDate());
            prescription.setDoctorLbz(dto.getDoctorLbz());
            prescription.setDepartmentFromId(dto.getDepartmentFromId());
            prescription.setDepartmentToId(dto.getDepartmentToId());
            ((LabPrescription)prescription).setComment(dto.getComment());

            Patient patient=patientRepository.findByLbp(dto.getLbp()).orElse(null);
            if(patient!=null) {
                MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElse(null);
                if(medicalRecord!=null){
                    prescription.setMedicalRecord(medicalRecord);
                }
            }
        }
        return prescription;
    }

    public LabResults getLabResult(Prescription prescription, LabResultDto labResultDto){
        LabResults labResults=new LabResults();
        labResults.setLabPrescription((LabPrescription) prescription);
        labResults.setParameterName(labResultDto.getParameterName());
        labResults.setAnalysisName(labResultDto.getAnalysisName());
        labResults.setLowerLimit(labResultDto.getLowerLimit());
        labResults.setUpperLimit(labResultDto.getUpperLimit());
        labResults.setUnitOfMeasure(labResultDto.getUnitOfMeasure());
        labResults.setResult(labResultDto.getResult());

        return labResults;
    }

}
