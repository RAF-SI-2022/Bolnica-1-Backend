package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.ExaminationStatus;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.mapper.AnamnesisMapper;
import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalExaminationService;

import java.util.Date;
import java.util.Optional;

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

    private ScheduleExamRepository scheduleExamRepository;

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
    public MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryCreateDto medicalHistoryCreateDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));

        MedicalHistory medicalHistory;

        MedicalHistory prevMedicalHistory = null;
        if(medicalHistoryCreateDto.isExists()){
            prevMedicalHistory = medicalHistoryRepository.findPrev(medicalHistoryCreateDto.getDiagnosisCodeDto().getCode(), medicalRecord.getId()).orElse(null);

        }

        if(prevMedicalHistory != null) {
            medicalHistory = medicalHistoryMapper.getMedicalHistoryFromCreateDtoExist(medicalHistoryCreateDto, prevMedicalHistory);
            prevMedicalHistory.setValidTo(new java.sql.Date(new Date().getTime()));
            prevMedicalHistory.setValid(false);
        }
        else {
            medicalHistory = medicalHistoryMapper.getMedicalHistoryFromCreateDtoNoExist(medicalHistoryCreateDto);
        }
        medicalHistory.setMedicalRecord(medicalRecord);
        return medicalHistoryMapper.toDto(medicalHistoryRepository.save(medicalHistory));
    }

    @Override
    public Object findScheduledExamination(Object object) {
        return null;
    }


    @Override
    public Object updateExaminationStatus(Object object) {
        return null;
    }

    @Override
    public MessageDto deleteScheduledExamination(Long id) {
        scheduleExamRepository.deleteScheduleExamById(id).orElseThrow(() -> new RuntimeException(String.format("Scheduled exam with id %d not found.", id)));
        return new MessageDto(String.format("Scheduled exam with id %d deleted",id));
    }

    @Override
    public Object findDoctorSpecByDepartment(Object object) {
        return null;
    }




}
