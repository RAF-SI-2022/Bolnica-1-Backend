package raf.bolnica1.patient.cucumber.medicalRecordJefimija;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.cucumber.dataGenerators.classes.domain.AllergyGenerator;
import raf.bolnica1.patient.cucumber.dataGenerators.classes.domain.VaccinationGenerator;
import raf.bolnica1.patient.cucumber.dataGenerators.classes.dto.medicalRecord.MedicalRecordGenerator;
import raf.bolnica1.patient.cucumber.dataGenerators.classes.dto.patient.PatientCreateDtoGenerator;
import raf.bolnica1.patient.cucumber.validation.ClassJsonComparator;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.services.MedicalRecordService;
import raf.bolnica1.patient.services.PatientCrudService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MedicalRecordIntegrationTests extends MedicalRecordIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PatientCreateDtoGenerator patientCreateDtoGenerator;
    @Autowired
    private MedicalRecordGenerator medicalRecordGenerator;
    @Autowired
    private VaccinationGenerator vaccinationGenerator;
    @Autowired
    private AllergyGenerator allergyGenerator;

    /// SERVICES
    @Autowired
    private PatientCrudService patientService;
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    private String lbp = null;
    private Long medRecordId = 0L;
    private int count;
    private Object check;

    @When("dodamo novog pacijenta u sistem")
    public void dodaj_pacijenta() {
        PatientCreateDto patientCreateDto = patientCreateDtoGenerator.generatePatientCreateDto();
        assertTrue(patientCreateDto != null);
        Patient patient = patientRepository.findByLbp(patientCreateDto.getLbp()).orElse(null);

        if(patient == null) {
            PatientDto patientDto = patientService.registerPatient(patientCreateDto);
            lbp = patientDto.getLbp();
            assertTrue(classJsonComparator.compareCommonFields(patientDto, patientCreateDto));
            check = patientDto;
        }else
            lbp = patient.getLbp();
    }

    @Then("mozemo pronaci pacijenta na osnovu jedinstvenog lbp-a")
    public void nadji_pacijenta(){
        PatientDto patient = patientService.findPatient(lbp);
        assertTrue(patient!=null);
        if(check instanceof PatientDto){
            assertTrue(classJsonComparator.compareCommonFields(patient, ((PatientDto) check)));
        }
    }

    @When("nadjemo karton za pacijenta")
    public void pronadji_karton() {
        dodaj_pacijenta();
        MedicalRecordDto medicalRecordDto = medicalRecordService.findMedicalRecord(lbp);
        assertTrue(medicalRecordDto != null);
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patientRepository.findByLbp(lbp).orElse(null)).orElse(null);
        assertTrue(medicalRecord != null);
        assertTrue(medicalRecord.getId() == medicalRecord.getId());
        assertTrue(classJsonComparator.compareCommonFields(medicalRecord.getGeneralMedicalData(), medicalRecordDto.getGeneralMedicalDataDto()));
    }

    @Then("upisujemo krvnu grupu i rh faktor")
    public void upisi_opste_podatke_u_karton(){
        GeneralMedicalDataDto generalMedicalDataDto = medicalRecordService.addGeneralMedicalData(lbp, medicalRecordGenerator.generateGeneralMedicalDataCreateDto());
        assertTrue(generalMedicalDataDto != null);
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patientRepository.findByLbp(lbp).orElse(null)).orElse(null);
        assertTrue(medicalRecord != null);
        assertTrue(medicalRecord.getGeneralMedicalData() != null);
        assertTrue(classJsonComparator.compareCommonFields(medicalRecord.getGeneralMedicalData(),generalMedicalDataDto));
        check = medicalRecord.getGeneralMedicalData();
    }

    @Then("mozemo naci podatke o krvnoj grupi i faktoru")
    public void nadji_podatke_o_krvnoj_grupi_i_faktoru(){
        MedicalRecordDto medicalRecordDto = medicalRecordService.findMedicalRecord(lbp);
        assertTrue(medicalRecordDto != null);
        assertTrue(medicalRecordDto.getGeneralMedicalDataDto() != null);
        assertTrue(medicalRecordDto.getGeneralMedicalDataDto().getBloodType() != null);
        assertTrue(medicalRecordDto.getGeneralMedicalDataDto().getRH() != null);
        if(check instanceof GeneralMedicalDataDto){
            assertTrue(classJsonComparator.compareCommonFields(medicalRecordDto.getGeneralMedicalDataDto(), ((GeneralMedicalDataDto) check)));
        }
    }

    @Then("dodajemo operaciju")
    public void dodaj_operaciju(){
        count = medicalRecordService.findMedicalRecord(lbp).getOperationDtos().size();
        OperationCreateDto operationCreateDto = medicalRecordGenerator.generateOperationCreateDto();
        OperationDto operationDto = medicalRecordService.addOperation(lbp, operationCreateDto);
        assertTrue(operationDto != null);
        assertTrue(classJsonComparator.compareCommonFields(operationDto,operationCreateDto));
        check = operationDto;
    }

    @Then("mozemo videti da li se dodala operacija za pacijenta")
    public void proveri_da_li_je_operacija_dodata(){
        MedicalRecordDto medicalRecordDto = medicalRecordService.findMedicalRecord(lbp);
        assertTrue(medicalRecordDto != null);
        assertTrue(medicalRecordDto.getOperationDtos().size() == count + 1);

        boolean exist = false;
        if(check instanceof OperationDto){
            for(OperationDto operationDto : medicalRecordDto.getOperationDtos()){
                if(classJsonComparator.compareAllFields(operationDto, ((OperationDto) check))){
                    exist = true;
                    break;
                }
            }
        }
        assertTrue(exist);
    }

    @Then("dodajemo novu vakcinu za pacijenta")
    public void dodaj_vakcinu(){
        vaccinationGenerator.generate();
        GeneralMedicalDataDto generalMedicalDataDto = medicalRecordService.findMedicalRecord(lbp).getGeneralMedicalDataDto();
        if(generalMedicalDataDto == null || (generalMedicalDataDto.getVaccinationDtos() == null))
            count = 0;
        else
            count = generalMedicalDataDto.getVaccinationDtos().size();

        String vaccine = vaccinationGenerator.getRandomVacc().getName();
        MessageDto messageDto = medicalRecordService.addVaccine(lbp, medicalRecordGenerator.generateVaccinationCreateDto(vaccine));
        assertTrue(messageDto != null);
        check = vaccine;
    }

    @Then("mozemo videti da li se dodala vakcina za pacijenta")
    public void proveri_da_li_je_vakcina_dodata(){
        GeneralMedicalDataDto generalMedicalDataDto = medicalRecordService.findMedicalRecord(lbp).getGeneralMedicalDataDto();
        assertTrue(generalMedicalDataDto != null);
        assertTrue(generalMedicalDataDto.getVaccinationDtos() != null);
        assertTrue(generalMedicalDataDto.getVaccinationDtos() != null);
        assertTrue(generalMedicalDataDto.getVaccinationDtos().size() == count + 1);

        boolean exist = false;
        if(check != null && check instanceof String){
            for(VaccinationDto vaccination : generalMedicalDataDto.getVaccinationDtos()){
                if(vaccination.getName().equals(check)){
                    exist = true;
                    break;
                }
            }
        }
        assertTrue(exist);
    }

    @Then("dodajemo novu alergiju za pacijenta")
    public void dodaj_alergiju(){
        allergyGenerator.generate();
        GeneralMedicalDataDto generalMedicalDataDto = medicalRecordService.findMedicalRecord(lbp).getGeneralMedicalDataDto();
        if(generalMedicalDataDto == null || (generalMedicalDataDto.getAllergyDtos() == null))
            count = 0;
        else
            count = generalMedicalDataDto.getAllergyDtos().size();

        String allergy = allergyGenerator.getRandomAllergy().getName();
        MessageDto messageDto = medicalRecordService.addAllergy(lbp, allergy);
        assertTrue(messageDto != null);
        check = allergy;
    }

    @Then("mozemo videti da li se dodala alergija za pacijenta")
    public void proveri_da_li_je_alergija_dodata(){
        GeneralMedicalDataDto generalMedicalDataDto = medicalRecordService.findMedicalRecord(lbp).getGeneralMedicalDataDto();
        assertTrue(generalMedicalDataDto != null);
        assertTrue(generalMedicalDataDto.getAllergyDtos() != null);
        assertTrue(generalMedicalDataDto.getAllergyDtos() != null);
        assertTrue(generalMedicalDataDto.getAllergyDtos().size() == count + 1);

        boolean exist = false;
        if(check != null && check instanceof String){
            for(AllergyDto allergy : generalMedicalDataDto.getAllergyDtos()){
                if(allergy.getName().equals(check)){
                    exist = true;
                    break;
                }
            }
        }
        assertTrue(exist);
    }

}
