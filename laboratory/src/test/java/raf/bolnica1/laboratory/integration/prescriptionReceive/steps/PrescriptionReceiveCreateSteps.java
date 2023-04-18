package raf.bolnica1.laboratory.integration.prescriptionReceive.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.integration.prescriptionReceive.PrescriptionReceiveIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrescriptionReceiveCreateSteps extends PrescriptionReceiveIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    /// SERVICES
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;

    /// CLASS DATA
    private PrescriptionCreateDto prescriptionCreateDto;
    private Long prescriptionId;



    private List<Long> getAnalysisParameterIdsFrom(List<PrescriptionAnalysisDto> prescriptionAnalysisDtos){
        List<Long> analysisParameterIds=new ArrayList<>();
        HashSet<Long> mapa=new HashSet<>();
        for(PrescriptionAnalysisDto pa:prescriptionAnalysisDtos){
            for(Long id2:pa.getParametersIds()){
                analysisParameterIds.add(analysisParameterRepository.findAnalysisParameterByAnalysisIdAndParameterId(
                        pa.getAnalysisId(),id2).getId());
            }
        }

        ///System.out.println(analysisParameterIds+"  EVO IH");
        return analysisParameterIds;
    }


    @When("kreiramo novi uput")
    public void kreiramo_novi_uput() {
        try{
            prescriptionCreateDto=prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);
            prescriptionRecieveService.createPrescription(prescriptionCreateDto);
        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("on se nalazi sacuvan u bazi")
    public void on_se_nalazi_sacuvan_u_bazi() {
        try{

            List<Prescription> prescriptions= prescriptionRepository.findAll();

            boolean flag=false;
            for(Prescription prescription:prescriptions)
                if(classJsonComparator.compareCommonFields(prescription,prescriptionCreateDto)) {
                    flag = true;
                    prescriptionId=prescription.getId();
                }

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("sve njegove analize su korektno sacuvane u bazi")
    public void sve_njegove_analize_su_korektno_sacuvane_u_bazi() {
        try{

            List<Long> analysisParameters=getAnalysisParameterIdsFrom(prescriptionCreateDto.getPrescriptionAnalysisDtos());
            List<ParameterAnalysisResult> pars=parameterAnalysisResultRepository.findAll();

            for(int i=0;i<analysisParameters.size();i++){
                boolean flag=false;
                for(int j=0;j<pars.size();j++){

                    if(pars.get(j).getAnalysisParameter().getId().equals(analysisParameters.get(i)) &&
                    pars.get(j).getLabWorkOrder().getPrescription().getId().equals(prescriptionId)) {
                        flag = true;
                        /*System.out.println(objectMapper.writeValueAsString(pars.get(j)));
                        System.out.println(analysisParameters.get(i)+" "+prescriptionId+"  IDS");*/
                    }

                }
                Assertions.assertTrue(flag);
            }


        }catch (Exception e){
            Assertions.fail(e);
        }
    }





}
