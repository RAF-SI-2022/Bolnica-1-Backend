package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.mapper.AnamnesisMapper;
import raf.bolnica1.patient.mapper.DiagnosisCodeMapper;
import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalExaminationService;

@Service
@Transactional
@AllArgsConstructor
public class MedicalExaminationServiceImpl implements MedicalExaminationService {

    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private ExaminationHistoryRepository examinationHistoryRepository;
    private MedicalHistoryRepository medicalHistoryRepository;
    private AnamnesisRepository anamnesisRepository;
    private AnamnesisMapper anamnesisMapper;
    private DiagnosisCodeRepository diagnosisCodeRepository;
    private ExaminationHistoryMapper examinationHistoryMapper;
    private MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public ExaminationHistoryDto addExamination(String lbp, ExaminationHistoryCreateDto examinationHistoryCreateDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));

        ExaminationHistory examinationHistory = examinationHistoryMapper.toEntity(examinationHistoryCreateDto);
        examinationHistory.setMedicalRecord(medicalRecord);

        /// postaviti anamnezu i dijagnozu
        Anamnesis anamnesis = anamnesisMapper.toEntity(examinationHistoryCreateDto.getAnamnesisDto());
        anamnesis = anamnesisRepository.save(anamnesis);
        examinationHistory.setAnamnesis(anamnesis);

        DiagnosisCode diagnosisCode = diagnosisCodeRepository.findByCode(examinationHistoryCreateDto.getDiagnosisCodeDto().getCode());
        examinationHistory.setDiagnosisCode(diagnosisCode);

        return examinationHistoryMapper.toDto(examinationHistoryRepository.save(examinationHistory));
    }

    @Override
    public MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryDto medicalHistoryDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));

        MedicalHistory medicalHistory = medicalHistoryMapper.toEntity(medicalHistoryDto);
        medicalHistory.setMedicalRecord(medicalRecord);

        MedicalHistory checkHistory = medicalHistoryRepository.findByDiagnosisCodeAndStartDate(medicalHistoryDto.getDiagnosisCodeDto().getCode(), medicalHistoryDto.getStartDate()).orElse(null);
        if(checkHistory == null) {
            MedicalHistory prevMedicalHistory = medicalHistoryRepository.findByDiagnosisCodeAndStartDateAndValid(medicalHistoryDto.getDiagnosisCodeDto().getCode(), medicalHistoryDto.getStartDate()).orElse(null);
            if (prevMedicalHistory != null) {
                prevMedicalHistory.setValidTo(medicalHistoryDto.getValidFrom());
                prevMedicalHistory.setValid(false);
            }
            medicalHistory.setValid(true);
        }
        else
            medicalHistory.setValid(false);

        return medicalHistoryMapper.toDto(medicalHistoryRepository.save(medicalHistory));
    }
}
