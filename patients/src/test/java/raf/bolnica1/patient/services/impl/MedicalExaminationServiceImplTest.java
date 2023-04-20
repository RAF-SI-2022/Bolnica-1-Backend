package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.CountryCode;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.constants.TreatmentResult;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.AnamnesisDto;
import raf.bolnica1.patient.dto.general.DiagnosisCodeDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.mapper.AnamnesisMapper;
import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.repository.*;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    //Testovi za addExamination
    @Test
    public void testAddExaminationForValidPatient() {

        String lbp = "123456";
        ExaminationHistoryCreateDto examinationHistoryCreateDto = createExaminationHistoryCreateDto();


        Patient patient = createPatietnt();
        MedicalRecord medicalRecord = createMedicalRecord();
        ExaminationHistory examinationHistory = createExaminationHistory();
        Anamnesis anamnesis = createAnamnesis();
        DiagnosisCode diagnosisCode = createDiagnosisCode();
        ExaminationHistoryDto expected = createExamHistoryDto();

        //System.out.println(examinationHistoryCreateDto.getDiagnosisCodeDto().getCode());
        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));
        when(examinationHistoryMapper.toEntity(examinationHistoryCreateDto)).thenReturn(examinationHistory);
        when(anamnesisMapper.toEntity(examinationHistoryCreateDto.getAnamnesisDto())).thenReturn(anamnesis);
        when(anamnesisRepository.save(anamnesis)).thenReturn(anamnesis);

        when(diagnosisCodeRepository.findByCode(examinationHistoryCreateDto.getDiagnosisCodeDto().getCode())).thenReturn(diagnosisCode);
        when(examinationHistoryMapper.toDto(examinationHistoryRepository.save(examinationHistory))).thenReturn(expected);

        ExaminationHistoryDto actual = medicalExaminationService.addExamination(lbp, examinationHistoryCreateDto);

        assertEquals(expected, actual);
        verify(patientRepository, times(1)).findByLbp(lbp);
        verify(medicalRecordRepository, times(1)).findByPatient(patient);
        verify(examinationHistoryMapper, times(1)).toEntity(examinationHistoryCreateDto);
        verify(anamnesisMapper, times(1)).toEntity(examinationHistoryCreateDto.getAnamnesisDto());
        verify(anamnesisRepository, times(1)).save(anamnesis);
        verify(diagnosisCodeRepository, times(1)).findByCode(examinationHistoryCreateDto.getDiagnosisCodeDto().getCode());
        verify(examinationHistoryMapper, times(1)).toDto(examinationHistoryRepository.save(examinationHistory));
    }

    @Test
    public void testAddExaminationForInvalidPatient() {
        String lbp = "123456";
        ExaminationHistoryCreateDto examHistoryDto = createExaminationHistoryCreateDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        try {
            medicalExaminationService.addExamination(lbp, examHistoryDto);
            fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException ex) {
            assertEquals("Patient with lbp 123456 not found.", ex.getMessage());
        }
    }

    //Testovi za addMedicalHistory
    @Test
    void addMedicalHistorySuccessfull() {
        // Arrange
        String lbp = "123456";
        MedicalHistoryCreateDto medicalHistoryCreateDto = createMedicalHistoryCreateDto();

        Patient patient = createPatietnt();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.of(patient));

        MedicalRecord medicalRecord = createMedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setPatient(patient);
        when(medicalRecordRepository.findByPatient(patient)).thenReturn(Optional.of(medicalRecord));

        MedicalHistory medicalHistory = createMedicalHistory();
        when(medicalHistoryMapper.getMedicalHistoryFromCreateDtoNoExist(medicalHistoryCreateDto)).thenReturn(medicalHistory);
        when(medicalHistoryMapper.toDto(medicalHistory)).thenReturn(new MedicalHistoryDto());

        when(medicalHistoryRepository.save(medicalHistory)).thenReturn(medicalHistory);

        MedicalHistoryDto result = medicalExaminationService.addMedicalHistory(lbp, medicalHistoryCreateDto);

        assertNotNull(result);
    }

    @Test
    void addMedicalHistoryWhenPatientDoesNotExistShouldThrowException() {
        String lbp = "123456";
        MedicalHistoryCreateDto medicalHistoryCreateDto = createMedicalHistoryCreateDto();

        when(patientRepository.findByLbp(lbp)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> medicalExaminationService.addMedicalHistory(lbp, medicalHistoryCreateDto));
    }


