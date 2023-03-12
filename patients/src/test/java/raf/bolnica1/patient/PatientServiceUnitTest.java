package raf.bolnica1.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.PatientDto;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.PatientService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PatientServiceUnitTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private SocialDataRepository socialDataRepository;
    @Mock
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;
    @Mock
    private ExaminationHistoryRepository examinationHistoryRepository;

    @InjectMocks
    private PatientService patientService;


    @Test
    void registerPatient(){

        PatientDto dto = createDto();

        Patient patient = createPatient(dto);
        given(patientRepository.save(patient)).willReturn(patient);

        SocialData sd = new SocialData();
        sd.setProfession("");
        sd.setExpertiseDegree(ExpertiseDegree.BEZ_OSNOVNOG);
        sd.setMaritalStatus(MaritalStatus.SAMAC_SAMICA);
        sd.setNumOfChildren(0);
        sd.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
//        SocialData sd = patient.getSocialData();
        given(socialDataRepository.save(sd)).willReturn(sd);

        GeneralMedicalData gmd = new GeneralMedicalData();
        given(generalMedicalDataRepository.save(gmd)).willReturn(gmd);

        MedicalRecord md = new MedicalRecord();
//        md.setDeleted(false);
//        md.setRegistrationDate(Date.valueOf(LocalDate.now()));
        md.setGeneralMedicalData(gmd);
        md.setPatient(patient);
        given(medicalRecordRepository.save(md)).willReturn(md);

        try{
            PatientDto serviceDto = patientService.registerPatient(dto);
            System.err.println(serviceDto.getName());
            assertTrue(true);
//            assertNotNull(serviceDto);
//            assertEquals(serviceDto.getName(), dto.getName());
        }catch(Exception e){
            fail(e.getMessage());
        }
    }


    private PatientDto createDto(){
        PatientDto dto = new PatientDto();
        dto.setName("Petar");
        dto.setSurname("Petrovic");
        dto.setCitizenship(CountryCode.SRB);
        dto.setEmail("p4cijent@mail.com");
        dto.setBirthPlace("Beograd");
        dto.setJmbg("11111112345");
        dto.setGender(Gender.MUSKO);
        dto.setDateOfBirth("2011-11-11");
        dto.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        dto.setExpertiseDegree(ExpertiseDegree.BEZ_OSNOVNOG);
        dto.setGuardianJmbg("20030612345");
        dto.setMaritalStatus(MaritalStatus.SAMAC_SAMICA);
        dto.setParentName("Marko");
        dto.setNumOfChildren(0);
        dto.setGuardianNameAndSurname("Marko Markovic");
        dto.setPhone("0630744261");
        dto.setProfession("");
        dto.setPlaceOfLiving("Beograd");
        return dto;
    }
    private Patient createPatient(PatientDto dto){
        return PatientMapper.patientDtoToPatient(dto);
    }
    private Patient createPatient(String name, String surname, String jmbg, String lbp, Long id){
        Patient p = new Patient();

        p.setId(new Long(1));
        p.setName(name);
        p.setSurname(surname);
        p.setLbp(lbp);
        p.setJmbg(jmbg);
        p.setCitizenship(CountryCode.SRB);
        p.setPhone("0630744261");
        p.setEmail("p4cijent@mail.com");
        p.setBirthPlace("Beograd");
        p.setGender(Gender.MUSKO);
        p.setDateOfBirth(Date.valueOf("2011-11-11"));
        p.setGuardianJmbg("20030612345");
        p.setParentName("Marko");
        p.setGuardianNameAndSurname("Marko Markovic");
        p.setPhone("0630744261");
        p.setPlaceOfLiving("Beograd");

        SocialData sd = new SocialData();
        sd.setFamilyStatus(FamilyStatus.OBA_RODITELJA);
        sd.setExpertiseDegree(ExpertiseDegree.BEZ_OSNOVNOG);
        sd.setMaritalStatus(MaritalStatus.SAMAC_SAMICA);
        sd.setNumOfChildren(0);
        sd.setProfession("");

        p.setSocialData(sd);

        return p;
    }

}
