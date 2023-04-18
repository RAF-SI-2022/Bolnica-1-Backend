package raf.bolnica1.patient.cucumber.medicalrecordservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalRecordService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordServiceTestsSteps extends MedicalRecordServiceTestsConfig{

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    AllergyRepository allergyRepository;

    @Autowired
    AllergyDataRepository allergyDataRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    VaccinationRepository vaccinationRepository;


    private List<AllergyDto> allergyes;
    private List<VaccinationDto> vaccinationDtos;
    private List<DiagnosisCodeDto> diagnosisCodes;

    private Allergy allergy;

    private Optional<Patient> patient;

    private Optional<MedicalRecord> medicalRecord;

    private Vaccination vaccination;

    private VaccinationDataDto vaccinationDto;

    private OperationCreateDto operationCreateDto;

    private GeneralMedicalDataCreateDto generalMedicalDataCreateDto;
    private String name = "Mleko";

    private String lbp = "P0002";

    @When("Kada pronadjemo sve alergije")
    public void kada_pronadjemo_sve_alergije() {
        try{
            allergyes = medicalRecordService.gatherAllergies();
        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Odgovor treba da sadrzi listu svih alergija")
    public void odgovor_treba_da_sadrzi_listu_svih_alergija() {
        assertNotNull(allergyes);
        assertFalse(allergyes.isEmpty());

    }



    @When("Kada pronadjemo sve vakcine")
    public void kada_pronadjemo_sve_vakcine() {
        try{
            vaccinationDtos = medicalRecordService.gatherVaccines();
        }catch (Exception e){
            fail(e.getMessage());
        }


    }
    @Then("Odgovor treba da sadrzi listu svih vakcina")
    public void odgovor_treba_da_sadrzi_listu_svih_vakcina() {
        assertNotNull(vaccinationDtos);
        assertFalse(vaccinationDtos.isEmpty());
    }



    @When("Kada pronadjemo sve dijagnoze")
    public void kada_pronadjemo_sve_dijagnoze() {
        try{
            diagnosisCodes = medicalRecordService.gatherDiagnosis();
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Odgovor treba da sadrzi listu svih dijagnoza")
    public void odgovor_treba_da_sadrzi_listu_svih_dijagnoza() {
        assertNotNull(diagnosisCodes);
        assertFalse(diagnosisCodes.isEmpty());
    }


    public Patient patientCompate(){
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

        return patient1;
    }

    public Patient invalidPatientCopare(){
        Patient patient6 = new Patient();
        patient6.setJmbg("0607995000001");
        patient6.setLbp("P0007");
        patient6.setName("Michael");
        patient6.setParentName("John");
        patient6.setSurname("Johnson");
        patient6.setGender(Gender.MUSKO);
        patient6.setDateOfBirth(Date.valueOf("1989-5-4"));
        patient6.setBirthPlace("Toronto");
        patient6.setPlaceOfLiving("Canada");
        patient6.setResidenceCountry(CountryCode.CAN);
        patient6.setCitizenship(CountryCode.CAN);
        patient6.setPhone("+35498214756");
        patient6.setEmail("michael.johnson@example.com");
        patient6.setGuardianJmbg("0485363054001");
        patient6.setGuardianNameAndSurname("Maria Garcia");
        SocialData socialData6 = new SocialData();
        socialData6.setMaritalStatus(MaritalStatus.RAZVEDENI);
        socialData6.setNumOfChildren(2);
        socialData6.setExpertiseDegree(ExpertiseDegree.VISE);
        socialData6.setProfession("Architect");
        socialData6.setFamilyStatus(FamilyStatus.JEDAN_RODITELJ);
        patient6.setSocialData(socialData6);

        return patient6;
    }

    public MedicalRecord medicalRecordCompare(Patient patient){
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setPatient(patient);
        medicalRecord1.setRegistrationDate(Date.valueOf("2023-03-04"));
        medicalRecord1.setDeleted(false);
        GeneralMedicalData generalMedicalData1 = new GeneralMedicalData();
        generalMedicalData1.setBloodType("A");
        generalMedicalData1.setRH("+");
        medicalRecord1.setGeneralMedicalData(generalMedicalData1);
        return medicalRecord1;
    }

    @When("Kada se doda nova alergija")
    public void kada_se_doda_nova_alergija() {

        Allergy a1 = new Allergy();
        a1.setName("Mleko");

        Patient patient1 = patientCompate();
        MedicalRecord medicalRecord1 = medicalRecordCompare(patient1);

        try{
            allergy =  allergyRepository.findByName(name);
            assertNotNull(allergy);
            assertEquals(a1.getName(),allergy.getName());

            patient =  patientRepository.findByLbp(lbp);
            assertNotNull(patient.get());
            assertEquals(patient1.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecord1.getPatient().getLbp(),medicalRecord.get().getPatient().getLbp());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Ta alergija treba biti sacuvana u bazi")
    public void ta_alergija_treba_biti_sacuvana_u_bazi() {
        try{
            MessageDto messageDto = medicalRecordService.addAllergy(lbp,name);
            assertEquals("Uspesno dodata alergija.",messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @When("Kada se doda nova alergija za pacijenta sa lbp-om koji nema svoj karton odogovor treba da bude da alergija nije uspesno dodata")
    public void kada_se_doda_nova_alergija_za_pacijenta_sa_lbp_om_koji_nema_svoj_karton_odogovor_treba_da_bude_da_alergija_nije_uspesno_dodata() {
        Allergy a1 = new Allergy();
        a1.setName("Mleko");

        Patient patient6 = invalidPatientCopare();

        try{
            allergy =  allergyRepository.findByName(name);
            assertNotNull(allergy);
            assertEquals(a1.getName(),allergy.getName());

            patient =  patientRepository.findByLbp("P0007");
            assertNotNull(patient.get());
            assertEquals(patient6.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        }catch (Exception e){
            assertEquals("Neuspesno dodata alergija.",e.getMessage());
            fail(e.getMessage());
        }

//        MessageDto messageDto = medicalRecordService.addAllergy(lbp,name);
//        assertEquals("Uspesno dodata alergija.",messageDto.getMessage());
    }





    @When("Kada se doda nova vakcina")
    public void kada_se_doda_nova_vakcina() {
        vaccinationDto =  new VaccinationDataDto();
        vaccinationDto.setVaccinationName("PRIORIX");
        vaccinationDto.setVaccinationDate(Date.valueOf("2022-05-04"));

        Patient patient1 = patientCompate();
        MedicalRecord medicalRecord1 = medicalRecordCompare(patient1);

        try{
            vaccination = vaccinationRepository.findByName(vaccinationDto.getVaccinationName());
            assertNotNull(vaccination);
            assertEquals(vaccinationDto.getVaccinationName(),vaccination.getName());

            patient =  patientRepository.findByLbp(lbp);
            assertNotNull(patient.get());
            assertEquals(patient1.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecord1.getPatient().getLbp(),medicalRecord.get().getPatient().getLbp());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Ta vakcina treba biti sacuvana u bazi")
    public void ta_vakcina_treba_biti_sacuvana_u_bazi() {
        try{
            MessageDto messageDto = medicalRecordService.addVaccine(lbp,vaccinationDto);
            assertEquals("Uspesno dodata vakcina.",messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }

    }

    @When("Kada se doda nova vakcina za pacijenta sa lbp-om koji nema svoj karton odogovor treba da bude da vakcina nije uspesno dodata")
    public void kada_se_doda_nova_vakcina_za_pacijenta_sa_lbp_om_koji_nema_svoj_karton_odogovor_treba_da_bude_da_vakcina_nije_uspesno_dodata() {
        vaccinationDto =  new VaccinationDataDto();
        vaccinationDto.setVaccinationName("PRIORIX");
        vaccinationDto.setVaccinationDate(Date.valueOf("2022-05-04"));

        Patient patient6 = invalidPatientCopare();
        try{
            vaccination = vaccinationRepository.findByName(vaccinationDto.getVaccinationName());
            assertNotNull(vaccination);
            assertEquals(vaccinationDto.getVaccinationName(),vaccination.getName());

            patient =  patientRepository.findByLbp("P0007");
            assertNotNull(patient.get());
            assertEquals(patient6.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        }catch (Exception e){
            assertEquals("Neuspesno dodata vakcina.",e.getMessage());
            fail(e.getMessage());
        }


//        MessageDto messageDto = medicalRecordService.addVaccine(lbp,vaccinationDto);
//        assertEquals("Uspesno dodata vakcina.",messageDto.getMessage());
    }




    @When("Kada se doda nova operacija")
    public void kada_se_doda_nova_operacija() {

        operationCreateDto = new OperationCreateDto();
        operationCreateDto.setHospitalId(1L);
        operationCreateDto.setDepartmentId(1L);
        operationCreateDto.setOperationDate(Date.valueOf("2023-08-12"));
        operationCreateDto.setDescription("Appendectomy");

        Patient patient1 = patientCompate();
        MedicalRecord  medicalRecord1 = medicalRecordCompare(patient1);
        try{
            patient =  patientRepository.findByLbp(lbp);
            assertNotNull(patient.get());
            assertEquals(patient1.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecord1.getPatient().getLbp(),medicalRecord.get().getPatient().getLbp());
        }catch (Exception e){
            fail(e.getMessage());
        }


    }
    @Then("Ta operacija treba biti sacuvana u bazi")
    public void ta_operacija_treba_biti_sacuvana_u_bazi() {
        try{
            OperationDto dto = medicalRecordService.addOperation(lbp,operationCreateDto);

            assertNotNull(dto);
            assertEquals(1L,dto.getDepartmentId());
        }catch (Exception e){
            fail(e.getMessage());
        }


    }

    @When("Kada se doda nova operacija za pacijenta sa invalid lbp-om, dgovor treba da bude da pacijent ne postoji")
    public void kada_se_doda_nova_operacija_za_pacijenta_sa_invalid_lbp_om_dgovor_treba_da_bude_da_pacijent_ne_postoji() {
        operationCreateDto = new OperationCreateDto();
        operationCreateDto.setHospitalId(1L);
        operationCreateDto.setDepartmentId(1L);
        operationCreateDto.setOperationDate(Date.valueOf("2023-08-12"));
        operationCreateDto.setDescription("Appendectomy");

        try{
            patient =  patientRepository.findByLbp("1");

        }catch (Exception e){
            assertEquals("Patient with lbp 1 not found.",e.getMessage());
            fail(e.getMessage());
        }

//        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
//        assertNotNull(medicalRecord.get());
//
//        OperationDto dto = medicalRecordService.addOperation(lbp,operationCreateDto);
//
//        assertNotNull(dto);
//        assertEquals(1L,dto.getDepartmentId());

    }


    @When("Kada se dodaju osnovni zdravstveni podaci")
    public void kada_se_dodaju_osnovni_zdravstveni_podaci() {

        List<AllergyDto> allergyDtos = new ArrayList<>();

        AllergyDto allergyDto = new AllergyDto();
        allergyDto.setName("Mleko");
        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto2.setName("Orašasti plodovi");
        AllergyDto allergyDto3 = new AllergyDto();
        allergyDto3.setName("Pšenica");
        AllergyDto allergyDto4 = new AllergyDto();
        allergyDto4.setName("Penicilin");
        allergyDtos.add(allergyDto);
        allergyDtos.add(allergyDto2);
        allergyDtos.add(allergyDto3);
        allergyDtos.add(allergyDto4);

        List<VaccinationDto> vaccinationDtos1 = new ArrayList<>();

        VaccinationDto vaccinationDto1 = new VaccinationDto();
        vaccinationDto1.setName("PRIORIX");
        vaccinationDto1.setVaccinationDate(Date.valueOf("2022-07-04"));
        vaccinationDto1.setDescription("Vakcine protiv pneumokoka");
        vaccinationDto1.setType(VaccinationType.BACTERIA);
        vaccinationDto1.setManufacturer("GlaxoSmithKline Biologicals S.A.,Belgija");
        vaccinationDtos1.add(vaccinationDto1);

        Patient patient1 = patientCompate();
        MedicalRecord medicalRecord1 =  medicalRecordCompare(patient1);

        generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setBloodType("A");
        generalMedicalDataCreateDto.setRH("+");
        generalMedicalDataCreateDto.setAllergyDtos(allergyDtos);
        generalMedicalDataCreateDto.setVaccinationDtos(vaccinationDtos1);

        try{
            patient =  patientRepository.findByLbp(lbp);
            assertNotNull(patient.get());
            assertEquals(patient1.getLbp(),patient.get().getLbp());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecord1.getPatient().getLbp(),medicalRecord.get().getPatient().getLbp());


            assertNotNull(vaccinationRepository.findByName(vaccinationDto1.getName()));

            for (VaccinationDto dto:vaccinationDtos1){
                assertEquals(dto.getName(),vaccinationRepository.findByName(dto.getName()).getName());
            }


            for (AllergyDto dto:allergyDtos){
                assertNotNull(allergyRepository.findByName(dto.getName()));
            }

            for (AllergyDto dto:allergyDtos){
                assertEquals(dto.getName(),allergyRepository.findByName(dto.getName()).getName());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Ti podaci treba biti sacuvana u bazi")
    public void ti_podaci_treba_biti_sacuvana_u_bazi() {

        try{
            GeneralMedicalDataDto generalMedicalData1 = medicalRecordService.addGeneralMedicalData(lbp,generalMedicalDataCreateDto);

            assertNotNull(generalMedicalData1);
            assertEquals("A",generalMedicalData1.getBloodType());
        }catch (Exception e){
            fail(e.getMessage());
        }

    }



}
