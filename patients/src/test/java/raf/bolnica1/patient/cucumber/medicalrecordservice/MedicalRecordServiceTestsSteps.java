package raf.bolnica1.patient.cucumber.medicalrecordservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalRecordService;
import raf.bolnica1.patient.services.impl.MedicalRecordServiceImpl;

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

    @Autowired
    OperationRepository operationRepository;

    private Throwable t;
    private List<AllergyDto> allergyes;
    private List<VaccinationDto> vaccinationDtos;
    private List<DiagnosisCodeDto> diagnosisCodes;

    private Allergy allergy;
    private Allergy allergySend;

    private Optional<Patient> patientOpt;

    private Patient patientSend;

    private Optional<MedicalRecord> medicalRecordOpt;

    private MedicalRecord medicalRecordSend;

    private Vaccination vaccination;

    private VaccinationDataDto vaccinationDto;

    private OperationCreateDto operationCreateDto;

    private GeneralMedicalDataCreateDto generalMedicalDataCreateDto;

    Patient getPatient(){

        List<Patient> patients = patientRepository.findAll();
        Patient p = patients.get(0);
        return p;
    }

    Patient getInvalidPatient(){
        List<Patient> patients = patientRepository.findAll();
        Optional<MedicalRecord> m;
        for(Patient p: patients){
            if(!medicalRecordRepository.findByPatient(p).isPresent()){
                return p;
            }
        }
        return null;
    }

    Allergy getAllergy(){
        Allergy a1;
        List<Allergy> allergies = allergyRepository.findAll();
        a1 = allergies.get(0);
        return a1;
    }

    MedicalRecord getRecord(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        MedicalRecord m;
        m = medicalRecords.get(0);
        return m;
    }
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


    @When("Kada se doda nova alergija")
    public void kada_se_doda_nova_alergija() {

        allergySend = getAllergy();
        patientSend = getPatient();
        medicalRecordSend = getRecord();



        try{
            allergy =  allergyRepository.findByName(allergySend.getName());
            assertNotNull(allergy);
            assertEquals(allergySend.getId(),allergy.getId());

            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getId(), patientOpt.get().getId());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientOpt.get());
            assertNotNull(medicalRecordOpt.get());
            assertEquals(medicalRecordSend.getId(),medicalRecordOpt.get().getId());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Ta alergija treba biti sacuvana u bazi")
    public void ta_alergija_treba_biti_sacuvana_u_bazi() {
        try{
            MessageDto messageDto = medicalRecordService.addAllergy(patientOpt.get().getLbp(),allergySend.getName());
            assertEquals("Uspesno dodata alergija.",messageDto.getMessage());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @When("Kada se doda nova alergija za pacijenta sa lbp-om koji nema svoj karton odogovor treba da bude da alergija nije uspesno dodata")
    public void kada_se_doda_nova_alergija_za_pacijenta_sa_lbp_om_koji_nema_svoj_karton_odogovor_treba_da_bude_da_alergija_nije_uspesno_dodata() {
        allergySend = getAllergy();
        patientSend = getInvalidPatient();

        try{
            allergy =  allergyRepository.findByName(allergySend.getName());
            assertNotNull(allergy);
            assertEquals(allergySend.getId(),allergy.getId());

            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getId(), patientOpt.get().getId());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientOpt.get());

            medicalRecordService.addAllergy(patientOpt.get().getLbp(),allergySend.getName());
        }catch (Exception e){
            assertEquals("Neuspesno dodata alergija.",e.getMessage());
            fail(e.getMessage());
        }
    }





    @When("Kada se doda nova vakcina")
    public void kada_se_doda_nova_vakcina() {

        vaccinationDto =  new VaccinationDataDto();
        vaccinationDto.setVaccinationName("PRIORIX");
        vaccinationDto.setVaccinationDate(Date.valueOf("2022-05-04"));

        patientSend = getPatient();
        medicalRecordSend = getRecord();

        try{
            vaccination = vaccinationRepository.findByName(vaccinationDto.getVaccinationName());
            assertNotNull(vaccination);
            assertEquals(vaccinationDto.getVaccinationName(),vaccination.getName());

            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getId(), patientOpt.get().getId());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientOpt.get());
            assertNotNull(medicalRecordOpt.get());
            assertEquals(medicalRecordSend.getId(),medicalRecordOpt.get().getId());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Ta vakcina treba biti sacuvana u bazi")
    public void ta_vakcina_treba_biti_sacuvana_u_bazi() {
        try{
            MessageDto messageDto = medicalRecordService.addVaccine(patientOpt.get().getLbp(),vaccinationDto);
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

        patientSend = getInvalidPatient();
        medicalRecordSend = getRecord();
        try{
            vaccination = vaccinationRepository.findByName(vaccinationDto.getVaccinationName());
            assertNotNull(vaccination);
            assertEquals(vaccinationDto.getVaccinationName(),vaccination.getName());

            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getId(), patientOpt.get().getId());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientSend);

            medicalRecordService.addVaccine(patientSend.getLbp(),vaccinationDto);
        }catch (Exception e){
            assertEquals("Neuspesno dodata vakcina.",e.getMessage());
            fail(e.getMessage());
        }
        
    }




    @When("Kada se doda nova operacija")
    public void kada_se_doda_nova_operacija() {

        operationCreateDto = new OperationCreateDto();
        operationCreateDto.setHospitalId(1L);
        operationCreateDto.setDepartmentId(1L);
        operationCreateDto.setOperationDate(Date.valueOf("2023-08-12"));
        operationCreateDto.setDescription("Appendectomy");

        patientSend = getPatient();
        medicalRecordSend = getRecord();
        try{
            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getId(), patientOpt.get().getId());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientOpt.get());
            assertNotNull(medicalRecordOpt.get());
            assertEquals(medicalRecordSend.getId(),medicalRecordOpt.get().getId());
        }catch (Exception e){
            fail(e.getMessage());
        }


    }
    @Then("Ta operacija treba biti sacuvana u bazi")
    public void ta_operacija_treba_biti_sacuvana_u_bazi() {
        try{
            OperationDto dto = medicalRecordService.addOperation(patientOpt.get().getLbp(),operationCreateDto);
            assertNotNull(dto);
            assertEquals(dto.getId(),operationRepository.findById(dto.getId()).get().getId());
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
            t = assertThrows(RuntimeException.class, () ->medicalRecordService.addOperation("1", operationCreateDto));

        }catch (Exception e){
            assertEquals("Patient with lbp 1 not found.",t.getMessage());
            fail(e.getMessage());
        }

    }


    @When("Kada se dodaju osnovni zdravstveni podaci")
    public void kada_se_dodaju_osnovni_zdravstveni_podaci() {

        List<Allergy> allergyesRes = allergyRepository.findAll();

        List<AllergyDto> allergyDtos =  new ArrayList<>();

        for(Allergy a: allergyesRes){
            AllergyDto f = new AllergyDto();
            f.setName(a.getName());
            allergyDtos.add(f);
        }

        List<Vaccination> vaccinations = vaccinationRepository.findAll();

        List<VaccinationDto> vaccinationDtos1 =  new ArrayList<>();

        for(Vaccination a: vaccinations){
            VaccinationDto f = new VaccinationDto();
            f.setName(a.getName());
            f.setType(a.getType());
            f.setDescription(a.getDescription());
            f.setManufacturer(a.getManufacturer());
            vaccinationDtos1.add(f);
        }

        patientSend = getPatient();
        medicalRecordSend = getRecord();

        generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setBloodType(medicalRecordSend.getGeneralMedicalData().getBloodType());
        generalMedicalDataCreateDto.setRH(medicalRecordSend.getGeneralMedicalData().getRH());
        generalMedicalDataCreateDto.setAllergyDtos(allergyDtos);
        generalMedicalDataCreateDto.setVaccinationDtos(vaccinationDtos1);

        try{
            patientOpt =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patientOpt.get());
            assertEquals(patientSend.getLbp(), patientOpt.get().getLbp());

            medicalRecordOpt = medicalRecordRepository.findByPatient(patientOpt.get());
            assertNotNull(medicalRecordOpt.get());
            assertEquals(medicalRecordSend.getId(),medicalRecordOpt.get().getId());

            for (VaccinationDto dto: vaccinationDtos1){
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
            GeneralMedicalDataDto generalMedicalData1 = medicalRecordService.addGeneralMedicalData(patientOpt.get().getLbp(), generalMedicalDataCreateDto);
            assertNotNull(generalMedicalData1);
            assertEquals(generalMedicalDataCreateDto.getBloodType(),generalMedicalData1.getBloodType());
        }catch (Exception e){
            fail(e.getMessage());
        }

    }



}
