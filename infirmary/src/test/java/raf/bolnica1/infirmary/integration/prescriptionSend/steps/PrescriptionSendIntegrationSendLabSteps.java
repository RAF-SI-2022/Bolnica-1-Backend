package raf.bolnica1.infirmary.integration.prescriptionSend.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.PrescriptionReceiveDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.TokenSetter;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.integration.prescriptionSend.PrescriptionSendIntegrationTestConfig;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.PrescriptionSendService;
import raf.bolnica1.infirmary.util.ExtractPageContentFromPageJson;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionSendIntegrationSendLabSteps extends PrescriptionSendIntegrationTestConfig {
    /// GENERATORS
    @Autowired
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator;

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
    @Qualifier("labPrescriptionRestTemplate")
    private RestTemplate labPrescriptionRestTemplate;
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    private TokenSetter tokenSetter;
    @Autowired
    private ExtractPageContentFromPageJson extractPageContentFromPageJson;


    /// CLASS DATA
    private PrescriptionCreateDto prescriptionCreateDto;

    @When("poslat uput na laboratoriju")
    public void poslat_uput_na_laboratoriju() {
        try{

            String token=jwtTokenGetter.getDefaultToken();
            String lbz=tokenSetter.setToken(token);

            prescriptionCreateDto=prescriptionCreateDtoGenerator.getPrescriptionCreateDto();

            prescriptionSendService.sendPrescription(prescriptionCreateDto);

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("taj uput se nalazi na laboratoriji")
    public void taj_uput_se_nalazi_na_laboratoriji() {
        try{

            List<PatientDto> ret=new ArrayList<>();

            String token= jwtTokenGetter.getDrMedSpec();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(token);
            HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
            String uri=new String("/"+prescriptionCreateDto.getLbp());
            ResponseEntity<String> prescriptionList = labPrescriptionRestTemplate.exchange(uri, HttpMethod.GET, httpEntity,String.class);
            if(prescriptionList.getBody()==null) {
                Assertions.fail("nije nadjen uput na labu");
            }

            System.out.println("STRING PAGE");
            System.out.println(prescriptionList.getBody());

            List<PrescriptionDto>prescriptionDtos=null;
            try {
                prescriptionDtos=extractPageContentFromPageJson.extractPageContentFromPageJson(prescriptionList.getBody(), PrescriptionDto.class);
            } catch (JsonProcessingException e) {
                Assertions.fail("nije se mogao ocitati json u RandomLBP");
            }

            boolean flag=false;
            for(PrescriptionDto p:prescriptionDtos)
                if(classJsonComparator.compareCommonFields(p,prescriptionCreateDto))
                    flag=true;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            System.out.println("puklo slanje uputa na lab");
            Assertions.fail(e);
        }
    }

}
