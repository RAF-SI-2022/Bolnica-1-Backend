package raf.bolnica1.laboratory.integration.labResult.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.dataGenerators.classes.domain.labWorkOrder.LabWorkOrderFilterGenerator;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.result.ResultUpdateDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomLong;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.dataGenerators.primitives.util.patient.PatientDto;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.prescription.*;
import raf.bolnica1.laboratory.integration.labResult.LabResultIntegrationTestConfig;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.LabResultService;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;
import raf.bolnica1.laboratory.util.ExtractPageContentFromPageJson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LabResultSteps extends LabResultIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;
    @Autowired
    private LabWorkOrderFilterGenerator labWorkOrderFilterGenerator;
    @Autowired
    private RandomString randomString;
    @Autowired
    private ResultUpdateDtoGenerator resultUpdateDtoGenerator;

    /// REPOSITORIES
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private LabWorkOrderRepository labWorkOrderRepository;
    @Autowired
    private AnalysisParameterRepository analysisParameterRepository;
    @Autowired
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    /// SERVICES
    @Autowired
    private LabResultService labResultService;
    @Autowired
    private LabWorkOrdersService labWorkOrdersService;
    @Autowired
    private PrescriptionRecieveService prescriptionRecieveService;

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

    /// CLASS DATA
    private PrescriptionCreateDto prescriptionCreateDto;
    private Long labWorkOrderId;


    @Given("imamo otvoren uput zajedno sa LabWorkOrderom i analizama")
    public void imamo_otvoren_uput_zajedno_sa_lab_work_orderom_i_analizama() {
        try{

            prescriptionCreateDto=prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);
            prescriptionRecieveService.createPrescription(prescriptionCreateDto);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("azuriranje rezultata u za neki LabWorkOrder ce biti izvrseno")
    public void azuriranje_rezultata_u_za_neki_lab_work_order_ce_biti_izvrseno() {
        try{

            List<ParameterAnalysisResult> list=parameterAnalysisResultRepository.findAll();
            ParameterAnalysisResult parId=list.get(0);

            ResultUpdateDto resultUpdateDto=resultUpdateDtoGenerator.getResultUpdateDto(parId.getLabWorkOrder().getId(),
                    parId.getAnalysisParameter().getId());

            labResultService.updateResults(resultUpdateDto);

            ParameterAnalysisResult result=parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(
                    parId.getLabWorkOrder().getId(),parId.getAnalysisParameter().getId()).get();

            Assertions.assertTrue(classJsonComparator.compareCommonFields(result,resultUpdateDto));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }





    private List<PrescriptionAnalysisNameDto> sortParams(List<PrescriptionAnalysisNameDto> val){
        List<PrescriptionAnalysisNameDto> ret=new ArrayList<>(val);
        ret.sort(Comparator.comparing(PrescriptionAnalysisNameDto::getAnalysisName));
        for(PrescriptionAnalysisNameDto pand:ret){
            List<ParameterDto> pom=new ArrayList<>(pand.getParameters());
            pom.sort(Comparator.comparing(ParameterDto::getParameterName));
            pand.setParameters(pom);
        }
        return ret;
    }

    private PrescriptionDoneDto prescriptionDoneDto;
    @Given("otvoren uput i LabWorkOrder sa upisanim rezultatima")
    public void otvoren_uput_i_lab_work_order_sa_upisanim_rezultatima() {
        try{
            prescriptionCreateDto=prescriptionCreateDtoGenerator.getPrescriptionCreateDto(analysisParameterRepository);
            prescriptionRecieveService.createPrescription(prescriptionCreateDto);

            List<ParameterAnalysisResult> list=parameterAnalysisResultRepository.findAll();

            for(ParameterAnalysisResult parId:list){
                if(classJsonComparator.compareCommonFields(parId.getLabWorkOrder().getPrescription(),prescriptionCreateDto)){
                    labWorkOrderId=parId.getLabWorkOrder().getId();
                    prescriptionCreateDto.setPid(parId.getLabWorkOrder().getPrescription().getId());
                }
                ResultUpdateDto resultUpdateDto=resultUpdateDtoGenerator.getResultUpdateDto(parId.getLabWorkOrder().getId(),
                        parId.getAnalysisParameter().getId());
                labResultService.updateResults(resultUpdateDto);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @When("posaljemo rezultat u pacijent servis")
    public void posaljemo_rezultat_u_pacijent_servis() {
        try{
            prescriptionDoneDto=prescriptionRecieveService.findPrescription(prescriptionCreateDto.getPid());
            prescriptionDoneDto.setId(null);
            prescriptionDoneDto.setPrescriptionStatus(PrescriptionStatus.REALIZOVAN);
            prescriptionDoneDto.setDate(null);
            prescriptionDoneDto.setParameters(sortParams(prescriptionDoneDto.getParameters()));
            labResultService.commitResults(labWorkOrderId);
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("rezultati se nalaze na pacijent servisu")
    public void rezultati_se_nalaze_na_pacijent_servisu() {
        try{

            Thread.sleep(3000);

            String token= jwtTokenGetter.getDrMedSpec();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(token);
            HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
            String uri=new String("/done_prescriptions/"+prescriptionCreateDto.getLbp()+"?page=0&size=1000000000");
            ResponseEntity<String> prescriptionList = prescriptionRestTemplate.exchange(uri, HttpMethod.GET, httpEntity,String.class);
            if(prescriptionList.getBody()==null) {
                Assertions.fail("nisu nadjeni uputi");
            }

            List<PrescriptionDoneDto>prescriptionDoneDtos=null;
            try {
                prescriptionDoneDtos=extractPageContentFromPageJson.extractPageContentFromPageJson(
                        prescriptionList.getBody(), PrescriptionDoneDto.class);
            } catch (JsonProcessingException e) {
                Assertions.fail("nije se mogao ocitati json u PrescriptionDoneDto");
            }


            boolean flag=false;
            for(PrescriptionDoneDto p:prescriptionDoneDtos) {

                p.setDate(null);
                p.setParameters(sortParams(p.getParameters()));

                if (classJsonComparator.compareAllFields(p, prescriptionDoneDto))
                    flag = true;
            }

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