//POMOCNE FJE
    private MedicalHistory createMedicalHistory(){
        MedicalHistory medicalHistory = new MedicalHistory();

        medicalHistory.setConfidential(false);
        medicalHistory.setCurrStateDesc("CurrStateDesc");
        medicalHistory.setTreatmentResult(TreatmentResult.U_TOKU);

        DiagnosisCode diagnosisCode = new DiagnosisCode();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setLatinDescription("Latin Description");
        diagnosisCode.setId(1L);
        medicalHistory.setDiagnosisCode(diagnosisCode);

        medicalHistory.setId(1L);
        medicalHistory.setMedicalRecord(createMedicalRecord());

        return medicalHistory;
    }
    private MedicalHistoryCreateDto createMedicalHistoryCreateDto(){
        MedicalHistoryCreateDto medicalHistoryCreateDto = new MedicalHistoryCreateDto();
        medicalHistoryCreateDto.setExists(false);
        medicalHistoryCreateDto.setConfidential(false);
        medicalHistoryCreateDto.setCurrStateDesc("CurrStateDesc");
        medicalHistoryCreateDto.setTreatmentResult(TreatmentResult.U_TOKU);

        DiagnosisCodeDto diagnosisCode = new DiagnosisCodeDto();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setLatinDescription("Latin Description");
        medicalHistoryCreateDto.setDiagnosisCodeDto(diagnosisCode);

        return medicalHistoryCreateDto;
    }
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

    private DiagnosisCode createDiagnosisCode(){
        DiagnosisCode diagnosisCode = new DiagnosisCode();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setId(1L);
        diagnosisCode.setLatinDescription("Latin Description");
        return diagnosisCode;
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

    private ExaminationHistoryCreateDto createExaminationHistoryCreateDto(){
        ExaminationHistoryCreateDto examinationHistoryCreateDto = new ExaminationHistoryCreateDto();

        DiagnosisCodeDto diagnosisCode = new DiagnosisCodeDto();
        diagnosisCode.setCode("Code");
        diagnosisCode.setDescription("Description");
        diagnosisCode.setLatinDescription("Latin Description");

        examinationHistoryCreateDto.setDiagnosisCodeDto(diagnosisCode);

        AnamnesisDto anamnesisDto = new AnamnesisDto();
        anamnesisDto.setPersonalAnamnesis("PersonalAnamnesis");
        anamnesisDto.setFamilyAnamnesis("FamilyAnamnesis");
        anamnesisDto.setMainProblems("MainProblems");
        anamnesisDto.setPatientOpinion("Patient opinion");
        anamnesisDto.setCurrDisease("CurrDesease");

        examinationHistoryCreateDto.setAnamnesisDto(anamnesisDto);

        examinationHistoryCreateDto.setTherapy("Therapy");
        examinationHistoryCreateDto.setAdvice("Advice");
        examinationHistoryCreateDto.setLbz("LBZ");
        examinationHistoryCreateDto.setObjectiveFinding("Objective finding");
        examinationHistoryCreateDto.setConfidential(false);

        return examinationHistoryCreateDto;
    }

    private Anamnesis createAnamnesis(){
        Anamnesis anamnesis = new Anamnesis();
        anamnesis.setId(1L);
        anamnesis.setFamilyAnamnesis("FamilyAnamnesis");
        anamnesis.setPersonalAnamnesis("PersonalAnamnesis");
        anamnesis.setCurrDisease("CurrDesease");
        anamnesis.setMainProblems("MainProblems");
        anamnesis.setPatientOpinion("Patient opinion");

        return anamnesis;
    }
}
