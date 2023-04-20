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

    @Autowired
    DiagnosisCodeRepository diagnosisCodeRepository;


    private Optional<Patient> patient;

    private Optional<MedicalRecord> medicalRecord;

    private GeneralMedicalData generalMedicalData;

    private Patient patientSend;

    private MedicalRecord medicalRecordSend;

    private List<Operation> operations;
    private List<MedicalHistory> medicalHistories;

    private List<ExaminationHistory> examinationHistories;

    private int size = 2;
    private int page = 0;

    private Date startDate  = Date.valueOf("2023-02-04");
    private Date endDate = Date.valueOf("2023-02-04");

    private String code;

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

    DiagnosisCode getCode(){

        List<DiagnosisCode> codes = diagnosisCodeRepository.findAll();
        DiagnosisCode code;

        code = codes.get(0);

        return code;
    }

    MedicalRecord getRecord(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        MedicalRecord m;
        m = medicalRecords.get(0);
        return m;
    }

    @When("Kada se prosledi lbp preko koga dohvatamo osnovne medicinske podatke")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_osnovne_medicinske_podatke() {

        patientSend = getPatient();
        medicalRecordSend = getRecord();

        try{
            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            generalMedicalData = medicalRecord.get().getGeneralMedicalData();


            List<Object[]> vaccinationsAndDates =  vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData);
            assertNotNull(vaccinationsAndDates);
            assertFalse(vaccinationsAndDates.isEmpty());

            List<Allergy> allergies=allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData);
            assertNotNull(allergies);
            assertFalse(allergies.isEmpty());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Odgovor treba da sadrzi odgovarajuci osnovni medicinski podaci")
    public void odgovor_treba_da_sadrzi_odgovarajuci_osnovni_medicinski_podaci() {
        try{
            GeneralMedicalDataDto generalMedicalDataDto = findInfoService.findGeneralMedicalDataByLbp(patient.get().getLbp());
            assertNotNull(generalMedicalDataDto);
            assertEquals(generalMedicalData.getId(),generalMedicalDataDto.getId());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }


    @When("Kada se prosledi lbp preko koga dohvatamo operaciju")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_operaciju() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            operations=operationRepository.findOperationsByMedicalRecord(medicalRecord.get());
            assertNotNull(operations);
            assertFalse(operations.isEmpty());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Then("Odgovor treba da sadrzi odgovarajuce operacije")
    public void odgovor_treba_da_sadrzi_odgovarajuce_operacije() {
        try{
            List<OperationDto> operationDtos = findInfoService.findOperationsByLbp(patient.get().getLbp());
            assertNotNull(operationDtos);
            assertFalse(operationDtos.isEmpty());

            for (int i = 0; i <operationDtos.size() ; i++) {
                assertEquals(operations.get(i).getId(),operationDtos.get(i).getId());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }

    }


    @When("Kada se prosledi lbp preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_istoriju() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecord(medicalRecord.get());
            assertNotNull(medicalHistories);
            assertFalse(medicalHistories.isEmpty());


        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju() {
        try{
            List<MedicalHistoryDto> medicalHistoryDtos = findInfoService.findMedicalHistoryByLbp(patient.get().getLbp());
            assertNotNull(medicalHistoryDtos);
            assertFalse(medicalHistoryDtos.isEmpty());

            for (int i = 0; i <medicalHistoryDtos.size() ; i++) {
                assertEquals(medicalHistories.get(i).getStartDate(),medicalHistoryDtos.get(i).getStartDate());
                assertEquals(medicalHistories.get(i).getEndDate(),medicalHistoryDtos.get(i).getEndDate());
                assertEquals(medicalHistories.get(i).getValidTo(),medicalHistoryDtos.get(i).getValidTo());
                assertEquals(medicalHistories.get(i).getValidFrom(),medicalHistoryDtos.get(i).getValidFrom());
                assertEquals(medicalHistories.get(i).getTreatmentResult(),medicalHistoryDtos.get(i).getTreatmentResult());
                assertEquals(medicalHistories.get(i).getCurrStateDesc(),medicalHistoryDtos.get(i).getCurrStateDesc());
                assertEquals(medicalHistories.get(i).isValid(),medicalHistoryDtos.get(i).isValid());
                assertEquals(medicalHistories.get(i).getDiagnosisCode().getCode(),medicalHistoryDtos.get(i).getDiagnosisCodeDto().getCode());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }

    }


    @When("Kada se prosledi lbp preko koga dohvatamo istoriju pregleda")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_istoriju_pregleda() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecord(medicalRecord.get());
            assertNotNull(examinationHistories);
            assertFalse(examinationHistories.isEmpty());

        }catch (Exception e){
            fail(e.getMessage());
        }


    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju pregleda")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju_pregleda() {
        try{
            List<ExaminationHistoryDto> examinationHistoryDtos = findInfoService.findExaminationHistoryByLbp(patient.get().getLbp());
            assertNotNull(examinationHistoryDtos);
            assertFalse(examinationHistoryDtos.isEmpty());

            for (int i = 0; i <examinationHistoryDtos.size() ; i++) {
                assertEquals(examinationHistories.get(i).getId(),examinationHistoryDtos.get(i).getId());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }

    }



    @When("Kada se prosledi lbp preko koga dohvatamo zdravstveni karton")
    public void kada_se_prosledi_lbp_preko_koga_dohvatamo_zdravstveni_karton() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Odgovor treba da sadrzi odgovarajuci zdravstveni karton")
    public void odgovor_treba_da_sadrzi_odgovarajuci_zdravstveni_karton() {
        try{

            MedicalRecordDto dto = findInfoService.findMedicalRecordByLbp(patient.get().getLbp());
            assertNotNull(dto);
            assertEquals(medicalRecord.get().getId(),dto.getId());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }


    @When("Kada se prosledi lbp i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_i_broj_strane_preko_koga_dohvatamo_istoriju() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            Pageable pageable= PageRequest.of(page,size);
            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordPaged(pageable,medicalRecord.get());
            assertNotNull(medicalHistories);
            assertFalse(medicalHistories.isEmpty());

            for (MedicalHistory m: medicalHistories){
                assertEquals(medicalRecord.get().getId(),m.getMedicalRecord().getId());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }



    }
    @Then("Odgovor treba da sadrzi odgovarajucu istoriju sa te strane")
    public void odgovor_treba_da_sadrzi_odgovarajucu_istoriju_sa_te_strane() {
        try{
            Page<MedicalHistoryDto> medicalHistoryDtos = findInfoService.findMedicalHistoryByLbpPaged(patient.get().getLbp(),page,size);
            assertNotNull(medicalHistoryDtos);
            assertFalse(medicalHistoryDtos.isEmpty());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }


    @When("Kada se prosledi lbp, kod dijagnoze i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_kod_dijagnoze_i_broj_strane_preko_koga_dohvatamo_istoriju() {

        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();
            code = getCode().getCode();

            Pageable pageable= PageRequest.of(page,size);
            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(pageable,medicalRecord.get(),code);
            assertNotNull(medicalHistories);
            assertFalse(medicalHistories.isEmpty());

            for (MedicalHistory m: medicalHistories){
                assertEquals(medicalRecord.get().getId(),m.getMedicalRecord().getId());
            }


        }catch (Exception e){
            fail(e.getMessage());
        }



    }
    @Then("Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane")
    public void odgovor_treba_da_sadrzi_odgovarajucu_medicinsku_istoriju_sa_te_strane() {
        try{
            Page<MedicalHistoryDto> examinationHistoryDtos = findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(patient.get().getLbp(),code,page,size);
            assertNotNull(examinationHistoryDtos);
            assertFalse(examinationHistoryDtos.isEmpty());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }

    @When("Kada se prosledi lbp, pocetnim i krajnjim datumom i broj strane preko koga dohvatamo istoriju")
    public void kada_se_prosledi_lbp_pocetnim_i_krajnjim_datumom_i_broj_strane_preko_koga_dohvatamo_istoriju() {
        try{
            patientSend = getPatient();
            medicalRecordSend = getRecord();

            Pageable pageable= PageRequest.of(page,size);
            patient =  patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient.get());
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord.get());
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            Page<ExaminationHistory> examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(pageable,medicalRecord.get(),startDate,endDate);
            assertNotNull(examinationHistories);
            assertFalse(examinationHistories.isEmpty());

            for (ExaminationHistory e: examinationHistories){
                assertEquals(medicalRecord.get().getId(),e.getMedicalRecord().getId());
            }

        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Odgovor treba da sadrzi odgovarajucu medicinsku istoriju sa te strane i prosledjenim datumom")
    public void odgovor_treba_da_sadrzi_odgovarajucu_medicinsku_istoriju_sa_te_strane_i_prosledjenim_datumom() {

        try{
            Page<ExaminationHistoryDto> examinationHistoryDtos = findInfoService.findExaminationHistoryByLbpAndDateRangePaged(patient.get().getLbp(),startDate,endDate,page,size);
            assertNotNull(examinationHistoryDtos);
            assertFalse(examinationHistoryDtos.isEmpty());

        }catch (Exception e){
            fail(e.getMessage());
        }

    }
}
