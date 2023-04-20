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
import raf.bolnica1.patient.domain.constants.CountryCode;
import raf.bolnica1.patient.domain.constants.Gender;
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

    //Testovi za findGeneralMedicalDataByLbp
    @Test
    public void testFindGeneralMedicalDataByLbpSuccces() {
        String lbp = "123456";
        Patient patient = createPatietnt();
        patient.setLbp(lbp);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        generalMedicalData.setBloodType("A");
        medicalRecord.setGeneralMedicalData(generalMedicalData);
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));

        List<Object[]> vaccinationsAndDates = new ArrayList<>();
        Object[] vaccination1 = new Object[]{"Flu shot", Date.valueOf("2011-11-11")};
        vaccinationsAndDates.add(vaccination1);
        Object[] vaccination2 = new Object[]{"COVID vaccine", Date.valueOf("2011-12-11")};
        vaccinationsAndDates.add(vaccination2);
        when(vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData)).thenReturn(vaccinationsAndDates);

        List<Allergy> allergies = new ArrayList<>();
        Allergy allergy1 = new Allergy();
        allergy1.setName("Peanuts");
        Allergy allergy2 = new Allergy();
        allergy2.setName("Penicillin");
        allergies.add(allergy1);
        allergies.add(allergy2);
        when(allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData)).thenReturn(allergies);

        GeneralMedicalDataDto generalMedicalDataDto = new GeneralMedicalDataDto();
        generalMedicalDataDto.setBloodType("A");

        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setName("Flu shot");
        vaccinationDto1.setVaccinationDate(Date.valueOf("2011-11-11"));
        VaccinationDto vaccinationDto2 = new VaccinationDto();
        vaccinationDto2.setName("COVID vaccine");
        vaccinationDto2.setVaccinationDate(Date.valueOf("2011-12-11"));
        List<VaccinationDto> vaccinationDtos = new ArrayList<>();
        vaccinationDtos.add(vaccinationDto1);
        vaccinationDtos.add(vaccinationDto2);

        AllergyDto allergyDto1 = new AllergyDto();
        allergyDto1.setName("Peanuts");
        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto2.setName("Penicillin");
        List<AllergyDto> allergyDtos = new ArrayList<>();
        allergyDtos.add(allergyDto1);
        allergyDtos.add(allergyDto2);

        generalMedicalDataDto.setVaccinationDtos(vaccinationDtos);
        generalMedicalDataDto.setAllergyDtos(allergyDtos);
        when(generalMedicalDataMapper.toDto(generalMedicalData,vaccinationsAndDates,allergies)).thenReturn(generalMedicalDataDto);

        GeneralMedicalDataDto result = findInfoService.findGeneralMedicalDataByLbp(lbp);

        /*
        System.out.println(generalMedicalData.getBloodType());
        System.out.println(result.getBloodType());
        */

        assertEquals(generalMedicalData.getBloodType(), result.getBloodType());

        assertEquals(vaccinationsAndDates.size(), result.getVaccinationDtos().size());
        assertEquals(vaccination1[0], result.getVaccinationDtos().get(0).getName());
        assertEquals(vaccination2[0], result.getVaccinationDtos().get(1).getName());
        assertEquals(allergies.size(), result.getAllergyDtos().size());
        assertEquals(allergy1.getName(), result.getAllergyDtos().get(0).getName());
        assertEquals(allergy2.getName(), result.getAllergyDtos().get(1).getName());
    }

    @Test
    public void testFindGeneralMedicalDataByLbpNull() {
        String lbp = "123456";
        Patient patient = createPatietnt();
        patient.setLbp(lbp);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(new MedicalRecord()));

        GeneralMedicalDataDto result = findInfoService.findGeneralMedicalDataByLbp(lbp);

        assertNull(result);
    }
    @Test
    public void testFindGeneralMedicalDataByLbpThrowsRuntimeException() {
        String lbp = "123456";
        Patient patient = createPatietnt();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findInfoService.findGeneralMedicalDataByLbp(lbp));
    }

    //Testovi za findOperationsByLbp
    @Test
    public void testFindOperationsByLbp() {
        String lbp = "123456";
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        List<Operation> operations = createOperations();
        List<OperationDto> expectedDtos = createOperationDtos();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(operationRepository.findOperationsByMedicalRecord(medicalRecord)).thenReturn(operations);
        when(operationMapper.toDto(operations)).thenReturn(expectedDtos);

        List<OperationDto> resultDtos = findInfoService.findOperationsByLbp(lbp);

        assertEquals(expectedDtos, resultDtos);
    }

    @Test
    public void testFindOperationsByLbpWhenPatientDoesNotExist() {
        String lbp = "123456";

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findInfoService.findOperationsByLbp(lbp));
    }

    @Test
    public void testFindOperationsByLbpWhenPatientExistsButHasNoMedicalRecord() {
        String lbp = "123456";
        Patient patient = createPatietnt();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findInfoService.findOperationsByLbp(lbp));
    }


    // Testovi za findMedicalHistoryByLbp
    @Test
    public void testFindMedicalHistoryByLbp() {
        String lbp = "123456";
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        List<MedicalHistory> medicalHistories = createMedicalHistories();
        List<MedicalHistoryDto> medicalHistoryDtos = createMedicalHistoryDtos();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findMedicalHistoryByMedicalRecord(medicalRecord)).thenReturn(medicalHistories);
        when(medicalHistoryMapper.toDto(medicalHistories)).thenReturn(medicalHistoryDtos);


        List<MedicalHistoryDto> result = findInfoService.findMedicalHistoryByLbp(lbp);

        assertNotNull(result);
        assertEquals(2, result.size());


        assertEquals(medicalHistories.get(0).getCurrStateDesc(), medicalHistoryDtos.get(0).getCurrStateDesc());
        assertEquals(medicalHistories.get(0).getStartDate(), medicalHistoryDtos.get(0).getStartDate());

        assertEquals(medicalHistories.get(1).getCurrStateDesc(), medicalHistoryDtos.get(1).getCurrStateDesc());
        assertEquals(medicalHistories.get(1).getStartDate(), medicalHistoryDtos.get(1).getStartDate());
    }

    @Test
    public void testFindMedicalHistoryByLbpThrowsException() {
        String lbp = "123456";
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(createPatietnt()));
        when(medicalRecordRepository.findByPatient(any(Patient.class))).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            findInfoService.findMedicalHistoryByLbp(lbp);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
        assertEquals("Medical record for patient with lbp 123456 not found.", exception.getMessage());

        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(any(Patient.class));
    }

    //Testovi za find findMedicalHistoryByLbpPaged
    @Test
    public void testFindMedicalHistoryByLbpPagedThrowsExceptionIfPatientNotFound() {
        String lbp = "123456";
        int page = 0;
        int size = 10;
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            findInfoService.findMedicalHistoryByLbpPaged(lbp, page, size);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
        assertEquals("Patient with lbp 123456 not found.", exception.getMessage());

        verify(patientRepository).findByLbp(lbp);
        verifyNoMoreInteractions(medicalRecordRepository, medicalHistoryRepository);
    }

    @Test
    public void testFindExaminationHistoryByLbpPaged() {
        String lbp = "123456";
        Date startDate = new Date(1674082800000L);
        Date endDate = new Date(System.currentTimeMillis());
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        ExaminationHistory examinationHistory = createExaminationHistory();
        List<ExaminationHistory> examinationHistories = Arrays.asList(examinationHistory);
        Page<ExaminationHistory> pageExaminationHistories = new PageImpl<>(examinationHistories);

        ExaminationHistoryDto examinationHistoryDto = createExamHistoryDto();
        List<ExaminationHistoryDto> examinationHistoryDtos = Arrays.asList(examinationHistoryDto);

        Mockito.when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        Mockito.when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        Mockito.when(examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(pageable, medicalRecord, startDate, endDate)).thenReturn(pageExaminationHistories);
        Mockito.when(examinationHistoryMapper.toDto(examinationHistory)).thenReturn(examinationHistoryDto);

        Page<ExaminationHistoryDto> result = findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp, startDate, endDate, page, size);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        Mockito.verify(patientRepository, Mockito.times(1)).findByLbp(lbp);
        Mockito.verify(medicalRecordRepository, Mockito.times(1)).findByPatient(patient);
        Mockito.verify(examinationHistoryRepository, Mockito.times(1)).findExaminationHistoryByMedicalRecordAndDateRange(pageable, medicalRecord, startDate, endDate);
        Mockito.verify(examinationHistoryMapper, Mockito.times(1)).toDto(examinationHistory);
    }

    //Testovi za findMedicalHistoryByLbpAndDiagnosisCodePaged

    @Test
    public void testFindMedicalHistoryByLbpAndDiagnosisCodePaged() {
        String lbp = "123456";
        String code = "test-code";
        int page = 0;
        int size = 10;
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();

        List<MedicalHistory> medicalHistories = createMedicalHistories();
        Page<MedicalHistory> medicalHistoryPage = new PageImpl<>(medicalHistories);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(medicalHistoryRepository.findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(any(Pageable.class), eq(medicalRecord), eq(code))).thenReturn(medicalHistoryPage);

        Page<MedicalHistoryDto> result = findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(lbp, code, page, size);

        assertEquals(medicalHistories.size(), result.getNumberOfElements());
        assertEquals(2, result.getContent().size());
    }
    @Test
    public void testFindMedicalHistoryByLbpAndDiagnosisCodePagedWithInvalidLbp() {
        String lbp = "invalid-lbp";
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        Exception exception = null;
        try {
            findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(lbp, "test-code", 0, 10);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
        assertEquals("Patient with lbp invalid-lbp not found.", exception.getMessage());
    }

    //Tesovi za findExaminationHistoryByLbpAndDateRangePaged

    @Test
    void findExaminationHistoryByLbpAndDateRangePaged() {
        String lbp = "123456";
        Date startDate = new Date(0L);
        Date endDate = new Date(100000000000L);
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        ExaminationHistory examinationHistory = createExaminationHistory();
        ExaminationHistoryDto examinationHistoryDto = createExamHistoryDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(any(Pageable.class), eq(medicalRecord), eq(startDate), eq(endDate))).thenReturn(new PageImpl<>(Arrays.asList(examinationHistory)));
        when(examinationHistoryMapper.toDto(examinationHistory)).thenReturn(examinationHistoryDto);

        Page<ExaminationHistoryDto> result = findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp, startDate, endDate, 0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals(examinationHistoryDto, result.getContent().get(0));
        verify(patientRepository, times(1)).findByLbp(lbp);
        verify(medicalRecordRepository, times(1)).findByPatient(patient);
        verify(examinationHistoryRepository, times(1)).findExaminationHistoryByMedicalRecordAndDateRange(any(Pageable.class), eq(medicalRecord), eq(startDate), eq(endDate));
        verify(examinationHistoryMapper, times(1)).toDto(examinationHistory);
    }
    @Test
    public void testFindExaminationHistoryByLbpAndDateRangePagedThrows() {
        String lbp = "123456";
        Date startDate = Date.valueOf("2023-11-11");
        Date endDate = Date.valueOf("2023-12-11");
        int page = 0;
        int size = 10;
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(createPatietnt()));
        when(medicalRecordRepository.findByPatient(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp, startDate, endDate, page, size);
        });
    }

    //Testovi za findExaminationHistoryByLbp
    @Test
    void testFindExaminationHistoryByLbpSuccess() {
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        List<ExaminationHistory> examinationHistories = new ArrayList<>();
        ExaminationHistory examinationHistory = createExaminationHistory();
        examinationHistories.add(examinationHistory);
        List<ExaminationHistoryDto> expected = new ArrayList<>();
        ExaminationHistoryDto examinationHistoryDto = createExamHistoryDto();
        expected.add(examinationHistoryDto);

        when(patientRepository.findByLbp(anyString())).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(any())).thenReturn(Optional.of(medicalRecord));
        when(examinationHistoryRepository.findExaminationHistoryByMedicalRecord(any())).thenReturn(examinationHistories);
        when(examinationHistoryMapper.toDto(anyList())).thenReturn(expected);

        List<ExaminationHistoryDto> actual = findInfoService.findExaminationHistoryByLbp("123456");

        assertEquals(expected, actual);
        verify(patientRepository).findByLbp("123456");
        verify(medicalRecordRepository).findByPatient(patient);
        verify(examinationHistoryRepository).findExaminationHistoryByMedicalRecord(medicalRecord);
        verify(examinationHistoryMapper).toDto(examinationHistories);
    }
    @Test
    void testFindExaminationHistoryByLbpPatientNotFound() {
        when(patientRepository.findByLbp(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findInfoService.findExaminationHistoryByLbp("123456"));
        verify(patientRepository).findByLbp("123456");
        verifyNoInteractions(medicalRecordRepository, examinationHistoryRepository, examinationHistoryMapper);
    }

    @Test
    void testFindExaminationHistoryByLbpMedicalRecordNotFound() {
        Patient patient = createPatietnt();
        when(patientRepository.findByLbp(anyString())).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> findInfoService.findExaminationHistoryByLbp("123456"));
        verify(patientRepository).findByLbp("123456");
        verify(medicalRecordRepository).findByPatient(patient);
        verifyNoInteractions(examinationHistoryRepository, examinationHistoryMapper);
    }

    //Testovi za findPatientByLbp
    @Test
    void testFindPatientByLbpSuccess() {
        Patient patient = createPatietnt();
        PatientDto expected = createPatientDto();
        when(patientMapper.patientToPatientDto(any())).thenReturn(expected);

        PatientDto actual = findInfoService.findPatientByLbp(patient);

        assertNotNull(actual);
        assertEquals(actual.getLbp(), expected.getLbp());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getGuardianJmbg(), expected.getGuardianJmbg());
    }

    //Testovi za findMedicalRecordByLbp

    @Test
    void testFindMedicalRecordByLbpSuccess() {
        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        MedicalRecordDto expected = createMedicalRecordDto();
        when(patientRepository.findByLbp(anyString())).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(any())).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDto(any(), any(), any(), any(), any(), any())).thenReturn(expected);

        MedicalRecordDto actual = findInfoService.findMedicalRecordByLbp("123456");

        assertEquals(expected, actual);
    }

    @Test
    public void testFindMedicalRecordByLbp_PatientNotFound_ExceptionThrown() {
        String lbp = "123456789";
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            findInfoService.findMedicalRecordByLbp(lbp);
        });
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository, never()).findByPatient(any());
        verify(medicalRecordMapper, never()).toDto(any(), any(), any(), any(), any(), any());
    }

    @Test
    public void testFindMedicalRecordByLbp_MedicalRecordNotFound_ExceptionThrown() {
        String lbp = "123456789";
        Patient patient = new Patient();
        patient.setLbp(lbp);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            findInfoService.findMedicalRecordByLbp(lbp);
        });
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(medicalRecordMapper, never()).toDto(any(), any(), any(), any(), any(), any());
    }

    //POMOCNE FJE
    private Patient createPatietnt(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("123456");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("123456");
        patient.setName("Ime");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        return patient;
    }

    private PatientDto createPatientDto(){
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1L);
        patientDto.setEmail("patient@email.com");
        patientDto.setCitizenship(CountryCode.SRB);
        patientDto.setJmbg("123456");
        patientDto.setBirthPlace("Beograd");
        patientDto.setGender(Gender.MUSKO);
        patientDto.setLbp("123456");
        patientDto.setName("Ime");
        patientDto.setSurname("Prezimovic");
        patientDto.setPhone("06385113547");
        patientDto.setDateOfBirth(Date.valueOf("2011-11-11"));
        patientDto.setPlaceOfLiving("Beograd");
        patientDto.setGuardianJmbg("10987654321");
        patientDto.setGuardianNameAndSurname("Marko Markovic");
        patientDto.setDeleted(false);


        return patientDto;
    }

    private MedicalRecord createMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setPatient(createPatietnt());
        medicalRecord.setRegistrationDate(Date.valueOf("2010-12-11"));
        medicalRecord.setDeleted(false);

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        generalMedicalData.setId(1L);
        generalMedicalData.setBloodType("A");
        generalMedicalData.setRH("RH");
        medicalRecord.setGeneralMedicalData(generalMedicalData);
        return medicalRecord;
    }
    private MedicalRecordDto createMedicalRecordDto(){
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setId(1L);
        medicalRecordDto.setPatient(createPatientDto());
        medicalRecordDto.setRegistrationDate(Date.valueOf("2010-12-11"));

        return medicalRecordDto;
    }

    private ArrayList<Operation> createOperations(){
        ArrayList<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation();
        operation1.setOperationDate(Date.valueOf("2023-12-11"));
        operation1.setId(1L);
        operation1.setDescription("Description1");
        operation1.setDepartmentId(1L);
        operation1.setHospitalId(1L);

        Operation operation2 = new Operation();
        operation2.setOperationDate(Date.valueOf("2023-12-12"));
        operation2.setId(2L);
        operation2.setDescription("Description2");
        operation2.setDepartmentId(2L);
        operation2.setHospitalId(2L);

        operations.add(operation1);
        operations.add(operation2);
        return operations;
    }

    private ArrayList<OperationDto> createOperationDtos(){
        ArrayList<OperationDto> operations = new ArrayList<>();
        OperationDto operationDto1 = new OperationDto();
        operationDto1.setOperationDate(Date.valueOf("2023-12-11"));
        operationDto1.setId(1L);
        operationDto1.setDescription("Description1");
        operationDto1.setDepartmentId(1L);
        operationDto1.setHospitalId(1L);

        OperationDto operationDto2 = new OperationDto();
        operationDto2.setOperationDate(Date.valueOf("2023-12-12"));
        operationDto2.setId(2L);
        operationDto2.setDescription("Description2");
        operationDto2.setDepartmentId(2L);
        operationDto2.setHospitalId(2L);

        operations.add(operationDto1);
        operations.add(operationDto2);
        return operations;
    }

    private ArrayList<MedicalHistory> createMedicalHistories(){
        ArrayList<MedicalHistory> medicalHistories = new ArrayList<>();
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1L);
        medicalHistory1.setStartDate(Date.valueOf("2023-09-12"));
        medicalHistory1.setCurrStateDesc("CurrentState1");
        medicalHistory1.setValid(true);
        medicalHistory1.setConfidential(false);

        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(2L);
        medicalHistory2.setStartDate(Date.valueOf("2023-08-12"));
        medicalHistory2.setCurrStateDesc("CurrentState2");
        medicalHistory2.setValid(true);
        medicalHistory2.setConfidential(false);


        medicalHistories.add(medicalHistory1);
        medicalHistories.add(medicalHistory2);
        return medicalHistories;
    }

    private ArrayList<MedicalHistoryDto> createMedicalHistoryDtos(){
        ArrayList<MedicalHistoryDto> medicalHistories = new ArrayList<>();
        MedicalHistoryDto medicalHistoryDto1 = new MedicalHistoryDto();
        medicalHistoryDto1.setStartDate(Date.valueOf("2023-09-12"));
        medicalHistoryDto1.setCurrStateDesc("CurrentState1");
        medicalHistoryDto1.setValid(true);


        MedicalHistoryDto medicalHistoryDto2 = new MedicalHistoryDto();
        medicalHistoryDto2.setStartDate(Date.valueOf("2023-08-12"));
        medicalHistoryDto2.setCurrStateDesc("CurrentState2");
        medicalHistoryDto2.setValid(true);

        medicalHistories.add(medicalHistoryDto1);
        medicalHistories.add(medicalHistoryDto2);
        return medicalHistories;
    }
    private ExaminationHistory createExaminationHistory(){
        ExaminationHistory examinationHistory = new ExaminationHistory();

        DiagnosisCode diagnosisCode = new DiagnosisCode();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setLatinDescription("Latin Description");
        diagnosisCode.setId(1L);

        examinationHistory.setDiagnosisCode(diagnosisCode);

        examinationHistory.setAdvice("Advice");
        examinationHistory.setExamDate(Date.valueOf("2023-12-11"));
        examinationHistory.setId(1L);
        examinationHistory.setConfidential(false);
        examinationHistory.setLbz("LBZ");
        examinationHistory.setObjectiveFinding("Objective finding");
        examinationHistory.setTherapy("Therapy");


        return examinationHistory;
    }

    private ExaminationHistoryDto createExamHistoryDto(){
        ExaminationHistoryDto examinationHistoryDto = new ExaminationHistoryDto();

        DiagnosisCodeDto diagnosisCode = new DiagnosisCodeDto();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setLatinDescription("Latin Description");

        examinationHistoryDto.setDiagnosisCodeDto(diagnosisCode);

        examinationHistoryDto.setAdvice("Advice");
        examinationHistoryDto.setExamDate(Date.valueOf("2023-12-11"));
        examinationHistoryDto.setId(1L);
        examinationHistoryDto.setConfidential(false);
        examinationHistoryDto.setLbz("LBZ");
        examinationHistoryDto.setObjectiveFinding("Objective finding");
        examinationHistoryDto.setTherapy("Therapy");


        //System.out.println(examinationHistoryDto.getDiagnosisCodeDto().getCode());
        return examinationHistoryDto;
    }
}
