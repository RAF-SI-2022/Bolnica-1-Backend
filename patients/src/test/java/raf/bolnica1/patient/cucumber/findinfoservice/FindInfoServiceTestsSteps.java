package raf.bolnica1.patient.cucumber.findinfoservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.FindInfoService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FindInfoServiceTestsSteps extends FindInfoServiceTestsConfig{

    @Autowired
    FindInfoService findInfoService;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    VaccinationDataRepository vaccinationDataRepository;

    @Autowired
    AllergyDataRepository allergyDataRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    ExaminationHistoryRepository examinationHistoryRepository;


    private Optional<Patient> patient;

    private Optional<MedicalRecord> medicalRecord;

    private GeneralMedicalData generalMedicalData;

    private String lbp = "P0002";

    private int size = 2;
    private int page = 0;

    private Date startDate  = Date.valueOf("2023-02-04");
    private Date endDate = Date.valueOf("2023-02-04");

    private String code = "A15.3";

    @When("Kada se prosledi lbp preko koga dohvatamo osnovne medicinske podatke")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_osnovne_medicinske_podatke() {

        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        generalMedicalData = medicalRecord.get().getGeneralMedicalData();


        List<Object[]> vaccinationsAndDates =  vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData);
        assertNotNull(vaccinationsAndDates);
        assertFalse(vaccinationsAndDates.isEmpty());

        List<Allergy> allergies=allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData);
        assertNotNull(allergies);
        assertFalse(allergies.isEmpty());



    }
    @Then("Odgovor treba da sadrzi odgovarajuci osnovni medicinski podaci")
    public void odgovor_treba_da_sadrzi_odgovarajuci_osnovni_medicinski_podaci() {
        GeneralMedicalDataDto generalMedicalDataDto = findInfoService.findGeneralMedicalDataByLbp(lbp);
        assertNotNull(generalMedicalDataDto);
        assertEquals("A",generalMedicalDataDto.getBloodType());
    }


    @When("Kada se prosledi lbp preko koga dohvatamo operaciju")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_operaciju() {
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        List<Operation> operations=operationRepository.findOperationsByMedicalRecord(medicalRecord.get());
        assertNotNull(operations);
        assertFalse(operations.isEmpty());


    }
    @Then("Odgovor treba da sadrzi odgovarajuce operacije")
    public void odgovor_treba_da_sadrzi_odgovarajuce_operacije() {
        List<OperationDto> operationDtos = findInfoService.findOperationsByLbp(lbp);
        assertNotNull(operationDtos);
        assertFalse(operationDtos.isEmpty());
    }


    @When("Kada se prosledi lbp preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_istoriju() {
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());


        List<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecord(medicalRecord.get());
        assertNotNull(medicalHistories);
        assertFalse(medicalHistories.isEmpty());
    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju() {
        List<MedicalHistoryDto> medicalHistoryDtos = findInfoService.findMedicalHistoryByLbp(lbp);
        assertNotNull(medicalHistoryDtos);
        assertFalse(medicalHistoryDtos.isEmpty());
    }


    @When("Kada se prosledi lbp preko koga dohvatamo istoriju pregleda")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_istoriju_pregleda() {
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        List<ExaminationHistory> examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecord(medicalRecord.get());
        assertNotNull(examinationHistories);
        assertFalse(examinationHistories.isEmpty());

    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju pregleda")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju_pregleda() {
        List<ExaminationHistoryDto> examinationHistoryDtos = findInfoService.findExaminationHistoryByLbp(lbp);
        assertNotNull(examinationHistoryDtos);
        assertFalse(examinationHistoryDtos.isEmpty());
    }



    @When("Kada se prosledi lbp preko koga dohvatamo zdravstveni karton")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_zdravstveni_karton() {
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());
    }
    @Then("Odgovor treba da sadrzi odgovarajuci zdravstveni karton")
    public void odgovor_treba_da_sadrzi_odgovarajuci_zdravstveni_karton() {
        MedicalRecordDto dto = findInfoService.findMedicalRecordByLbp(lbp);
        assertNotNull(dto);

    }


    @When("Kada se prosledi lbp i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_i_broj_strane_preko_koga_dohvatamo_istoriju() {

        Pageable pageable= PageRequest.of(page,size);
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordPaged(pageable,medicalRecord.get());
        assertNotNull(medicalHistories);
        assertFalse(medicalHistories.isEmpty());

    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju sa te strane")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju_sa_te_strane() {
        Page<MedicalHistoryDto> medicalHistoryDtos = findInfoService.findMedicalHistoryByLbpPaged(lbp,page,size);
        assertNotNull(medicalHistoryDtos);
        assertFalse(medicalHistoryDtos.isEmpty());
    }


    @When("Kada se prosledi lbp, kod dijagnoze i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_kod_dijagnoze_i_broj_strane_preko_koga_dohvatamo_istoriju() {
        Pageable pageable= PageRequest.of(page,size);
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(pageable,medicalRecord.get(),code);
        assertNotNull(medicalHistories);
        assertFalse(medicalHistories.isEmpty());

    }
    @Then("Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane")
    public void odgovor_treba_da_sadrzi_odgovarajucu_medicinsku_istoriju_sa_te_strane() {
        Page<MedicalHistoryDto> examinationHistoryDtos = findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(lbp,code,page,size);
        assertNotNull(examinationHistoryDtos);
        assertFalse(examinationHistoryDtos.isEmpty());
    }

    @When("Kada se prosledi lbp, pocetnim i krajnjim datumom i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_pocetnim_i_krajnjim_datumom_i_broj_strane_preko_koga_dohvatamo_istoriju() {
        Pageable pageable= PageRequest.of(page,size);
        patient =  patientRepository.findByLbp(lbp);
        assertNotNull(patient.get());

        medicalRecord = medicalRecordRepository.findByPatient(patient.get());
        assertNotNull(medicalRecord.get());

        Page<ExaminationHistory> examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(pageable,medicalRecord.get(),startDate,endDate);
        assertNotNull(examinationHistories);
        assertFalse(examinationHistories.isEmpty());
    }
    @Then("Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane i prosledjenim datumom")
    public void odgovor_treba_da_sadrzi_odgovarajucu_medicinsku_istoriju_sa_te_strane_i_prosledjenim_datumom() {
        Page<ExaminationHistoryDto> examinationHistoryDtos = findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp,startDate,endDate,page,size);
        assertNotNull(examinationHistoryDtos);
        assertFalse(examinationHistoryDtos.isEmpty());
    }
}
