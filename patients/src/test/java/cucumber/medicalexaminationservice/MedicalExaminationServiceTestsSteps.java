package cucumber.medicalexaminationservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.general.AnamnesisDto;
import raf.bolnica1.patient.dto.general.DiagnosisCodeDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.services.MedicalExaminationService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalExaminationServiceTestsSteps extends MedicalExaminationServiceTestsConfig{
    @Autowired
    MedicalExaminationService medicalExaminationService;
    @When("Kada se prosledi {string} kao lbp napravi se novi examination history")
    public void kada_se_prosledi_kao_lbp_napravi_se_novi_examination_history(String lbp){
        DiagnosisCodeDto diagnosisCodeDto = new DiagnosisCodeDto();
        diagnosisCodeDto.setCode("Code");
        diagnosisCodeDto.setDescription("Description");
        diagnosisCodeDto.setLatinDescription("Latin description");

        AnamnesisDto anamnesisDto = new AnamnesisDto();
        anamnesisDto.setFamilyAnamnesis("Family anamnesis");
        anamnesisDto.setPersonalAnamnesis("Personal anamnesis");
        anamnesisDto.setCurrDisease("Current disease");
        anamnesisDto.setMainProblems("Main problems");
        anamnesisDto.setPatientOpinion("Patient opinion");

        ExaminationHistoryCreateDto examinationHistoryCreateDto = new ExaminationHistoryCreateDto();
        examinationHistoryCreateDto.setExamDate(Date.valueOf("2023-11-11"));
        examinationHistoryCreateDto.setLbz("E0000");
        examinationHistoryCreateDto.setConfidential(false);
        examinationHistoryCreateDto.setObjectiveFinding("Objective finding");
        examinationHistoryCreateDto.setAdvice("Advice");
        examinationHistoryCreateDto.setTherapy("Therapy");
        examinationHistoryCreateDto.setAnamnesisDto(anamnesisDto);
        examinationHistoryCreateDto.setDiagnosisCodeDto(diagnosisCodeDto);

        try{
            ExaminationHistoryDto examinationHistoryDto = medicalExaminationService.addExamination(lbp, examinationHistoryCreateDto);
            assertNotNull(examinationHistoryDto);
        }catch (Exception e){
            fail(e.getMessage());
        }

    }

    @Then("Taj examination je sacuvan u bazi")
    public void taj_examination_je_sacuvan_u_bazi(){
        //Tek treba da dodje provera za medical record
    }

}
