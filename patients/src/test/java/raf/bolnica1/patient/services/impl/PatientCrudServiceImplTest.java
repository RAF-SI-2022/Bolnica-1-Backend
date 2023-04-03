package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.parameters.P;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.PatientDto;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.SocialDataRepository;
import raf.bolnica1.patient.services.PatientCrudService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
public class PatientCrudServiceImplTest {
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private SocialDataRepository socialDataRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @InjectMocks
    private PatientCrudServiceImpl patientCrudService;

    @Test
    void testRegisterPatient(){
        PatientCreateDto createDto = new PatientCreateDto();
        createDto.setRegisterDate(Date.valueOf("2023-04-03"));
        createDto.setEmail("patient@email.com");
        createDto.setCitizenship(CountryCode.SRB);
        createDto.setJmbg("12345678910");
        createDto.setBirthPlace("Beograd");
        createDto.setGender(Gender.MUSKO);
        createDto.setLbp("4321");
        createDto.setName("Ime");
        createDto.setSurname("Prezimovic");
        createDto.setPhone("06385113547");
        createDto.setDateOfBirth(Date.valueOf("2011-11-11"));
        createDto.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        createDto.setFamilyStatus(FamilyStatus.USVOJEN);
        createDto.setNumOfChildren(0);
        createDto.setPlaceOfLiving("Beograd");
        createDto.setProfession("life coach");
        createDto.setMaritalStatus(MaritalStatus.RAZVEDENI);
        createDto.setGuardianJmbg("10987654321");
        createDto.setGuardianNameAndSurname("Marko Markovic");

        SocialData sd = new SocialData();
        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("12345678910");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("4321");
        patient.setName("Ime");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        MedicalRecord md = new MedicalRecord();
        md.setPatient(patient);
        md.setRegistrationDate(createDto.getRegisterDate());

        PatientDto patientDto = createPatientDto();

        given(patientRepository.save(patient)).willReturn(patient);
        given(medicalRecordRepository.save(any())).willReturn(md);
        given(socialDataRepository.save(sd)).willReturn(sd);
        given(patientMapper.patientDtoToPatientGeneralData(createDto)).willReturn(patient);
        given(patientMapper.patientToPatientDto(patient)).willReturn(patientDto);
        given(patientMapper.patientDtoToPatientSocialData(createDto)).willReturn(sd);

        try{
            PatientDto dto = patientCrudService.registerPatient(createDto);
            assertNotNull(dto);
            assertEquals(dto.getLbp(), patientDto.getLbp());
        }catch (Exception e){
            fail(e.getMessage());
//            e.printStackTrace();
        }
    }

    @Test
    public void testDeletePatient(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("12345678910");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("4321");
        patient.setName("Ime");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        SocialData sd = new SocialData();
        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);

        patient.setSocialData(sd);

        MedicalRecord md = new MedicalRecord();
        md.setPatient(patient);
        md.setRegistrationDate(Date.valueOf("2011-11-11"));


        given(patientRepository.findByLbp("4321")).willReturn(Optional.of(patient));
        given(medicalRecordRepository.findByPatient(patient)).willReturn(Optional.of(md));

