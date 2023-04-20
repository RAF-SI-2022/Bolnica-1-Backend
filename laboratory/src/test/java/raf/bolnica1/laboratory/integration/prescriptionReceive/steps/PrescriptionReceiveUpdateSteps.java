package raf.bolnica1.laboratory.integration.prescriptionReceive.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionUpdateDtoGenerator;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionUpdateDto;
import raf.bolnica1.laboratory.integration.prescriptionReceive.PrescriptionReceiveIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrescriptionReceiveUpdateSteps extends PrescriptionReceiveIntegrationTestConfig {

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
    private PrescriptionUpdateDto prescriptionUpdateDto;
    private List<Long> oldAnalysisParameters;
    private List<Long> newAnalysisParameters;
    private PrescriptionStatus status;
    private OrderStatus workOrderStatus;



    private List<Long> getAnalysisParameterIdsFrom(List<PrescriptionAnalysisDto> prescriptionAnalysisDtos){
        List<Long> analysisParameterIds=new ArrayList<>();
        for(PrescriptionAnalysisDto pa:prescriptionAnalysisDtos){
            for(Long id2:pa.getParametersIds()){
                analysisParameterIds.add(analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(
                        pa.getAnalysisId(),id2).getId());
            }
        }

        ///System.out.println(analysisParameterIds+"  EVO IH");
        return analysisParameterIds;
    }

    @When("napravljena nova verzija uputa")
    public void napravljena_nova_verzija_uputa() {
        try {
            prescriptionUpdateDto=prescriptionUpdateDtoGenerator.getPrescriptionUpdateDto(prescriptionRepository,
                    analysisParameterRepository);

            List<ParameterAnalysisResult> par=parameterAnalysisResultRepository.findAll();
            oldAnalysisParameters=new ArrayList<>();
            for(ParameterAnalysisResult p:par)
                if(p.getLabWorkOrder().getPrescription().getId().equals(prescriptionUpdateDto.getId()))
                    oldAnalysisParameters.add(p.getAnalysisParameter().getId());

            newAnalysisParameters=getAnalysisParameterIdsFrom(prescriptionUpdateDto.getPrescriptionAnalysisDtos());

            status=prescriptionRepository.findPrescriptionById(prescriptionUpdateDto.getId()).getStatus();
            workOrderStatus=labWorkOrderRepository.findByPrescription(prescriptionUpdateDto.getId()).get().getStatus();

            prescriptionRecieveService.updatePrescription(prescriptionUpdateDto);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("uput se nalazi u bazi")
    public void uput_se_nalazi_u_bazi() {
        try {

            System.out.println(status+"  STATUS");
            if(status==PrescriptionStatus.REALIZOVAN)return;

            List<Prescription> prescriptions= prescriptionRepository.findAll();

            boolean flag=false;
            for(Prescription prescription:prescriptions) {
                ///System.out.println("petlja: "+ prescription.getId());
                if (classJsonComparator.compareCommonFields(prescription, prescriptionUpdateDto)) {
                    flag = true;
                }
            }

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("svi prethodne analize za taj uput obrisane i dodate nove")
    public void svi_prethodne_analize_za_taj_uput_obrisane_i_dodate_nove() {
        try {

            if(workOrderStatus==OrderStatus.OBRADJEN || workOrderStatus==OrderStatus.U_OBRADI ||
            status==PrescriptionStatus.REALIZOVAN)return;

            HashSet<Long> oldMap=new HashSet<>(oldAnalysisParameters);
            HashSet<Long> newMap=new HashSet<>(newAnalysisParameters);

            List<ParameterAnalysisResult> par=parameterAnalysisResultRepository.findAll();
            int correctCount=0;
            for(ParameterAnalysisResult p:par){
                ///System.out.println(objectMapper.writeValueAsString(p));
                if(p.getLabWorkOrder().getPrescription().getId().equals(prescriptionUpdateDto.getId())){

                    if(newMap.contains(p.getAnalysisParameter().getId())){
                        correctCount++;
                        continue;
                    }

                    if(oldMap.contains(p.getAnalysisParameter().getId())){
                        Assertions.fail("nije dobro obrisao prescriptione nakon update");
                    }

                }
            }

            Assertions.assertEquals(correctCount,newMap.size());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
