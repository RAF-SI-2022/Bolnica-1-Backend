package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.mapper.AnamnesisMapper;
import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalExaminationServiceImplTest {

    @InjectMocks
    private MedicalExaminationServiceImpl medicalExaminationService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private ExaminationHistoryRepository examinationHistoryRepository;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @Mock
    private AnamnesisRepository anamnesisRepository;

    @Mock
    private AnamnesisMapper anamnesisMapper;

    @Mock
    private DiagnosisCodeRepository diagnosisCodeRepository;

    @Mock
    private ExaminationHistoryMapper examinationHistoryMapper;

    @Mock
    private MedicalHistoryMapper medicalHistoryMapper;

    @Mock
    private ScheduleExamRepository scheduleExamRepository;

    @Test
    public void testAddExamination() {
        // Mock data
        String lbp = "12345678";
        ExaminationHistoryCreateDto examinationHistoryCreateDto = new ExaminationHistoryCreateDto();
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        Anamnesis anamnesis = new Anamnesis();
        DiagnosisCode diagnosisCode = new DiagnosisCode();
        ExaminationHistory examinationHistory = new ExaminationHistory();

        when(patientRepository.findByLbp(eq(lbp))).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(eq(patient))).thenReturn(Optional.of(medicalRecord));
        when(anamnesisMapper.toEntity(eq(examinationHistoryCreateDto.getAnamnesisDto()))).thenReturn(anamnesis);
        when(anamnesisRepository.save(eq(anamnesis))).thenReturn(anamnesis);
        when(diagnosisCodeRepository.findByCode(eq(examinationHistoryCreateDto.getDiagnosisCodeDto().getCode()))).thenReturn(diagnosisCode);
        when(examinationHistoryMapper.toDto(eq(examinationHistoryRepository.save(eq(examinationHistory))))).thenReturn(new ExaminationHistoryDto());

        // Test the method
        ExaminationHistoryDto result = medicalExaminationService.addExamination(lbp, examinationHistoryCreateDto);

        // Assertions
        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    public void testAddMedicalHistory() {
        // Mock data
        String lbp = "12345678";
        MedicalHistoryCreateDto medicalHistoryCreateDto = new MedicalHistoryCreateDto();
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        MedicalHistory prevMedicalHistory = new MedicalHistory();
        MedicalHistory medicalHistory = new MedicalHistory();

        when(patientRepository.findByLbp(eq(lbp))).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(eq(patient))).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findPrev(eq(medicalHistoryCreateDto.getDiagnosisCodeDto().getCode()), eq(medicalRecord.getId()))).thenReturn(Optional.of(prevMedicalHistory));
        when(medicalHistoryMapper.getMedicalHistoryFromCreateDtoExist(eq(medicalHistoryCreateDto), eq(prevMedicalHistory))).thenReturn(medicalHistory);
        when(medicalHistoryRepository.save(eq(medicalHistory))).thenReturn(medicalHistory);
        when(medicalHistoryMapper.toDto(eq(medicalHistory))).thenReturn(new MedicalHistoryDto());

        // Test the method
        MedicalHistoryDto result = medicalExaminationService.addMedicalHistory(lbp, medicalHistoryCreateDto);

        // Assertions
        assertNotNull(result);
        // Add more assertions as needed
    }


}
