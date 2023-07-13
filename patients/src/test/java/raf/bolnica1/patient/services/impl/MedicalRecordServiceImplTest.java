package raf.bolnica1.patient.services.impl;

import static org.mockito.ArgumentMatchers.any;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceImplTest {

    @InjectMocks
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

    //OVDE SU TESTOVI ZA addGeneralMedicalData f-ju
    @Test
    void testAddWhenPatienFoundHasRecord() {

        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();

        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setAllergyDtos(Arrays.asList(new AllergyDto("Allergy 1"), new AllergyDto("Allergy 2")));
        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setName("Vaccination 1");
        VaccinationDto vaccinationDto2 = new VaccinationDto();
        vaccinationDto2.setName("Vaccination 2");
        generalMedicalDataCreateDto.setVaccinationDtos(Arrays.asList(vaccinationDto1,vaccinationDto2));
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        GeneralMedicalDataDto generalMedicalDataDto = new GeneralMedicalDataDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto, medicalRecord.getGeneralMedicalData())).thenReturn(generalMedicalData);
        when(allergyRepository.findByName("Allergy 1")).thenReturn(new Allergy());
        when(allergyRepository.findByName("Allergy 2")).thenReturn(new Allergy());
        when(vaccinationRepository.findByName("Vaccination 1")).thenReturn(new Vaccination());
        when(vaccinationRepository.findByName("Vaccination 2")).thenReturn(new Vaccination());
        when(generalMedicalDataRepository.save(generalMedicalData)).thenReturn(generalMedicalData);
        when(generalMedicalDataMapper.toDto(generalMedicalData)).thenReturn(generalMedicalDataDto);

        GeneralMedicalDataDto result = medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto);

        assertEquals(generalMedicalDataDto, result);
        verify(generalMedicalDataRepository).save(generalMedicalData);
    }

    @Test
    void testAddWhenPatientNotFound() {
        String lbp = "123456789";
        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto));
        assertEquals(String.format("Patient with lbp %s not found.", lbp), exception.getMessage());
    }

    @Test
    void testAddWhenPatientFoundNoRecord() {
        String lbp = "123456789";
        Patient patient = new Patient();
        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto));
    }

    @Test
    void testAddHasAlergies() {
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();

        AllergyDto allergyDto1 = new AllergyDto();
        allergyDto1.setName("Allergy1");

        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto2.setName("Allergy2");

        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setAllergyDtos(Arrays.asList(allergyDto1, allergyDto2));

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        GeneralMedicalDataDto generalMedicalDataDto = new GeneralMedicalDataDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto, medicalRecord.getGeneralMedicalData())).thenReturn(generalMedicalData);
        when(generalMedicalDataRepository.save(generalMedicalData)).thenReturn(generalMedicalData);
        when(generalMedicalDataMapper.toDto(generalMedicalData)).thenReturn(generalMedicalDataDto);

        GeneralMedicalDataDto result = medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto);

        assertEquals(generalMedicalDataDto, result);
        verify(allergyDataRepository, times(2)).save(any(AllergyData.class));
    }

    @Test
    void testAddHasVaccinations() {
        // Arrange
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();

        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setName("Vaccination1");
        vaccinationDto1.setVaccinationDate(Date.valueOf("2023-11-11"));

        VaccinationDto vaccinationDto2 = new VaccinationDto();
        vaccinationDto2.setName("Vaccination2");
        vaccinationDto2.setVaccinationDate(Date.valueOf("2023-12-11"));

        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setVaccinationDtos(Arrays.asList(vaccinationDto1, vaccinationDto2));

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        GeneralMedicalDataDto generalMedicalDataDto = new GeneralMedicalDataDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto, medicalRecord.getGeneralMedicalData())).thenReturn(generalMedicalData);
        when(generalMedicalDataRepository.save(generalMedicalData)).thenReturn(generalMedicalData);
        when(generalMedicalDataMapper.toDto(generalMedicalData)).thenReturn(generalMedicalDataDto);

        // Act
        GeneralMedicalDataDto result = medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto);

        // Assert
        assertEquals(generalMedicalDataDto, result);
        verify(vaccinationDataRepository, times(2)).save(any(VaccinationData.class));
    }
    @Test
    void testAddPatientHasVaccionationsHasAlergies() {
        // Arrange
        String lbp = "123456789";
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();

        AllergyDto allergyDto1 = new AllergyDto();
        allergyDto1.setName("Allergy1");

        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto2.setName("Allergy2");

        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setName("Vaccination1");
        vaccinationDto1.setVaccinationDate(Date.valueOf("2023-11-11"));

        VaccinationDto vaccinationDto2 = new VaccinationDto();
        vaccinationDto2.setName("Vaccination2");
        vaccinationDto2.setVaccinationDate(Date.valueOf("2023-12-11"));

        GeneralMedicalDataCreateDto generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setAllergyDtos(Arrays.asList(allergyDto1, allergyDto2));
        generalMedicalDataCreateDto.setVaccinationDtos(Arrays.asList(vaccinationDto1, vaccinationDto2));

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        GeneralMedicalDataDto generalMedicalDataDto = new GeneralMedicalDataDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto, medicalRecord.getGeneralMedicalData())).thenReturn(generalMedicalData);
        when(generalMedicalDataRepository.save(generalMedicalData)).thenReturn(generalMedicalData);
        when(generalMedicalDataMapper.toDto(generalMedicalData)).thenReturn(generalMedicalDataDto);

        // Act
        GeneralMedicalDataDto result = medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto);

        // Assert
        assertEquals(generalMedicalDataDto, result);
        verify(allergyDataRepository, times(2)).save(any(AllergyData.class));
        verify(vaccinationDataRepository, times(2)).save(any(VaccinationData.class));
    }
    //OVDE SU TESTOVI ZA addOperation f-ju
    @Test
    void testAddOperationOperationCreated() {
        String lbp = "12345";
        OperationCreateDto operationCreateDto = new OperationCreateDto();
        Patient patient = new Patient();
        MedicalRecord medicalRecord = new MedicalRecord();
        Operation operation = new Operation();
        Operation operationSaved = new Operation();
        OperationDto operationDto = new OperationDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(operationMapper.toEntity(operationCreateDto, medicalRecord)).thenReturn(operation);
        when(operationRepository.save(operation)).thenReturn(operationSaved);
        when(operationMapper.toDto(operationSaved)).thenReturn(operationDto);

        OperationDto result = medicalRecordService.addOperation(lbp, operationCreateDto);

        assertEquals(operationDto, result);
        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
        verify(operationMapper).toEntity(operationCreateDto, medicalRecord);
        verify(operationRepository).save(operation);
        verify(operationMapper).toDto(operationSaved);
    }
    @Test
    void testAddOperationWhenNoPatientFound() {
        String lbp = "123456789";
        OperationCreateDto operationCreateDto = new OperationCreateDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> medicalRecordService.addOperation(lbp, operationCreateDto));
        assertEquals(String.format("Patient with lbp %s not found.", lbp), exception.getMessage());

        verify(patientRepository).findByLbp(lbp);
    }

    @Test
    void testAddOperationWhenNoMedicalRedordFound() {
        String lbp = "123456789";
        OperationCreateDto operationCreateDto = new OperationCreateDto();
        Patient patient = new Patient();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> medicalRecordService.addOperation(lbp, operationCreateDto));

        verify(patientRepository).findByLbp(lbp);
        verify(medicalRecordRepository).findByPatient(patient);
    }

    //OVDE SU TESTOVI ZA gatherAllergies f-ju
    @Test
    void testGatherAlergies() {
        Allergy allergy1 = new Allergy();
        allergy1.setName("Allergy 1");
        Allergy allergy2 = new Allergy();
        allergy2.setName("Allergy 2");
        AllergyDto allergyDto1 = new AllergyDto("Allergy 1");
        AllergyDto allergyDto2 = new AllergyDto("Allergy 2");

        when(allergyRepository.findAll()).thenReturn(Arrays.asList(allergy1, allergy2));
        when(allergyMapper.toDto(allergy1)).thenReturn(allergyDto1);
        when(allergyMapper.toDto(allergy2)).thenReturn(allergyDto2);

        List<AllergyDto> result = medicalRecordService.gatherAllergies();

        assertEquals(2, result.size());
        assertEquals(allergyDto1, result.get(0));
        assertEquals(allergyDto2, result.get(1));

        verify(allergyRepository).findAll();
        verify(allergyMapper).toDto(allergy1);
        verify(allergyMapper).toDto(allergy2);
    }
    @Test
    void testGatherAllergiesEmpty() {
        when(allergyRepository.findAll()).thenReturn(Collections.emptyList());

        List<AllergyDto> result = medicalRecordService.gatherAllergies();

        assertTrue(result.isEmpty());
        verify(allergyRepository).findAll();
    }
    //OVDE SU TESTOVI ZA gatherVaccines f-ju
    @Test
    void testGatherVaccines() {
        Vaccination vaccination1 = new Vaccination();
        Vaccination vaccination2 = new Vaccination();
        VaccinationDto vaccinationDto1 = new VaccinationDto();
        VaccinationDto vaccinationDto2 = new VaccinationDto();
        when(vaccinationRepository.findAll()).thenReturn(Arrays.asList(vaccination1, vaccination2));
        when(vaccinationMapper.toDto(vaccination1)).thenReturn(vaccinationDto1);
        when(vaccinationMapper.toDto(vaccination2)).thenReturn(vaccinationDto2);

        List<VaccinationDto> result = medicalRecordService.gatherVaccines(false);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(vaccinationDto1, result.get(0));
        assertEquals(vaccinationDto2, result.get(1));
        verify(vaccinationRepository).findAll();
        verify(vaccinationMapper).toDto(vaccination1);
        verify(vaccinationMapper).toDto(vaccination2);
    }

    //OVDE SU TESTOVI ZA gatherDiagnosis f-ju

    @Test
    void gatherDiagnosis() {

        DiagnosisCode diagnosisCode1 = new DiagnosisCode();
        diagnosisCode1.setId(1L);
        diagnosisCode1.setCode("Code1");
        diagnosisCode1.setDescription("Des1");
        diagnosisCode1.setLatinDescription("LatDes1");

        DiagnosisCode diagnosisCode2 = new DiagnosisCode();
        diagnosisCode2.setId(2L);
        diagnosisCode2.setCode("Code2");
        diagnosisCode2.setDescription("Des2");
        diagnosisCode2.setLatinDescription("LatDes2");

        DiagnosisCodeDto diagnosisCodeDto1 = new DiagnosisCodeDto();
        diagnosisCodeDto1.setCode("Code1");
        diagnosisCodeDto1.setDescription("Des1");
        diagnosisCodeDto1.setLatinDescription("LatDes1");

        DiagnosisCodeDto diagnosisCodeDto2 = new DiagnosisCodeDto();
        diagnosisCodeDto2.setCode("Code2");
        diagnosisCodeDto2.setDescription("Des2");
        diagnosisCodeDto2.setLatinDescription("LatDes2");

        List<DiagnosisCode> diagnosisCodes = Arrays.asList(diagnosisCode1, diagnosisCode2);
        when(diagnosisCodeRepository.findAll()).thenReturn(diagnosisCodes);
        when(diagnosisCodeMapper.toDto(diagnosisCode1)).thenReturn(diagnosisCodeDto1);
        when(diagnosisCodeMapper.toDto(diagnosisCode2)).thenReturn(diagnosisCodeDto2);

        List<DiagnosisCodeDto> gatheredDiagnosisCodeDtos = medicalRecordService.gatherDiagnosis();

        verify(diagnosisCodeRepository, times(1)).findAll();
        verify(diagnosisCodeMapper, times(1)).toDto(diagnosisCode1);
        verify(diagnosisCodeMapper, times(1)).toDto(diagnosisCode2);

        assertEquals(2, gatheredDiagnosisCodeDtos.size());
        assertEquals(diagnosisCodeDto1, gatheredDiagnosisCodeDtos.get(0));
        assertEquals(diagnosisCodeDto2, gatheredDiagnosisCodeDtos.get(1));
    }

    //OVDE SU TESTOVI ZA addVaccine f-ju


    //Proverava opsti slucaj gde ce biti dobro sve
    @Test
    void testAddVaccine() {
        String testLbp = "testLbp";
        VaccinationDataDto vaccinationDataDto = createVaccinationDataDto();
        Vaccination mockVaccination = createVaccination();
        Patient mockPatient = new Patient();
        MedicalRecord mockMedicalRecord = new MedicalRecord();

        when(vaccinationRepository.findByName(vaccinationDataDto.getVaccinationName())).thenReturn(mockVaccination);
        when(patientRepository.findByLbp(testLbp)).thenReturn(Optional.of(mockPatient));
        when(medicalRecordRepository.findByPatient(mockPatient)).thenReturn(Optional.of(mockMedicalRecord));

        MessageDto result = medicalRecordService.addVaccine(testLbp, vaccinationDataDto);

        verify(vaccinationRepository).findByName(vaccinationDataDto.getVaccinationName());
        verify(patientRepository).findByLbp(testLbp);
        verify(medicalRecordRepository).findByPatient(mockPatient);

        assertEquals("Uspesno dodata vakcina.", result.getMessage());
    }

    @Test
    void testAddVaccinePatientFoundNoMedicalRecordExists() {
        String testLbp = "testLbp";
        VaccinationDataDto vaccinationDataDto = createVaccinationDataDto();
        Vaccination mockVaccination = createVaccination();
        Patient mockPatient = new Patient();

        when(vaccinationRepository.findByName(vaccinationDataDto.getVaccinationName())).thenReturn(mockVaccination);
        when(patientRepository.findByLbp(testLbp)).thenReturn(Optional.of(mockPatient));
        when(medicalRecordRepository.findByPatient(mockPatient)).thenReturn(Optional.empty());

        MessageDto result = medicalRecordService.addVaccine(testLbp, vaccinationDataDto);

        verify(vaccinationRepository).findByName(vaccinationDataDto.getVaccinationName());
        verify(patientRepository).findByLbp(testLbp);
        verify(medicalRecordRepository).findByPatient(mockPatient);

        assertEquals("Neuspesno dodata vakcina.", result.getMessage());
    }
    @Test
    void testAddVaccinePatientNotFound() {
        String testLbp = "testLbp";
        VaccinationDataDto vaccinationDataDto = createVaccinationDataDto();

        when(patientRepository.findByLbp(testLbp)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicalRecordService.addVaccine(testLbp, vaccinationDataDto));

        verify(patientRepository).findByLbp(testLbp);

        assertEquals(String.format("Patient with lbp %s not found.", testLbp), exception.getMessage());
    }


    //OVDE SU TESTOVI ZA addAllergy f-ju

    //Opsti slucaj gde radi dodavanje
    @Test
    void testAddAllergy() {
        Allergy allergy = new Allergy();
        allergy.setName("Sample Allergy");
        Patient patient = new Patient();
        patient.setLbp("12345");
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        medicalRecord.setGeneralMedicalData(generalMedicalData);
        when(allergyRepository.findByName("Sample Allergy")).thenReturn(allergy);
        when(patientRepository.findByLbp("12345")).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));

        MessageDto result = medicalRecordService.addAllergy("12345", "Sample Allergy");

        assertEquals("Uspesno dodata alergija.", result.getMessage());
        verify(allergyDataRepository, times(1)).save(any(AllergyData.class));
    }

    @Test
    void testAddAllergyPatientNotFound() {
        Allergy allergy = new Allergy();
        allergy.setName("Sample Allergy");
        when(allergyRepository.findByName("Sample Allergy")).thenReturn(allergy);
        when(patientRepository.findByLbp("12345")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> medicalRecordService.addAllergy("12345", "Sample Allergy"));

        verify(allergyDataRepository, never()).save(any(AllergyData.class));
    }

    @Test
    void testAddAllergy_noMedicalRecord() {
        Allergy allergy = new Allergy();
        allergy.setName("Sample Allergy");
        Patient patient = new Patient();
        patient.setLbp("12345");
        MedicalRecord medicalRecord = new MedicalRecord();
        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        medicalRecord.setGeneralMedicalData(generalMedicalData);
        when(allergyRepository.findByName("Sample Allergy")).thenReturn(allergy);
        when(patientRepository.findByLbp("12345")).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.empty());

        MessageDto result = medicalRecordService.addAllergy("12345", "Sample Allergy");

        assertEquals("Neuspesno dodata alergija.", result.getMessage());
        verify(allergyDataRepository, never()).save(any(AllergyData.class));
    }

    // POMOCNE METODE
    private VaccinationDataDto createVaccinationDataDto(){
        VaccinationDataDto vaccinationDataDto = new VaccinationDataDto();
        vaccinationDataDto.setVaccinationDate(Date.valueOf("2023-11-11"));
        vaccinationDataDto.setVaccinationName("Name1");

        return vaccinationDataDto;
    }

    private Vaccination createVaccination(){
        Vaccination mockVaccination = new Vaccination();
        mockVaccination.setName("Name1");
        mockVaccination.setId(1L);
        mockVaccination.setDescription("Desciption1");
        mockVaccination.setManufacturer("Pfizer");

        return mockVaccination;
    }


}

