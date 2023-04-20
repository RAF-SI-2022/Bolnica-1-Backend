package raf.bolnica1.laboratory.integration.prescriptionReceive.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionUpdateDtoGenerator;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.integration.prescriptionReceive.PrescriptionReceiveIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.List;

public class PrescriptionReceiveDeleteSteps extends PrescriptionReceiveIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionUpdateDtoGenerator prescriptionUpdateDtoGenerator;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;

    /// SERVICES
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private Prescription prescription;
    private long prescriptionId;
    private String lbz;


    @When("obrisan uput sa ID {int} i LBZ {string}")
    public void obrisan_uput_sa_id_i_lbz(Integer prescriptionId, String lbz) {
        try{
            this.prescriptionId=prescriptionId;
            this.lbz=lbz;
            prescription=prescriptionRepository.findPrescriptionById((long)prescriptionId);
            prescriptionRecieveService.deletePrescription((long)prescriptionId,lbz);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("on i njegov labWorkOrder se ne nalaze vise u bazi")
    public void on_i_njegov_lab_work_order_se_ne_nalaze_vise_u_bazi() {
        try{

            if(prescription==null)return;
            if(!prescription.getDoctorLbz().equals(lbz)){
                /// treba da ga i dalje ima
                Assertions.assertNotNull(prescriptionRepository.findPrescriptionById(prescriptionId));
            }
            else{
                /// treba da je obrisan
                Assertions.assertNull(prescriptionRepository.findPrescriptionById(prescriptionId));
                Assertions.assertTrue(!labWorkOrderRepository.findByPrescription(prescriptionId).isPresent());
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



}
