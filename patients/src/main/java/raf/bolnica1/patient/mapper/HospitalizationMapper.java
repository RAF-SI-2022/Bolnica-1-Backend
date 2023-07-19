package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.DischargeList;
import raf.bolnica1.patient.domain.Hospitalization;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.externalInfirmary.DischargeListCreateDto;
import raf.bolnica1.patient.dto.externalInfirmary.HospitalizationCreateDto;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;

@Component
@AllArgsConstructor
public class HospitalizationMapper {

    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public Hospitalization dtoToEntityHospitalization(HospitalizationCreateDto hospitalizationCreateDto){
        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setActive(true);
        hospitalization.setCovid(hospitalizationCreateDto.isCovid());
        hospitalization.setNote(hospitalizationCreateDto.getNote());
        hospitalization.setPatientAdmission(hospitalizationCreateDto.getPatientAdmission());
        System.out.println("LBP " + hospitalizationCreateDto.getLbp());
        Patient patient = patientRepository.findByLbp(hospitalizationCreateDto.getLbp()).orElseThrow(()->new RuntimeException());
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(()->new RuntimeException());
        hospitalization.setMedicalRecord(medicalRecord);

        return hospitalization;
    }

    public DischargeList dtoToEntityDischargeList(DischargeListCreateDto dischargeListCreateDto){
        DischargeList dischargeList = new DischargeList();
        dischargeList.setAnalysis(dischargeListCreateDto.getAnalysis());
        dischargeList.setAnamnesis(dischargeList.getAnamnesis());
        dischargeList.setDied(dischargeListCreateDto.isDied());
        dischargeList.setCreation(dischargeListCreateDto.getCreation());
        dischargeList.setCourseOfDisease(dischargeListCreateDto.getCourseOfDisease());
        dischargeList.setFollowingDiagnosis(dischargeListCreateDto.getFollowingDiagnosis());
        dischargeList.setSummary(dischargeListCreateDto.getSummary());
        dischargeList.setTherapy(dischargeListCreateDto.getTherapy());

        return dischargeList;
    }
}
