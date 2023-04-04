package raf.bolnica1.patient.services.cucumber.patientcrudservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.PatientDto;
import raf.bolnica1.patient.services.PatientCrudService;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

public class PatientCrudServiceTestsSteps extends PatientCrudServiceTestsConfig {

    @Autowired
    PatientCrudService patientCrudService;

    @When("Kada se napravi novi pacijent")
    public void kada_se_napravi_novi_pacijent() {
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

        try{
            PatientDto patientDto = patientCrudService.registerPatient(createDto);
            assertNotNull(patientDto);
            assertEquals(patientDto.getLbp(), createDto.getLbp());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Taj pacijent je sacuvan u bazi")
    public void taj_pacijent_je_sacuvan_u_bazi() {
        try{
            PatientDto patientDto = patientCrudService.findPatient("4321");
            assertNotNull(patientDto);
            assertEquals("Ime", patientDto.getName());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @When("Kada se prosledi {string} kao lbp")
    public void kada_se_prosledi_kao_lbp(String lbp) {
        MessageDto messageDto = patientCrudService.deletePatient(lbp);
        try{
            assertNotNull(messageDto);
            assertEquals("Patient with lbp "+lbp+" deleted.", messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Pacijentu sa lbp-op {string} ce se deleted flag postaviti na true")
    public void pacijentu_sa_lbp_op_ce_se_deleted_flag_postaviti_na_true(String lbp) {
        try{
            PatientDto patientDto = patientCrudService.findPatient(lbp);
            assertNotNull(patientDto);
            assertTrue(patientDto.isDeleted());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @When("Kada se proslede novi licni podaci pacijenta sa lbp-om {string}")
    public void kada_se_proslede_novi_licni_podaci_pacijenta_sa_lbp_om(String lbp) {
        PatientUpdateDto updateDto = new PatientUpdateDto();
        updateDto.setEmail("patient@email.com");
        updateDto.setCitizenship(CountryCode.SRB);
        updateDto.setJmbg("12345678910");
        updateDto.setBirthPlace("Beograd");
        updateDto.setGender(Gender.MUSKO);
        updateDto.setLbp(lbp);
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

        try{
            PatientDto patientDto = patientCrudService.updatePatient(updateDto);
            assertNotNull(patientDto);
            assertEquals("NovoIme", patientDto.getName());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Licni podaci pacijenta sa lbp-om {string} ce se promeniti u bazi")
    public void licni_podaci_pacijenta_sa_lbp_om_ce_se_promeniti_u_bazi(String lbp) {
        try{
            PatientDto patientDto = patientCrudService.findPatient(lbp);
            assertNotNull(patientDto);
            assertEquals("NovoIme", patientDto.getName());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

}
