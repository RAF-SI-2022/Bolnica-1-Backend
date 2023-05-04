package raf.bolnica1.patient.services.impl.additionalTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.cucumber.validation.ClassJsonComparator;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.domain.prescription.LabPrescription;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.DiagnosisCodeDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.mapper.AnamnesisMapper;
import raf.bolnica1.patient.mapper.DiagnosisCodeMapper;
import raf.bolnica1.patient.mapper.ExaminationHistoryMapper;
import raf.bolnica1.patient.mapper.MedicalHistoryMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalExaminationService;
import raf.bolnica1.patient.services.impl.MedicalExaminationServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MedicalExaminationServiceUnitTest {

    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private ExaminationHistoryRepository examinationHistoryRepository;
    private MedicalHistoryRepository medicalHistoryRepository;
    private AnamnesisRepository anamnesisRepository;
    private AnamnesisMapper anamnesisMapper;
    private DiagnosisCodeRepository diagnosisCodeRepository;
    private ExaminationHistoryMapper examinationHistoryMapper;
    private MedicalHistoryMapper medicalHistoryMapper;

    private MedicalExaminationService medicalExaminationService;


    private ClassJsonComparator classJsonComparator=new ClassJsonComparator(new ObjectMapper());

    private List<Prescription> prescriptions;
    private List<MedicalRecord> medicalRecords;
    private List<Patient> patients;

    @BeforeEach
    public void prepare(){
        generateData();

        patientRepository=mock(PatientRepository.class);
        medicalRecordRepository=mock(MedicalRecordRepository.class);
        examinationHistoryRepository=mock(ExaminationHistoryRepository.class);
        medicalHistoryRepository=mock(MedicalHistoryRepository.class);
        anamnesisRepository=mock(AnamnesisRepository.class);
        anamnesisMapper=new AnamnesisMapper();
        diagnosisCodeRepository=mock(DiagnosisCodeRepository.class);
        examinationHistoryMapper=new ExaminationHistoryMapper(new DiagnosisCodeMapper(),anamnesisMapper);
        medicalHistoryMapper=new MedicalHistoryMapper(new DiagnosisCodeMapper(),diagnosisCodeRepository);
        medicalExaminationService=new MedicalExaminationServiceImpl(patientRepository,medicalRecordRepository,
                examinationHistoryRepository,medicalHistoryRepository,anamnesisRepository,anamnesisMapper,
                diagnosisCodeRepository,examinationHistoryMapper,medicalHistoryMapper);
    }


    @Test
    public void addMedicalHistoryTest(){

        MedicalHistoryCreateDto medicalHistoryCreateDto=new MedicalHistoryCreateDto();
        medicalHistoryCreateDto.setConfidential(false);
        medicalHistoryCreateDto.setExists(false);
        medicalHistoryCreateDto.setTreatmentResult(TreatmentResult.OPORAVLJEN);
        medicalHistoryCreateDto.setCurrStateDesc("state");
        DiagnosisCodeDto diagnosisCodeDto=new DiagnosisCodeDto();
        diagnosisCodeDto.setCode("code");
        diagnosisCodeDto.setDescription("description");
        diagnosisCodeDto.setLatinDescription("latinDescription");
        medicalHistoryCreateDto.setDiagnosisCodeDto(diagnosisCodeDto);

        String lbp=patients.get(0).getLbp();

        given(patientRepository.findByLbp(lbp)).willReturn(Optional.ofNullable(patients.get(0)));
        given(medicalRecordRepository.findByPatient(patients.get(0))).willReturn(Optional.ofNullable(medicalRecords.get(0)));
        DiagnosisCode diagnosisCode=new DiagnosisCode();
        diagnosisCode.setCode(diagnosisCodeDto.getCode());
        diagnosisCode.setDescription(diagnosisCodeDto.getDescription());
        diagnosisCode.setLatinDescription(diagnosisCodeDto.getLatinDescription());
        given(diagnosisCodeRepository.findByCode("code")).willReturn(diagnosisCode);

        MedicalHistory pom=medicalHistoryMapper.getMedicalHistoryFromCreateDtoNoExist(medicalHistoryCreateDto);
        given(medicalHistoryRepository.save(any())).willReturn(pom);

        MedicalHistoryDto ret= medicalExaminationService.addMedicalHistory(lbp,medicalHistoryCreateDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,medicalHistoryCreateDto));

    }



    /// generate data

    public void generateData(){

        prescriptions=new ArrayList<>();
        medicalRecords=new ArrayList<>();
        patients=new ArrayList<>();

        Patient patient1 = new Patient();
        patient1.setJmbg("0101998100001");
        patient1.setLbp("P0002");
        patient1.setName("John");
        patient1.setParentName("Doe");
        patient1.setSurname("Smith");
        patient1.setGender(Gender.MUSKO);
        patient1.setDateOfBirth(Date.valueOf("1998-01-02"));
        patient1.setBirthPlace("Belgrade");
        patient1.setPlaceOfLiving("Novi Sad");
        patient1.setResidenceCountry(CountryCode.SRB);
        patient1.setCitizenship(CountryCode.SRB);
        patient1.setPhone("+38111111111");
        patient1.setEmail("john.smith@example.com");
        patient1.setGuardianJmbg("0101970100001");
        patient1.setGuardianNameAndSurname("Jane Doe");
        SocialData socialData1 = new SocialData();
        socialData1.setMaritalStatus(MaritalStatus.U_BRAKU);
        socialData1.setNumOfChildren(0);
        socialData1.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData1.setProfession("Software engineer");
        socialData1.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        patient1.setSocialData(socialData1);

        Patient patient2 = new Patient();
        patient2.setJmbg("0202999000001");
        patient2.setLbp("P0003");
        patient2.setName("Jane");
        patient2.setParentName("Smith");
        patient2.setSurname("Doe");
        patient2.setGender(Gender.ZENSKO);
        patient2.setDateOfBirth(Date.valueOf("1998-02-03"));
        patient2.setBirthPlace("Novi Sad");
        patient2.setPlaceOfLiving("Belgrade");
        patient2.setResidenceCountry(CountryCode.SRB);
        patient2.setCitizenship(CountryCode.SRB);
        patient2.setPhone("+38122222222");
        patient2.setEmail("jane.doe@example.com");
        patient2.setGuardianJmbg("0101970100001");
        patient2.setGuardianNameAndSurname("Jane Doe");
        SocialData socialData2 = new SocialData();
        socialData2.setMaritalStatus(MaritalStatus.RAZVEDENI);
        socialData2.setNumOfChildren(1);
        socialData2.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData2.setProfession("Lawyer");
        socialData2.setFamilyStatus(FamilyStatus.RAZVEDENI);
        patient2.setSocialData(socialData2);

        Patient patient3 = new Patient();
        patient3.setJmbg("0303998000001");
        patient3.setLbp("P0004");
        patient3.setName("Adam");
        patient3.setParentName("Johnson");
        patient3.setSurname("Lee");
        patient3.setGender(Gender.MUSKO);
        patient3.setDateOfBirth(Date.valueOf("1998-03-04"));
        patient3.setBirthPlace("New York");
        patient3.setPlaceOfLiving("Los Angeles");
        patient3.setResidenceCountry(CountryCode.USA);
        patient3.setCitizenship(CountryCode.USA);
        patient3.setPhone("+14155555555");
        patient3.setEmail("adam.lee@example.com");
        patient3.setGuardianJmbg("0203942000001");
        patient3.setGuardianNameAndSurname("Sarah Johnson");
        SocialData socialData3 = new SocialData();
        socialData3.setMaritalStatus(MaritalStatus.U_BRAKU);
        socialData3.setNumOfChildren(0);
        socialData3.setExpertiseDegree(ExpertiseDegree.SREDNJE);
        socialData3.setProfession("Doctor");
        socialData3.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        patient3.setSocialData(socialData3);

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setPatient(patient1);
        medicalRecord1.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord1.setDeleted(false);
        GeneralMedicalData generalMedicalData1 = new GeneralMedicalData();
        generalMedicalData1.setBloodType("A");
        generalMedicalData1.setRH("+");
        medicalRecord1.setGeneralMedicalData(generalMedicalData1);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setPatient(patient2);
        medicalRecord2.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord2.setDeleted(false);
        GeneralMedicalData generalMedicalData2 = new GeneralMedicalData();
        generalMedicalData2.setBloodType("B");
        generalMedicalData2.setRH("-");
        medicalRecord2.setGeneralMedicalData(generalMedicalData2);

        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setPatient(patient3);
        medicalRecord3.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord3.setDeleted(false);
        GeneralMedicalData generalMedicalData3 = new GeneralMedicalData();
        generalMedicalData3.setBloodType("0");
        generalMedicalData3.setRH("+");
        medicalRecord3.setGeneralMedicalData(generalMedicalData3);

        LabPrescription prescription1 = new LabPrescription();
        prescription1.setDoctorLbz("Dr. Smith");
        prescription1.setDepartmentFromId(1L);
        prescription1.setDepartmentToId(2L);
        prescription1.setDate(Date.valueOf("2023-05-07"));
        prescription1.setMedicalRecord(medicalRecord1);
        prescription1.setComment("comment1");
        prescription1.setId(1L);

        LabPrescription prescription2 = new LabPrescription();
        prescription2.setDoctorLbz("Dr. Johnson");
        prescription2.setDepartmentFromId(3L);
        prescription2.setDepartmentToId(4L);
        prescription2.setDate(Date.valueOf("2023-05-08"));
        prescription2.setMedicalRecord(medicalRecord2);
        prescription2.setComment("comment2");
        prescription2.setId(2L);

        LabPrescription prescription3 = new LabPrescription();
        prescription3.setDoctorLbz("Dr. Lee");
        prescription3.setDepartmentFromId(2L);
        prescription3.setDepartmentToId(3L);
        prescription3.setDate(Date.valueOf("2023-05-09"));
        prescription3.setMedicalRecord(medicalRecord3);
        prescription3.setComment("comment3");
        prescription3.setId(3L);



        prescriptions.add(prescription1);
        prescriptions.add(prescription2);
        prescriptions.add(prescription3);

        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);
        medicalRecords.add(medicalRecord3);

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

    }

}
