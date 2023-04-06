package raf.bolnica1.patient.services.impl;

import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.general.AllergyDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;
import raf.bolnica1.patient.dto.general.VaccinationDto;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceImplTest {

    /*@InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Mock
    private GeneralMedicalDataMapper generalMedicalDataMapper;
    @Mock
    private OperationMapper operationMapper;
    @Mock
    private DiagnosisCodeRepository diagnosisCodeRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    @Mock
    private AllergyDataRepository allergyDataRepository;
    @Mock
    private VaccinationDataRepository vaccinationDataRepository;
    @Mock
    private AllergyRepository allergyRepository;
    @Mock
    private AllergyMapper allergyMapper;
    @Mock
    private VaccinationRepository vaccinationRepository;
    @Mock
    private VaccinationMapper vaccinationMapper;
    @Mock
    private DiagnosisCodeMapper diagnosisCodeMapper;
    @Mock
    private OperationRepository operationRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddGeneralMedicalData() {
        // Mocking data
        String lbp = "123456789";
        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setAllergyDtos(Arrays.asList(new AllergyDto("Allergy 1"), new AllergyDto("Allergy 2")));
        generalMedicalDataCreateDto.setVaccinationDtos(Arrays.asList(new VaccinationDto("Vaccination 1", LocalDate.now()), new VaccinationDto("Vaccination 2", LocalDate.now())));

        Patient patient = new Patient();
        patient.setLbp(lbp);
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        AllergyData allergyData1 = new AllergyData();
        AllergyData allergyData2 = new AllergyData();
        VaccinationData vaccinationData1 = new VaccinationData();
        VaccinationData vaccinationData2;
        vaccinationData2 = new VaccinationData();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto)).thenReturn(generalMedicalData);
        when(allergyRepository.findByName("Allergy 1")).thenReturn(new Allergy());
        when(allergyRepository.findByName("Allergy 2")).thenReturn(new Allergy());
        when(vaccinationRepository.findByName("Vaccination 1")).thenReturn(new Vaccination());
        when(vaccinationRepository.findByName("Vaccination 2")).thenReturn(new Vaccination());

        // Call the method
        GeneralMedicalDataDto result = medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto);

        // Assertions
        assertNotNull(result);
        assertEquals(generalMedicalData, generalMedicalDataRepository.save(generalMedicalData));
        assertEquals(allergyData1, allergyDataRepository.save(allergyData1));
        assertEquals(allergyData2, allergyDataRepository.save(allergyData2));
        assertEquals(vaccinationData1, vaccinationDataRepository.save(vaccinationData1));
        assertEquals(vaccinationData2, vaccinationDataRepository.save(vaccinationData2));
        assertEquals(generalMedicalData, medicalRecord.getGeneralMedicalData());
    }

    @Test
    public void testAddOperation() {
        // Mock data
        String lbp = "123456789";
        OperationCreateDto operationCreateDto = new OperationCreateDto();
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        Operation operation = new Operation();
        OperationDto expectedOperationDto = new OperationDto();

        // Mock repositories
        when(patientRepository.findByLbp(eq(lbp))).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(eq(patient))).thenReturn(Optional.of(medicalRecord));

        // Mock mappers
        when(operationMapper.toEntity(eq(operationCreateDto), eq(medicalRecord))).thenReturn(operation);
        when(operationMapper.toDto(eq(operation))).thenReturn(expectedOperationDto);

        // Mock operationRepository
        when(operationRepository.save(eq(operation))).thenReturn(operation);

        // Call the method
        OperationDto result = medicalRecordService.addOperation(lbp, operationCreateDto);

        // Verify the results
        verify(patientRepository, times(1)).findByLbp(eq(lbp));
        verify(medicalRecordRepository, times(1)).findByPatient(eq(patient));
        verify(operationMapper, times(1)).toEntity(eq(operationCreateDto), eq(medicalRecord));
        verify(operationRepository, times(1)).save(eq(operation));
        verify(operationMapper, times(1)).toDto(eq(operation));

        assertEquals(expectedOperationDto, result);
    }

    @Test
    public void testGatherAllergies() {
        // Arrange
        Allergy allergy1 = new Allergy();
        allergy1.setId(1L);
        allergy1.setName("Allergy1");
        Allergy allergy2 = new Allergy();
        allergy2.setId(2L);
        allergy2.setName("Allergy2");

        List<Allergy> allergies = Arrays.asList(allergy1, allergy2);
        when(allergyRepository.findAll()).thenReturn(allergies);

        AllergyDto allergyDto1 = new AllergyDto();
        allergyDto1.setId(1L);
        allergyDto1.setName("Allergy1");
        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto2.setId(2L);
        allergyDto2.setName("Allergy2");

        when(allergyMapper.toDto(allergy1)).thenReturn(allergyDto1);
        when(allergyMapper.toDto(allergy2)).thenReturn(allergyDto2);

        // Act
        List<AllergyDto> result = medicalRecordService.gatherAllergies();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(allergyDto1, result.get(0));
        assertEquals(allergyDto2, result.get(1));
    }

    @Test
    public void testGatherVaccines() {
        // Arrange
        Vaccination vaccination1 = new Vaccination();
        vaccination1.setId(1L);
        vaccination1.setName("Vaccine1");
        Vaccination vaccination2 = new Vaccination();
        vaccination2.setId(2L);
        vaccination2.setName("Vaccine2");

        List<Vaccination> vaccinations = Arrays.asList(vaccination1, vaccination2);
        when(vaccinationRepository.findAll()).thenReturn(vaccinations);

        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setId(1L);
        vaccinationDto1.setName("Vaccine1");
        VaccinationDto vaccinationDto2 = new VaccinationDto();
        vaccinationDto2.setId(2L);
        vaccinationDto2.setName("Vaccine2");

        when(vaccinationMapper.toDto(vaccination1)).thenReturn(vaccinationDto1);
        when(vaccinationMapper.toDto(vaccination2)).thenReturn(vaccinationDto2);

        // Act
        List<VaccinationDto> result = medicalRecordService.gatherVaccines();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vaccinationDto1, result.get(0));
        assertEquals(vaccinationDto2, result.get(1));
    }

    @Test
    public void testGatherDiagnosis() {
        // Prepare test data
        DiagnosisCode diagnosisCode1 = new DiagnosisCode();
        diagnosisCode1.setId(1L);
        diagnosisCode1.setCode("D001");
        diagnosisCode1.setDescription("Test Diagnosis 1");

        DiagnosisCode diagnosisCode2 = new DiagnosisCode();
        diagnosisCode2.setId(2L);
        diagnosisCode2.setCode("D002");
        diagnosisCode2.setDescription("Test Diagnosis 2");

        List<DiagnosisCode> diagnosisCodes = Arrays.asList(diagnosisCode1, diagnosisCode2);

        DiagnosisCodeDto diagnosisCodeDto1 = new DiagnosisCodeDto();
        diagnosisCodeDto1.setId(1L);
        diagnosisCodeDto1.setCode("D001");
        diagnosisCodeDto1.setDescription("Test Diagnosis 1");

        DiagnosisCodeDto diagnosisCodeDto2 = new DiagnosisCodeDto();
        diagnosisCodeDto2.setId(2L);
        diagnosisCodeDto2.setCode("D002");
        diagnosisCodeDto2.setDescription("Test Diagnosis 2");

        when(diagnosisCodeRepository.findAll()).thenReturn(diagnosisCodes);
        when(diagnosisCodeMapper.toDto(diagnosisCode1)).thenReturn(diagnosisCodeDto1);
        when(diagnosisCodeMapper.toDto(diagnosisCode2)).thenReturn(diagnosisCodeDto2);

        // Perform the test
        List<DiagnosisCodeDto> diagnosisCodeDtos = medicalRecordService.gatherDiagnosis();

        // Assertions
        assertNotNull(diagnosisCodeDtos);
        assertEquals(2, diagnosisCodeDtos.size());
        assertEquals(diagnosisCodeDto1, diagnosisCodeDtos.get(0));
        assertEquals(diagnosisCodeDto2, diagnosisCodeDtos.get(1));

        // Verify the interactions with mocked dependencies
        verify(diagnosisCodeRepository, times(1)).findAll();
        verify(diagnosisCodeMapper, times(1)).toDto(diagnosisCode1);
        verify(diagnosisCodeMapper, times(1)).toDto(diagnosisCode2);
    }

    @Test
    public void testAddVaccine_Success() {
        // Prepare test data
        String lbp = "123456";
        VaccinationDataDto vaccinationDataDto = new VaccinationDataDto();
        vaccinationDataDto.setVaccinationName("Flu Vaccine");
        vaccinationDataDto.setVaccinationDate(LocalDate.now());

        Vaccination vaccination = new Vaccination();
        vaccination.setId(1L);
        vaccination.setName("Flu Vaccine");

        Patient patient = new Patient();
        patient.setLbp(lbp);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patient);

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        generalMedicalData.setId(1L);
        generalMedicalData.setMedicalRecord(medicalRecord);

        VaccinationData vaccinationData = new VaccinationData();
        vaccinationData.setVaccination(vaccination);
        vaccinationData.setGeneralMedicalData(generalMedicalData);
        vaccinationData.setDeleted(false);
        vaccinationData.setVaccinationDate(vaccinationDataDto.getVaccinationDate());

        when(vaccinationRepository.findByName(vaccinationDataDto.getVaccinationName())).thenReturn(vaccination);
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(vaccinationDataRepository.save(any(VaccinationData.class))).thenReturn(vaccinationData);

        // Perform the test
        MessageDto result = medicalRecordService.addVaccine(lbp, vaccinationDataDto);

        // Assertions
        assertNotNull(result);
        assertEquals("Uspesno dodata vakcina.", result.getMessage());

        // Verify the interactions with mocked dependencies
        verify(vaccinationRepository, times(1)).findByName(vaccinationDataDto.getVaccinationName());
        verify(patientRepository, times(1)).findByLbp(lbp);
        verify(medicalRecordRepository, times(1)).findByPatient(patient);
        verify(vaccinationDataRepository, times(1)).save(any(VaccinationData.class));
    }

    @Test
    public void testAddVaccine_Failure_PatientNotFound() {
        // Prepare test data
        String lbp = "123456";
        VaccinationDataDto vaccinationDataDto = new VaccinationDataDto();
        vaccinationDataDto.setVaccinationName("Flu Vaccine");
        vaccinationDataDto.setVaccinationDate(LocalDate.now());

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        // Perform the test
        MessageDto result = medicalRecordService.addVaccine(lbp, vaccinationDataDto);

        // Assertions
        assertNotNull(result);
        assertEquals("Neuspesno dodata vakcina.", result.getMessage());

        // Verify the interactions with mocked dependencies
        verify(vaccinationRepository, never()).findByName(anyString());
        verify(patientRepository, times(1)).findByLbp(lbp);
        verify(medicalRecordRepository, never()).findByPatient(any(Patient.class));
        verify(vaccinationDataRepository, never()).save(any(VaccinationData.class));
    }

    @Test
    public void testAddAllergy_Successful() {
        // Mocking dependencies
        String lbp = "123456789";
        String allergyName = "Pollen";
        Patient patient = new Patient();
        patient.setLbp(lbp);
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        Allergy allergy = new Allergy();
        AllergyData allergyData = new AllergyData();
        MessageDto expectedMessageDto = new MessageDto("Uspesno dodata alergija.");

        Mockito.when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        Mockito.when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        Mockito.when(medicalRecord.getGeneralMedicalData()).thenReturn(generalMedicalData);
        Mockito.when(allergyRepository.findByName(allergyName)).thenReturn(allergy);
        Mockito.when(allergyDataRepository.save(Mockito.any(AllergyData.class))).thenReturn(allergyData);

        // Test
        MessageDto result = medicalRecordService.addAllergy(lbp, allergyName);

        // Verification
        Assert.assertEquals(expectedMessageDto.getMessage(), result.getMessage());
        Mockito.verify(allergyDataRepository, Mockito.times(1)).save(Mockito.any(AllergyData.class));
    }

    @Test
    public void testAddAllergy_PatientNotFound() {
        // Mocking dependencies
        String lbp = "123456789";
        String allergyName = "Pollen";
        MessageDto expectedMessageDto = new MessageDto("Neuspesno dodata alergija.");

        Mockito.when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        // Test
        MessageDto result = medicalRecordService.addAllergy(lbp, allergyName);

        // Verification
        Assert.assertEquals(expectedMessageDto.getMessage(), result.getMessage());
        Mockito.verify(allergyDataRepository, Mockito.times(0)).save(Mockito.any(AllergyData.class));
    }

    @Test
    public void testAddAllergy_MedicalRecordNotFound() {

    }*/

}