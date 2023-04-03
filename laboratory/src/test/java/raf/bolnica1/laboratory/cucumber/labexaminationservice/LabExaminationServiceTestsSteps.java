package raf.bolnica1.laboratory.cucumber.labexaminationservice;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import raf.bolnica1.laboratory.repository.ScheduledLabExaminationRepository;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;

import static org.junit.jupiter.api.Assertions.fail;

public class LabExaminationServiceTestsSteps extends LabExaminationServiceTestsConfig{
/*
    @Autowired
    private LabExaminationsService labExaminationsService;

    @Autowired
    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;

    @Autowired
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;
/*
    @When("Kada se doda neki pregled")
    public void kada_se_doda_neki_pregled() {
        try {
            ScheduledLabExamination scheduledLabExamination=scheduledLabExaminationMapper.toEntity(1L,"lbp1",java.sql.Date.valueOf("2020-01-01"),"note","lbs1");
            scheduledLabExaminationRepository.save(scheduledLabExamination);
        }catch (Exception e){
            fail(e.getMessage());
        }

    }
    @Then("Se taj izmenjen pregled nadje promeni i sacuva")
    public void se_taj_izmenjen_pregled_nadje_promeni_i_sacuva() {
        try {
            labExaminationsService.changeExaminationStatus(1L, ExaminationStatus.OTKAZANO);
        }catch (Exception e){
            fail(e.getMessage());
        }

    }*/
@When("Kada se doda neki pregled")
public void kada_se_doda_neki_pregled() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}

@Then("Se taj izmenjen pregled nadje promeni i sacuva")
    public void se_taj_izmenjen_pregled_nadje_promeni_i_sacuva() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
