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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        given(patientRepository.save(any())).willReturn(patient);

        given(socialDataRepository.save(any())).willReturn(patient.getSocialData());

        GeneralMedicalData gmd = new GeneralMedicalData();
        given(generalMedicalDataRepository.save(any())).willReturn(gmd);

        MedicalRecord md = new MedicalRecord();
        md.setDeleted(false);
        md.setRegistrationDate(Date.valueOf(LocalDate.now()));
        md.setGeneralMedicalData(gmd);
        md.setPatient(patient);
        given(medicalRecordRepository.save(any())).willReturn(md);

        try{
            PatientDto serviceDto = patientService.registerPatient(dto);

            assertNotNull(serviceDto);
            assertEquals(serviceDto.getName(), dto.getName());
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void updatePatient(){
        PatientDto expectedDto = createDto();
        Patient patient = PatientMapper.patientDtoToPatient(expectedDto);
        given(patientRepository.save(any())).willReturn(patient);
        given(socialDataRepository.save(any())).willReturn(patient.getSocialData());
        given(patientRepository.findById(expectedDto.getId())).willReturn(Optional.of(patient));
        try{
            PatientDto dto = patientService.updatePatient(expectedDto);
            assertNotNull(dto);
            assertEquals(expectedDto, dto);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void deletePatient(){
        Patient patient = createPatient(createDto());
        given(patientRepository.findByLbp(patient.getLbp())).willReturn(Optional.of(patient));
        given(patientRepository.save(any())).willReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        MedicalRecord md1 = new MedicalRecord();
        md1.setDeleted(false);
        md1.setPatient(patient);
        md1.setRegistrationDate(Date.valueOf(LocalDate.now()));
        GeneralMedicalData gmd1 = new GeneralMedicalData();
        gmd1.setRH("+");
        gmd1.setBloodType("A");
        md1.setGeneralMedicalData(gmd1);

        MedicalRecord md2 = new MedicalRecord();
        md2.setDeleted(false);
        md2.setRegistrationDate(Date.valueOf(LocalDate.now()));
        GeneralMedicalData gmd2 = new GeneralMedicalData();
        gmd2.setRH("+");
        gmd2.setBloodType("A");
        md2.setGeneralMedicalData(gmd2);

        medicalRecordList.add(md1);
        medicalRecordList.add(md2);

        given(medicalRecordRepository.findAll()).willReturn(medicalRecordList);
        given(medicalRecordRepository.save(any())).willReturn(md1);

        try{
            //Ako pronadjemo pacijenta i njegov zdravstveni karton, onda mozemo da ih "obrisemo"
            boolean value = patientService.deletePatient(patient.getLbp());
            assertTrue(value);
        }catch (Exception e){
            fail(e.getMessage());
        }

        medicalRecordList.clear();
        given(medicalRecordRepository.findAll()).willReturn(medicalRecordList);
        try{
            //ako ne pronadjemo zdravsteni karton, onda vracamo false
            boolean value = patientService.deletePatient(patient.getLbp());
            assertFalse(value);
        }catch (Exception e){
            fail(e.getMessage());
        }

        given(patientRepository.findByLbp(patient.getLbp())).willReturn(Optional.empty());
        try{
            //ako ne pronadjemo pacijenta, onda vracamo false
            boolean value = patientService.deletePatient(patient.getLbp());
            assertFalse(value);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void filterPatients(){
        List<Patient> patientList = new ArrayList<>();
        Patient patient1 = createPatient(createDto());

        Patient patient2 = createPatient(createDto());
        patient2.setId(Long.parseLong("2"));
        patient2.setName("Djoka");
        patient2.setSurname("Djokovic");
        patient2.setJmbg("12345678910");
        patient2.setDeleted(true);

        List<Patient> name1 = new ArrayList<>();
        name1.add(patient1);
        List<Patient> name2 = new ArrayList<>();
        name2.add(patient2);

        List<Patient> surname1 = new ArrayList<>();
        surname1.add(patient1);
        List<Patient> surname2 = new ArrayList<>();
        surname2.add(patient2);

        given(patientRepository.findByName(patient1.getName())).willReturn(Optional.of(name1));
//        given(patientRepository.findByName(patient2.getName())).willReturn(Optional.of(name2));
        given(patientRepository.findBySurname(patient1.getSurname())).willReturn(Optional.of(surname1));
//        given(patientRepository.findBySurname(patient2.getSurname())).willReturn(Optional.of(surname2));
        given(patientRepository.findByJmbg(patient1.getJmbg())).willReturn(Optional.of(patient1));
//        given(patientRepository.findByJmbg(patient2.getJmbg())).willReturn(Optional.of(patient2));
        given(patientRepository.findByLbp(patient1.getLbp())).willReturn(Optional.of(patient1));
//        given(patientRepository.findByLbp(patient2.getLbp())).willReturn(Optional.of(patient2));

        try {
            List<PatientDto> result;
            //pacijent 1
            result = patientService.filterPatients("", "", "", "");
            assertEquals(0, result.size());

            result = patientService.filterPatients("", "", "Petar", "");
            assertEquals(1, result.size());

            result = patientService.filterPatients("", "", "", "Petrovic");
            assertEquals(1, result.size());

            result = patientService.filterPatients("", "11111112345", "", "");
            assertEquals(1, result.size());

            result = patientService.filterPatients(patient1.getLbp(), "", "", "");
            assertEquals(1, result.size());

            result = patientService.filterPatients("", "", "Petar", "Petrovic");
            assertEquals(1, result.size());

            result = patientService.filterPatients(patient1.getLbp(), "11111112345", "Petar", "Petrovic");
            assertEquals(1, result.size());


            //pacijent 2
            //ocekujemo nula pacijenata zato sto postoji samo jedan Djoka i on je "obrisan"
            result = patientService.filterPatients("", "", "Djoka", "");
            assertEquals(0, result.size());


        }catch (Exception e){
            fail(e.getMessage());
        }


    }

    private PatientDto createDto(){
        PatientDto dto = new PatientDto();
        dto.setLbp(UUID.randomUUID().toString());
        dto.setId(Long.parseLong("1"));
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

        p.setId(Long.parseLong("1"));
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
