package raf.bolnica1.laboratory.integration.prescription.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.prescription.PrescriptionGenerator;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.integration.prescription.PrescriptionIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.PrescriptionService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

public class PrescriptionUpdateStatusSteps extends PrescriptionIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionGenerator prescriptionGenerator;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    /// SERVICES
    @Autowired
    private PrescriptionService prescriptionService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private Prescription prescription;
    private PrescriptionStatus newStatus;


    @Given("imamo uput u bazi")
    public void imamo_uput_u_bazi() {

        try {
            prescription=prescriptionGenerator.getPrescriptionWithDBSave(prescriptionRepository);
        }
        catch (Exception e){
            Assertions.fail(e);
        }

    }
    @When("promenimo status uputa")
    public void promenimo_status_uputa() {

        try {
            newStatus=PrescriptionStatus.REALIZOVAN;
            prescriptionService.updatePrescriptionStatus(prescription.getId(), newStatus);
        }
        catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("promena je azurirana u bazi")
    public void promena_je_azurirana_u_bazi() {
        try {
            Prescription newPrescription=prescriptionRepository.findPrescriptionById(prescription.getId());
            System.out.println(objectMapper.writeValueAsString(prescription));
            Assertions.assertEquals(newPrescription.getStatus(),newStatus);
        }
        catch (Exception e){
            Assertions.fail(e);
        }
    }

}
