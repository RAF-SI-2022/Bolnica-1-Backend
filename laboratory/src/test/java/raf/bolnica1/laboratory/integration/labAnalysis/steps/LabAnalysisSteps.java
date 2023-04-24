package raf.bolnica1.laboratory.integration.labAnalysis.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.services.*;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder.LabWorkOrderFilterGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.labAnalysis.LabAnalysisDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.result.ResultUpdateDtoGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.ScheduledLabExaminationCreateGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomDate;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomLong;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.integration.labExamination.LabExaminationIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.*;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.util.ExtractPageContentFromPageJson;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LabAnalysisSteps extends LabExaminationIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private RandomDate randomDate;
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    @Autowired
    private LabWorkOrderFilterGenerator labWorkOrderFilterGenerator;
    @Autowired
    private RandomString randomString;
    @Autowired
    private ResultUpdateDtoGenerator resultUpdateDtoGenerator;
    @Autowired
    private ScheduledLabExaminationCreateGenerator scheduledLabExaminationCreateGenerator;
    @Autowired
    private LabAnalysisDtoGenerator labAnalysisDtoGenerator;

    /// REPOSITORIES
    @Autowired
    private LabAnalysisRepository labAnalysisRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Autowired
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;

    /// SERVICES
    @Autowired
    private LabResultService labResultService;
    @Autowired
    private LabWorkOrdersService labWorkOrdersService;
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;
    @Autowired
    private LabExaminationsService labExaminationsService;
    @Autowired
    private LabAnalysisService labAnalysisService;

    /// UTILS
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("prescriptionRestTemplate")
    private RestTemplate prescriptionRestTemplate;
    @Autowired
    private ExtractPageContentFromPageJson extractPageContentFromPageJson;
    @Autowired
    private AuthenticationUtils authenticationUtils;
    @Autowired
    @Qualifier("employeeRestTemplate")
    private RestTemplate employeeRestTemplate;

    /// CLASS DATA
    private LabAnalysisDto labAnalysisDto;


    @When("kreirana laboratorijska analiza")
    public void kreirana_laboratorijska_analiza() {
        try {
            labAnalysisDto=labAnalysisDtoGenerator.getLabAnalysisDto();
            LabAnalysisDto pom=labAnalysisDto;
            labAnalysisDto=labAnalysisService.createLabAnalysis(labAnalysisDto);
            pom.setId(labAnalysisDto.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,labAnalysisDto));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("ona se nalazi u bazi")
    public void ona_se_nalazi_u_bazi() {
        try {
            LabAnalysisDto pom=labAnalysisService.getLabAnalysis(labAnalysisDto.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom,labAnalysisDto));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("analiza je azurirana i u bazi")
    public void analiza_je_azurirana_i_u_bazi() {
        try {
            LabAnalysisDto pom=labAnalysisDtoGenerator.getLabAnalysisDto();
            pom.setId(labAnalysisDto.getId());

            labAnalysisService.updateLabAnalysis(pom);

            LabAnalysisDto pom2=labAnalysisService.getLabAnalysis(labAnalysisDto.getId());
            Assertions.assertTrue(classJsonComparator.compareCommonFields(pom2,pom));
            labAnalysisDto=pom2;
        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @When("obrisana laboratorijska analiza")
    public void obrisana_laboratorijska_analiza() {
        try {
            labAnalysisService.deleteLabAnalysis(labAnalysisDto.getId());
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("ta analiza se ne nalazi u bazi")
    public void ta_analiza_se_ne_nalazi_u_bazi() {
        try {

            List<LabAnalysis> pom=labAnalysisRepository.findAll();

            boolean flag=true;
            for(LabAnalysis la:pom)
                if(classJsonComparator.compareCommonFields(la,labAnalysisDto))
                    flag=false;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("dobavljanje analize to njenom ID daje tu analizu")
    public void dobavljanje_analize_to_njenom_id_daje_tu_analizu() {
        try {
            Assertions.assertTrue(classJsonComparator.compareCommonFields(
                    labAnalysisService.getLabAnalysis(labAnalysisDto.getId()),
                    labAnalysisDto
            ));
        }catch (Exception e){
            Assertions.fail(e);
        }
    }


    @Then("dobavljanje svih laboratorijskih analiza daje tu analizu i ostale vec postojece analize")
    public void dobavljanje_svih_laboratorijskih_analiza_daje_tu_analizu_i_ostale_vec_postojece_analize() {
        try {
            List<LabAnalysis> pom=labAnalysisRepository.findAll();
            List<LabAnalysisDto> pom2=labAnalysisService.getAllLabAnalysis();

            List<LabAnalysis>q1=new ArrayList<>(pom);
            List<LabAnalysisDto>q2=new ArrayList<>(pom2);
            q1.sort(Comparator.comparing(LabAnalysis::getId));
            q2.sort(Comparator.comparing(LabAnalysisDto::getId));

            Assertions.assertTrue(q1.size()==q2.size());
            for(int i=0;i<q1.size();i++)
                Assertions.assertTrue(classJsonComparator.compareCommonFields(q1.get(i),q2.get(i)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
