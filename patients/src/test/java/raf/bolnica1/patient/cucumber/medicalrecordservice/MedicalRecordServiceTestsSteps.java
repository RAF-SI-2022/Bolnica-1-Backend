package raf.bolnica1.patient.cucumber.medicalrecordservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.VaccinationType;
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


    private List<AllergyDto> allergy;
    private List<VaccinationDto> vaccinationDtos;
    private List<DiagnosisCodeDto> diagnosisCodes;

    private Allergy allergy2;

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
        allergy = medicalRecordService.gatherAllergies();

    }
    @Then("Odgovor treba da sadrzi listu svih alergija")
    public void odgovor_treba_da_sadrzi_listu_svih_alergija() {
        assertNotNull(allergy);
        assertFalse(allergy.isEmpty());

    }



    @When("Kada pronadjemo sve vakcine")
    public void kada_pronadjemo_sve_vakcine() {
        vaccinationDtos = medicalRecordService.gatherVaccines();
    }
    @Then("Odgovor treba da sadrzi listu svih vakcina")
    public void odgovor_treba_da_sadrzi_listu_svih_vakcina() {
        assertNotNull(vaccinationDtos);
        assertFalse(vaccinationDtos.isEmpty());
    }



    @When("Kada pronadjemo sve dijagnoze")
    public void kada_pronadjemo_sve_dijagnoze() {
        diagnosisCodes = medicalRecordService.gatherDiagnosis();
    }
    @Then("Odgovor treba da sadrzi listu svih dijagnoza")
    public void odgovor_treba_da_sadrzi_listu_svih_dijagnoza() {
        assertNotNull(diagnosisCodes);
        assertFalse(diagnosisCodes.isEmpty());
    }




    @When("Kada se doda nova alergija")
    public void kada_se_doda_nova_alergija() {
        allergy2 =  allergyRepository.findByName(name);
        assertNotNull(allergy2);

        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

    }
    @Then("Ta alergija treba biti sacuvana u bazi")
    public void ta_alergija_treba_biti_sacuvana_u_bazi() {
        MessageDto messageDto = medicalRecordService.addAllergy(lbp,name);
        assertEquals("Uspesno dodata alergija.",messageDto.getMessage());
    }




    @When("Kada se doda nova vakcina")
    public void kada_se_doda_nova_vakcina() {

        vaccinationDto =  new VaccinationDataDto();
        vaccinationDto.setVaccinationName("PRIORIX");
        vaccinationDto.setVaccinationDate(Date.valueOf("2022-05-04"));

       vaccination = vaccinationRepository.findByName(vaccinationDto.getVaccinationName());
       assertNotNull(vaccination);

        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

    }
    @Then("Ta vakcina treba biti sacuvana u bazi")
    public void ta_vakcina_treba_biti_sacuvana_u_bazi() {
        MessageDto messageDto = medicalRecordService.addVaccine(lbp,vaccinationDto);
        assertEquals("Uspesno dodata vakcina.",messageDto.getMessage());
    }



    @When("Kada se doda nova operacija")
    public void kada_se_doda_nova_operacija() {

        operationCreateDto = new OperationCreateDto();
        operationCreateDto.setHospitalId(1L);
        operationCreateDto.setDepartmentId(1L);
        operationCreateDto.setOperationDate(Date.valueOf("2023-08-12"));
        operationCreateDto.setDescription("Appendectomy");

        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());
    }
    @Then("Ta operacija treba biti sacuvana u bazi")
    public void ta_operacija_treba_biti_sacuvana_u_bazi() {

        OperationDto dto = medicalRecordService.addOperation(lbp,operationCreateDto);

        assertNotNull(dto);
        assertEquals(1L,dto.getDepartmentId());

    }


    @When("Kada se dodaju osnovni zdravstveni podaci")
    public void kada_se_dodaju_osnovni_zdravstveni_podaci() {

        List<AllergyDto> allergyDtos = new ArrayList<>();

        AllergyDto allergyDto = new AllergyDto();
        allergyDto.setName("Mleko");
        AllergyDto allergyDto2 = new AllergyDto();
        allergyDto.setName("Orašasti plodovi");
        AllergyDto allergyDto3 = new AllergyDto();
        allergyDto.setName("Pšenica");
        AllergyDto allergyDto4 = new AllergyDto();
        allergyDto.setName("Penicilin");
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


        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());


        generalMedicalDataCreateDto = new GeneralMedicalDataCreateDto();
        generalMedicalDataCreateDto.setBloodType("A");
        generalMedicalDataCreateDto.setRH("+");
        generalMedicalDataCreateDto.setAllergyDtos(allergyDtos);
        generalMedicalDataCreateDto.setVaccinationDtos(vaccinationDtos1);

        assertNotNull(vaccinationRepository.findByName(vaccinationDto1.getName()));

        assertNotNull(allergyRepository.findByName(allergyDto.getName()));
    }
    @Then("Ti podaci treba biti sacuvana u bazi")
    public void ti_podaci_treba_biti_sacuvana_u_bazi() {

        GeneralMedicalDataDto generalMedicalData1 = medicalRecordService.addGeneralMedicalData(lbp,generalMedicalDataCreateDto);

        assertNotNull(generalMedicalData1);
        assertEquals("A",generalMedicalData1.getBloodType());
    }



}
