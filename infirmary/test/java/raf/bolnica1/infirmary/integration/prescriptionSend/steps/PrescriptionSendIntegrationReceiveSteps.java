package raf.bolnica1.infirmary.integration.prescriptionSend.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.PrescriptionReceiveDtoGenerator;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.integration.prescriptionSend.PrescriptionSendIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.PrescriptionSendService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrescriptionSendIntegrationReceiveSteps extends PrescriptionSendIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionReceiveDtoGenerator prescriptionReceiveDtoGenerator;

    /// SERVICES
    @Autowired
    private PrescriptionSendService prescriptionSendService;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    /// MAPPERS
    @Autowired
    private PrescriptionMapper prescriptionMapper;


    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;


    /// CLASS DATA
    private PrescriptionReceiveDto prescriptionReceiveDto;


    @When("primljen uput za stacionar")
    public void primljen_uput_za_stacionar() {

        try {
            prescriptionReceiveDto = prescriptionReceiveDtoGenerator.getPrescriptionReceiveDto();
            prescriptionSendService.receivePrescription(prescriptionReceiveDto);
        }
        catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("uput se nalazi u bazi uputa")
    public void uput_se_nalazi_u_bazi_uputa() {

        try{

            List<PrescriptionDto> prescriptions=prescriptionMapper.toDto(prescriptionRepository.findAll());

            boolean flag=false;
            for(PrescriptionDto prescriptionDto:prescriptions){
                if(classJsonComparator.compareCommonFields(prescriptionDto,prescriptionReceiveDto))
                    flag=true;
            }

            assertTrue(flag);
        }
        catch(Exception e){
            Assertions.fail(e);
        }

    }


}
