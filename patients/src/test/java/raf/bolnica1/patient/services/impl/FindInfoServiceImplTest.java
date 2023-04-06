package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;

import java.util.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindInfoServiceImplTest {

    @InjectMocks
    private FindInfoServiceImpl findInfoService;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private VaccinationDataRepository vaccinationDataRepository;
    @Mock
    private AllergyDataRepository allergyDataRepository;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;
    @Mock
    private ExaminationHistoryRepository examinationHistoryRepository;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;
    @Mock
    private GeneralMedicalDataMapper generalMedicalDataMapper;
    @Mock
    private OperationMapper operationMapper;
    @Mock
    private MedicalHistoryMapper medicalHistoryMapper;
    @Mock
    private ExaminationHistoryMapper examinationHistoryMapper;

    public FindInfoServiceImplTest() {
    }

    @Test
    public void testFindGeneralMedicalDataByLbp() {
        // Mocking dependencies
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        List<Object[]> vaccinationsAndDates = new ArrayList<>();
        List<Allergy> allergies = new ArrayList<>();
        GeneralMedicalDataDto expectedDto = new GeneralMedicalDataDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecord.getGeneralMedicalData()).thenReturn(generalMedicalData);
        when(vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData)).thenReturn(vaccinationsAndDates);
        when(allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData)).thenReturn(allergies);
        when(generalMedicalDataMapper.toDto(generalMedicalData, vaccinationsAndDates, allergies)).thenReturn(expectedDto);

        // Call the method being tested
        GeneralMedicalDataDto resultDto = findInfoService.findGeneralMedicalDataByLbp(lbp);

        // Assert the result
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testFindOperationsByLbp() {
        // Mocking dependencies
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        List<Operation> operations = new ArrayList<>();
        List<OperationDto> expectedDtos = new ArrayList<>();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(operationRepository.findOperationsByMedicalRecord(medicalRecord)).thenReturn(operations);
        when(operationMapper.toDto(operations)).thenReturn(expectedDtos);

        // Call the method being tested
        List<OperationDto> resultDtos = findInfoService.findOperationsByLbp(lbp);

        // Assert the result
        assertEquals(expectedDtos, resultDtos);
    }

    @Test
    public void testFindMedicalHistoryByLbp() {
        // Mocking
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        MedicalHistory medicalHistory1 = new MedicalHistory();
        MedicalHistory medicalHistory2 = new MedicalHistory();
        List<MedicalHistory> medicalHistories = Arrays.asList(medicalHistory1, medicalHistory2);
        MedicalHistoryDto medicalHistoryDto1 = new MedicalHistoryDto();
        MedicalHistoryDto medicalHistoryDto2 = new MedicalHistoryDto();
        List<MedicalHistoryDto> expected = Arrays.asList(medicalHistoryDto1, medicalHistoryDto2);

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findMedicalHistoryByMedicalRecord(medicalRecord)).thenReturn(medicalHistories);
        when(medicalHistoryMapper.toDto(medicalHistory1)).thenReturn(medicalHistoryDto1);
        when(medicalHistoryMapper.toDto(medicalHistory2)).thenReturn(medicalHistoryDto2);

        // Test
        List<MedicalHistoryDto> result = findInfoService.findMedicalHistoryByLbp(lbp);

        // Verify
        assertEquals(expected, result);
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(medicalHistoryRepository).findMedicalHistoryByMedicalRecord(medicalRecord);
        verify(medicalHistoryMapper).toDto(medicalHistory1);
        verify(medicalHistoryMapper).toDto(medicalHistory2);
    }

    public void testFindMedicalHistoryByLbpPaged() {
        // Mocking data
        String lbp = "123456789";
        int page = 0;
        int size = 10;
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        Page<MedicalHistory> medicalHistories = new PageImpl<>(Arrays.asList(new MedicalHistory(), new MedicalHistory()));
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findMedicalHistoryByMedicalRecordPaged(any(Pageable.class), eq(medicalRecord)))
                .thenReturn(medicalHistories);
        when(medicalHistoryMapper.toDto(any(MedicalHistory.class))).thenReturn(new MedicalHistoryDto());

        // Call the method to be tested
        Page<MedicalHistoryDto> result = findInfoService.findMedicalHistoryByLbpPaged(lbp, page, size);

        // Assertions
        assertNotNull(result);
        assertEquals(medicalHistories.getTotalElements(), result.getTotalElements());
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(medicalHistoryRepository).findMedicalHistoryByMedicalRecordPaged(any(Pageable.class), eq(medicalRecord));
        verify(medicalHistoryMapper, times(medicalHistories.getContent().size())).toDto(any(MedicalHistory.class));
    }

    @Test
    public void testFindMedicalHistoryByLbpAndDiagnosisCodePaged() {
        // Mocking input parameters
        String lbp = "123456789";
        String code = "A01";
        int page = 0;
        int size = 10;

        // Mocking repository methods
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        Page<MedicalHistory> medicalHistories = new PageImpl<>(new ArrayList<>());
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(any(Pageable.class), eq(medicalRecord), eq(code))).thenReturn(medicalHistories);
        when(medicalHistoryMapper.toDto(any(MedicalHistory.class))).thenReturn(new MedicalHistoryDto());

        // Call the method being tested
        Page<MedicalHistoryDto> result = findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(lbp, code, page, size);

        // Assertions
        assertNotNull(result);
        verify(patientRepository, times(1)).findByLbp(lbp);
        verify(medicalRecordRepository, times(1)).findByPatient(patient);
        verify(medicalHistoryRepository, times(1)).findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(any(Pageable.class), eq(medicalRecord), eq(code));
        verify(medicalHistoryMapper, atLeast(1)).toDto(any(MedicalHistory.class));
    }

    @Test
    public void testFindExaminationHistoryByLbpAndDateRangePaged() {
        // Mock data
        String lbp = "12345";
        //Treba dopuniti
        Date startDate = new Date(1674082800000L);
        Date endDate = new Date(System.currentTimeMillis());
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        ExaminationHistory examinationHistory = new ExaminationHistory();
        List<ExaminationHistory> examinationHistories = Arrays.asList(examinationHistory);
        Page<ExaminationHistory> pageExaminationHistories = new PageImpl<>(examinationHistories);

        // Mock repository methods
        Mockito.when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        Mockito.when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        Mockito.when(examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(pageable, medicalRecord, startDate, endDate)).thenReturn(pageExaminationHistories);
        Mockito.when(examinationHistoryMapper.toDto(examinationHistory)).thenReturn(new ExaminationHistoryDto());

        // Call the method
        Page<ExaminationHistoryDto> result = findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp, startDate, endDate, page, size);

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        Mockito.verify(patientRepository, Mockito.times(1)).findByLbp(lbp);
        Mockito.verify(medicalRecordRepository, Mockito.times(1)).findByPatient(patient);
        Mockito.verify(examinationHistoryRepository, Mockito.times(1)).findExaminationHistoryByMedicalRecordAndDateRange(pageable, medicalRecord, startDate, endDate);
        Mockito.verify(examinationHistoryMapper, Mockito.times(1)).toDto(examinationHistory);
    }

    @Test
    public void testFindExaminationHistoryByLbp() {
        // Arrange
        String lbp = "12345";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        List<ExaminationHistory> examinationHistories = new ArrayList<>();
        ExaminationHistory examinationHistory1 = new ExaminationHistory();
        examinationHistories.add(examinationHistory1);
        ExaminationHistory examinationHistory2 = new ExaminationHistory();
        examinationHistories.add(examinationHistory2);
        when(patientRepository.findByLbp(eq(lbp))).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(eq(patient))).thenReturn(Optional.of(medicalRecord));
        when(examinationHistoryRepository.findExaminationHistoryByMedicalRecord(eq(medicalRecord))).thenReturn(examinationHistories);
        when(examinationHistoryMapper.toDto(eq(examinationHistory1))).thenReturn(new ExaminationHistoryDto());
        when(examinationHistoryMapper.toDto(eq(examinationHistory2))).thenReturn(new ExaminationHistoryDto());

        // Act
        List<ExaminationHistoryDto> result = findInfoService.findExaminationHistoryByLbp(lbp);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findByLbp(eq(lbp));
        verify(medicalRecordRepository, times(1)).findByPatient(eq(patient));
        verify(examinationHistoryRepository, times(1)).findExaminationHistoryByMedicalRecord(eq(medicalRecord));
        verify(examinationHistoryMapper, times(1)).toDto(eq(examinationHistory1));
        verify(examinationHistoryMapper, times(1)).toDto(eq(examinationHistory2));
    }

    @Test
    public void testFindPatientByLbp() {
        // Arrange
        Patient patient = new Patient();
        PatientDto patientDto = new PatientDto();
        when(patientMapper.patientToPatientDto(eq(patient))).thenReturn(patientDto);

        // Act
        PatientDto result = findInfoService.findPatientByLbp(patient);

        // Assert
        assertNotNull(result);
        assertEquals(patientDto, result);
        verify(patientMapper, times(1)).patientToPatientDto(eq(patient));
    }

    @Test
    public void testFindMedicalRecordByLbp_Success() {
        // Arrange
        String lbp = "123456789";
        Patient patient = new Patient();
        patient.setLbp(lbp);
        MedicalRecord medicalRecord = new MedicalRecord();
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDto(any(), any(), any(), any(), any(), any())).thenReturn(new MedicalRecordDto());

        // Act
        MedicalRecordDto result = findInfoService.findMedicalRecordByLbp(lbp);

        // Assert
        assertNotNull(result);
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(medicalRecordMapper).toDto(eq(medicalRecord), any(), any(), any(), any(), any());
    }

    @Test
    public void testFindMedicalRecordByLbp_PatientNotFound_ExceptionThrown() {
        // Arrange
        String lbp = "123456789";
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            findInfoService.findMedicalRecordByLbp(lbp);
        });
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository, never()).findByPatient(any());
        verify(medicalRecordMapper, never()).toDto(any(), any(), any(), any(), any(), any());
    }

    @Test
    public void testFindMedicalRecordByLbp_MedicalRecordNotFound_ExceptionThrown() {
        // Arrange
        String lbp = "123456789";
        Patient patient = new Patient();
        patient.setLbp(lbp);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            findInfoService.findMedicalRecordByLbp(lbp);
        });
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(medicalRecordMapper, never()).toDto(any(), any(), any(), any(), any(), any());
    }
}
