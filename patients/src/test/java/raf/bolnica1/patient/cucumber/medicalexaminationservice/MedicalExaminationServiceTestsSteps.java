package raf.bolnica1.patient.cucumber.medicalexaminationservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.*;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.AnamnesisDto;
import raf.bolnica1.patient.dto.general.DiagnosisCodeDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalExaminationService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalExaminationServiceTestsSteps extends MedicalExaminationServiceTestsConfig{
    @Autowired
    MedicalExaminationService medicalExaminationService;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    AnamnesisRepository anamnesisRepository;
    @Autowired
    DiagnosisCodeRepository diagnosisCodeRepository;

    @Autowired
    ExaminationHistoryRepository examinationHistoryRepository;

    @Autowired
    MedicalHistoryRepository medicalHistoryRepository;

    private Optional<Patient> patient;
    private Optional<MedicalRecord> medicalRecord;
    private Patient patientSend;

    private MedicalRecord medicalRecordSend;
    private Anamnesis anamnesis;
    private DiagnosisCode  diagnosisCode;

    private ExaminationHistoryCreateDto examinationHistoryCreateDto;



    private Optional<MedicalHistory> medicalHistory;

    private MedicalHistoryCreateDto medicalHistoryCreateDto;

    private  ExaminationHistoryDto examinationHistoryDto;

    private Anamnesis an;

    private DiagnosisCode code;;

    Patient getPatient(String lbp){

        List<Patient> patients = patientRepository.findAll();
        Patient p = new Patient();
        for (Patient pp: patients){
            if(pp.getLbp().equals(lbp)){
                p = pp;
                break;
            }
        }
        return p;
    }

    MedicalRecord getRecord(){
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        MedicalRecord m;
        m = medicalRecords.get(0);
        return m;
    }

    ExaminationHistoryCreateDto setExaminationHistory(AnamnesisDto anamnesis1,DiagnosisCodeDto d1){
        ExaminationHistoryCreateDto examinationHistoryCreateDto = new ExaminationHistoryCreateDto();
        examinationHistoryCreateDto.setExamDate(Date.valueOf("2023-02-05"));
        examinationHistoryCreateDto.setLbz("lbz321");
        examinationHistoryCreateDto.setConfidential(true);
        examinationHistoryCreateDto.setObjectiveFinding("Objective finding 321");
        examinationHistoryCreateDto.setAdvice("Advice 321");
        examinationHistoryCreateDto.setTherapy("Therapy 321");
        examinationHistoryCreateDto.setAnamnesisDto(anamnesis1);
        examinationHistoryCreateDto.setDiagnosisCodeDto(d1);
        return examinationHistoryCreateDto;
    }

    MedicalHistoryCreateDto setMedicalHistoryCreateDto(DiagnosisCodeDto d1){
        MedicalHistoryCreateDto medicalHistoryCreateDto = new MedicalHistoryCreateDto();
        medicalHistoryCreateDto.setConfidential(false);
        medicalHistoryCreateDto.setTreatmentResult(TreatmentResult.OPORAVLJEN);
        medicalHistoryCreateDto.setCurrStateDesc("Current state description 321");
        medicalHistoryCreateDto.setExists(true);
        medicalHistoryCreateDto.setDiagnosisCodeDto(d1);
        return medicalHistoryCreateDto;
    }

    @When("Kada se prosledi {string} kao lbp napravi se novi examination history")
    public void kada_se_prosledi_kao_lbp_napravi_se_novi_examination_history(String lbp){


        List<DiagnosisCode> diagnosisCodes = diagnosisCodeRepository.findAll();

        code = diagnosisCodes.get(0);

        DiagnosisCodeDto diagnosisCodeDto = new DiagnosisCodeDto();
        diagnosisCodeDto.setCode(code.getCode());
        diagnosisCodeDto.setLatinDescription(code.getLatinDescription());
        diagnosisCodeDto.setDescription(code.getDescription());

        patientSend = getPatient(lbp);
        medicalRecordSend = getRecord();

        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();

        an = anamnesisList.get(0);

        AnamnesisDto anamnesisDto = new AnamnesisDto();
        anamnesisDto.setCurrDisease(an.getCurrDisease());
        anamnesisDto.setFamilyAnamnesis(an.getFamilyAnamnesis());
        anamnesisDto.setPersonalAnamnesis(an.getPersonalAnamnesis());
        anamnesisDto.setMainProblems(an.getMainProblems());
        anamnesisDto.setPatientOpinion(an.getPatientOpinion());

        examinationHistoryCreateDto = setExaminationHistory(anamnesisDto,diagnosisCodeDto);

        try{
            patient = patientRepository.findByLbp(patientSend.getLbp());
            assertNotNull(patient);
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord);
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());


            anamnesis = anamnesisRepository.findById(an.getId()).get();
            assertNotNull(anamnesis);
            assertEquals(an.getId(),anamnesis.getId());

            diagnosisCode = diagnosisCodeRepository.findByCode(diagnosisCodeDto.getCode());
            assertNotNull(diagnosisCode);
            assertEquals(diagnosisCodeDto.getCode(),diagnosisCode.getCode());


            examinationHistoryDto = medicalExaminationService.addExamination(patient.get().getLbp(), examinationHistoryCreateDto);
            assertNotNull(examinationHistoryDto);


        }catch (Exception e){
            fail(e.getMessage());
        }

    }

    @Then("Taj examination je sacuvan u bazi")
    public void taj_examination_je_sacuvan_u_bazi(){
        try{

           List<Anamnesis> anamnesises = anamnesisRepository.findByMainProblemsEquals(an.getMainProblems());
            assertNotNull(anamnesises);
            assertFalse(anamnesises.isEmpty());

            for (Anamnesis a: anamnesises){
                assertEquals(an.getMainProblems(),a.getMainProblems());
            }
            ExaminationHistory examinationHistory =  examinationHistoryRepository.findByStartDateAndLbzAndConfidentialAndObjectiveFindingAndAdviceAndTherapy(examinationHistoryCreateDto.getExamDate(), examinationHistoryCreateDto.getLbz(), examinationHistoryCreateDto.isConfidential(), examinationHistoryCreateDto.getObjectiveFinding(), examinationHistoryCreateDto.getAdvice(), examinationHistoryCreateDto.getTherapy());

            assertNotNull(examinationHistory);
            assertEquals(examinationHistoryCreateDto.getLbz(),examinationHistory.getLbz());
            assertEquals(examinationHistoryCreateDto.getExamDate(),examinationHistory.getExamDate());
            assertEquals(examinationHistoryCreateDto.getObjectiveFinding(),examinationHistory.getObjectiveFinding());
            assertEquals(examinationHistoryCreateDto.getAdvice(),examinationHistory.getAdvice());
            assertEquals(examinationHistoryCreateDto.getTherapy(),examinationHistory.getTherapy());
            assertEquals(examinationHistoryCreateDto.isConfidential(),examinationHistory.isConfidential());
            assertEquals(an.getMainProblems(),examinationHistory.getAnamnesis().getMainProblems());
            assertEquals(an.getFamilyAnamnesis(),examinationHistory.getAnamnesis().getFamilyAnamnesis());
            assertEquals(an.getPersonalAnamnesis(),examinationHistory.getAnamnesis().getPersonalAnamnesis());
            assertEquals(an.getPatientOpinion(),examinationHistory.getAnamnesis().getPatientOpinion());
            assertEquals(an.getCurrDisease(),examinationHistory.getAnamnesis().getCurrDisease());
            assertEquals(code.getId(),examinationHistory.getDiagnosisCode().getId());


        }catch (Exception e){
            fail(e.getMessage());
        }
    }



    @When("Kada se prosledi {string} kao lbp napravi se novi medical history")
    public void kada_se_prosledi_kao_lbp_napravi_se_novi_medical_history(String lbp) {
        try{
            patientSend = getPatient(lbp);
            medicalRecordSend = getRecord();

            List<DiagnosisCode> diagnosisCodes = diagnosisCodeRepository.findAll();

            code = diagnosisCodes.get(0);

            DiagnosisCodeDto diagnosisCodeDto = new DiagnosisCodeDto();
            diagnosisCodeDto.setCode(code.getCode());
            diagnosisCodeDto.setLatinDescription(code.getLatinDescription());
            diagnosisCodeDto.setDescription(code.getDescription());


            medicalHistoryCreateDto = setMedicalHistoryCreateDto(diagnosisCodeDto);

            patient = patientRepository.findByLbp(lbp);
            assertNotNull(patient);
            assertEquals(patientSend.getId(),patient.get().getId());

            medicalRecord = medicalRecordRepository.findByPatient(patient.get());
            assertNotNull(medicalRecord);
            assertEquals(medicalRecordSend.getId(),medicalRecord.get().getId());

            diagnosisCode = diagnosisCodeRepository.findById(code.getId()).get();
            assertNotNull(diagnosisCode);
            assertEquals(code.getId(),diagnosisCode.getId());

            medicalHistory = medicalHistoryRepository.findPrev(medicalHistoryCreateDto.getDiagnosisCodeDto().getCode(),medicalRecord.get().getId());
            assertNotNull(medicalHistory);
            assertEquals(medicalHistoryCreateDto.getDiagnosisCodeDto().getCode(),medicalHistory.get().getDiagnosisCode().getCode());


            MedicalHistoryDto medicalHistoryDto = medicalExaminationService.addMedicalHistory(lbp, medicalHistoryCreateDto);
            assertNotNull(medicalHistoryDto);

        }catch (Exception e){
            fail(e.getMessage());
        }


    }
    @Then("Taj medical je sacuvan u bazi")
    public void taj_medical_je_sacuvan_u_bazi() {
        try{
            MedicalHistory dto =  medicalHistoryRepository.findByStartDateEqualsAndTreatmentResultEqualsAndCurrStateDescEqualsAndDiagnosisCodeEqualsAndConfidentialEquals
                    (medicalHistory.get().getStartDate(),medicalHistoryCreateDto.getTreatmentResult(), medicalHistoryCreateDto.getCurrStateDesc(),medicalHistory.get().getDiagnosisCode(),medicalHistory.get().isConfidential());

            assertNotNull(dto);
            assertEquals(code.getId(),dto.getDiagnosisCode().getId());
            assertEquals(medicalHistoryCreateDto.getTreatmentResult(),dto.getTreatmentResult());
            assertEquals(medicalHistoryCreateDto.getCurrStateDesc(),dto.getCurrStateDesc());
            assertEquals(medicalHistory.get().getStartDate(),dto.getStartDate());
            assertEquals(medicalHistory.get().isConfidential(),dto.isConfidential());
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

}