        try{
            MessageDto messageDto =  patientCrudService.deletePatient("4321");
            assertNotNull(messageDto);
            assertEquals(messageDto.getMessage(), "Patient with lbp 4321 deleted.");
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindPatient(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("12345678910");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("4321");
        patient.setName("Ime");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        SocialData sd = new SocialData();
        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);

        patient.setSocialData(sd);

        PatientDto patientDto = createPatientDto();

        given(patientRepository.findByLbp("4321")).willReturn(Optional.of(patient));
        given(patientMapper.patientToPatientDto(patient)).willReturn(patientDto);

        try {
            PatientDto dto = patientCrudService.findPatient("4321");
            assertNotNull(dto);
            assertEquals("4321", dto.getLbp());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    public void testUpdatePatient(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("12345678910");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("4321");
        patient.setName("StaroIme");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        SocialData sd = new SocialData();
        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);
        patient.setSocialData(sd);

        PatientUpdateDto updateDto = new PatientUpdateDto();
        updateDto.setEmail("patient@email.com");
        updateDto.setCitizenship(CountryCode.SRB);
        updateDto.setJmbg("12345678910");
        updateDto.setBirthPlace("Beograd");
        updateDto.setGender(Gender.MUSKO);
        updateDto.setLbp("4321");
        updateDto.setName("NovoIme");
        updateDto.setSurname("Prezimovic");
        updateDto.setPhone("06385113547");
        updateDto.setDateOfBirth(Date.valueOf("2011-11-11"));
        updateDto.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        updateDto.setFamilyStatus(FamilyStatus.USVOJEN);
        updateDto.setNumOfChildren(0);
        updateDto.setPlaceOfLiving("Beograd");
        updateDto.setProfession("life coach");
        updateDto.setMaritalStatus(MaritalStatus.RAZVEDENI);
        updateDto.setGuardianJmbg("10987654321");
        updateDto.setGuardianNameAndSurname("Marko Markovic");
        updateDto.setDeleted(false);


        Patient patient2 = new Patient();
        patient2.setId(1L);
        patient2.setEmail("patient@email.com");
        patient2.setCitizenship(CountryCode.SRB);
        patient2.setJmbg("12345678910");
        patient2.setBirthPlace("Beograd");
        patient2.setGender(Gender.MUSKO);
        patient2.setLbp("4321");
        patient2.setName("NovoIme");
        patient2.setSurname("Prezimovic");
        patient2.setPhone("06385113547");
        patient2.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient2.setPlaceOfLiving("Beograd");
        patient2.setGuardianJmbg("10987654321");
        patient2.setGuardianNameAndSurname("Marko Markovic");
        patient2.setDeleted(false);
        patient2.setSocialData(sd);

        PatientDto patientDto = createPatientDto();
        patientDto.setName("NovoIme");

        given(patientRepository.findByLbp("4321")).willReturn(Optional.of(patient));
        given(patientRepository.save(patient2)).willReturn(patient2);
        given(patientMapper.setPatientGeneralData(updateDto, patient)).willReturn(patient2);
        given(patientMapper.patientToPatientDto(patient2)).willReturn(patientDto);

        try{
            PatientDto dto = patientCrudService.updatePatient(updateDto);
            assertNotNull(dto);
            assertEquals(dto.getName(), updateDto.getName());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testFilterPatients(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("12345678910");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("4321");
        patient.setName("Prvi");
        patient.setSurname("Pacijentic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        Patient patient2 = new Patient();
        patient2.setId(1L);
        patient2.setEmail("patient@email.com");
        patient2.setCitizenship(CountryCode.SRB);
        patient2.setJmbg("12345678910");
        patient2.setBirthPlace("Beograd");
        patient2.setGender(Gender.MUSKO);
        patient2.setLbp("1234");
        patient2.setName("Drugi");
        patient2.setSurname("Pacijentic");
        patient2.setPhone("06385113547");
        patient2.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient2.setPlaceOfLiving("Beograd");
        patient2.setGuardianJmbg("12345678910");
        patient2.setGuardianNameAndSurname("Marko Markovic");
        patient2.setDeleted(false);

        SocialData sd = new SocialData();
        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);
        patient.setSocialData(sd);

        PatientDto patientDto = createPatientDto();
        patientDto.setName("Prvi");
        patientDto.setSurname("Pacijentic");

        PatientDto patientDto2 = createPatientDto();
        patientDto.setName("Drugi");
        patientDto.setSurname("Pacijentic");
        patientDto2.setJmbg("12345678910");
        patientDto2.setLbp("1234");

        Page<Patient> singelElementPage = new PageImpl<>(List.of(patient));
        Page<Patient> multiElementPage = new PageImpl<>(List.of(patient, patient2));

        given(patientRepository.listPatientsWithFilters(any(), eq(null), eq(null), eq(null), eq("4321"))).willReturn(singelElementPage);
        given(patientRepository.listPatientsWithFilters(any(), eq(null), eq("Pacijentic"), eq(null), eq(null))).willReturn(multiElementPage);
        given(patientMapper.patientToPatientDto(patient)).willReturn(patientDto);
        given(patientMapper.patientToPatientDto(patient2)).willReturn(patientDto2);

        try{
            Page<PatientDto> page1 = patientCrudService.filterPatients("4321", null, null, null, 0, 10);
            assertNotNull(page1);
            assertEquals(page1.getTotalElements(), 1);

            Page<PatientDto> page2 = patientCrudService.filterPatients(null, null, null, "Pacijentic", 0, 10);
            assertNotNull(page2);
            assertEquals(page2.getTotalElements(), 2);
        }catch (Exception e){
            fail(e.getMessage());
        }

    }


    private PatientDto createPatientDto(){
        PatientDto dto = new PatientDto();
        dto.setEmail("patient@email.com");
        dto.setCitizenship(CountryCode.SRB);
        dto.setJmbg("12345678910");
        dto.setBirthPlace("Beograd");
        dto.setGender(Gender.MUSKO);
        dto.setLbp("4321");
        dto.setName("Ime");
        dto.setSurname("Prezimovic");
        dto.setPhone("06385113547");
        dto.setDateOfBirth(Date.valueOf("2011-11-11"));
        dto.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        dto.setFamilyStatus(FamilyStatus.USVOJEN);
        dto.setNumOfChildren(0);
        dto.setPlaceOfLiving("Beograd");
        dto.setProfession("life coach");
        dto.setMaritalStatus(MaritalStatus.RAZVEDENI);
        dto.setGuardianJmbg("10987654321");
        dto.setGuardianNameAndSurname("Marko Markovic");
        dto.setDeleted(false);
        return dto;
    }

    private Patient createPatient(){
        Patient entity = new Patient();
        entity.setId(1L);
        entity.setEmail("patient@email.com");
        entity.setCitizenship(CountryCode.SRB);
        entity.setJmbg("12345678910");
        entity.setBirthPlace("Beograd");
        entity.setGender(Gender.MUSKO);
        entity.setLbp("4321");
        entity.setName("Ime");
        entity.setSurname("Prezimovic");
        entity.setPhone("06385113547");
        entity.setDateOfBirth(Date.valueOf("2011-11-11"));
        entity.setPlaceOfLiving("Beograd");
        entity.setGuardianJmbg("10987654321");
        entity.setGuardianNameAndSurname("Marko Markovic");
        entity.setDeleted(false);

        SocialData sd = new SocialData();

        sd.setExpertiseDegree(ExpertiseDegree.OSNOVNO);
        sd.setFamilyStatus(FamilyStatus.USVOJEN);
        sd.setNumOfChildren(0);
        sd.setProfession("life coach");
        sd.setMaritalStatus(MaritalStatus.RAZVEDENI);
        entity.setSocialData(sd);

        return entity;
    }

}
