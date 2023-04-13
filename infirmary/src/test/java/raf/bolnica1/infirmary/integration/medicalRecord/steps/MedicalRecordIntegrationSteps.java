package raf.bolnica1.infirmary.integration.medicalRecord.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.examinationHistory.ExaminationHistoryCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.ExaminationHistoryDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.OperationDto;
import raf.bolnica1.infirmary.integration.medicalRecord.MedicalRecordIntegrationTestConfig;
import org.junit.jupiter.api.Assertions;
import raf.bolnica1.infirmary.services.MedicalRecordService;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

public class MedicalRecordIntegrationSteps extends MedicalRecordIntegrationTestConfig {

    /// GENERATORS
    @Autowired
    private ExaminationHistoryCreateDtoGenerator examinationHistoryCreateDtoGenerator;

    /// SERVICES
    @Autowired
    private MedicalRecordService medicalRecordService;

    /// UTILS
    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    @Qualifier("infoRestTemplate")
    private RestTemplate infoRestTemplate;
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private ObjectMapper objectMapper;


    private MedicalRecordDto medicalRecordDto;
    private String patientLbp;
    private ExaminationHistoryCreateDto examinationHistoryCreateDto;



    @When("napravljen i upisan pregled za pacijenta sa LBP {string}")
    public void napravljen_i_upisan_pregled_za_pacijenta_sa_lbp(String lbp) {

        try {
            examinationHistoryCreateDto=examinationHistoryCreateDtoGenerator.getExaminationHistoryCreateDto(lbp);
            medicalRecordService.createExaminationHistory(examinationHistoryCreateDto);
        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @When("dobavljen karton pacijenta sa LBP {string}")
    public void dobavljen_karton_pacijenta_sa_lbp(String lbp) {

        try{
            String token= jwtTokenGetter.getDrMedSpec();
            medicalRecordDto=medicalRecordService.getMedicalRecordByLbp(lbp, "Bearer " + token);
            patientLbp=lbp;
        }catch (Exception e){
            Assertions.fail(e);
        }

    }
    @Then("upisani pregled se nalazi u kartonu")
    public void upisani_pregled_se_nalazi_u_kartonu() {

        try{

            boolean flag=false;
            for(ExaminationHistoryDto examinationHistoryDto: medicalRecordDto.getExaminationHistoryDtos())
                if(classJsonComparator.compareCommonFields(examinationHistoryDto,examinationHistoryCreateDto))
                    flag=true;

            Assertions.assertTrue(flag);

        }catch (Exception e){
            Assertions.fail(e);
        }

    }

    @Then("dobavljen karton je tacan koji smo trazili")
    public void dobavljen_karton_je_tacan_koji_smo_trazili() {

        try{
            if(medicalRecordDto==null)return;

            String token= jwtTokenGetter.getDrMedSpec();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(token);
            HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
            String uri=new String("/myFindMedicalRecord/"+patientLbp);
            ResponseEntity<MedicalRecordDto> medicalRecord = infoRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, MedicalRecordDto.class);
            if(medicalRecord.getBody()==null)
                Assertions.fail("nije nadjen karton iako postoji");

            Assertions.assertTrue(classJsonComparator.compareAllFields(medicalRecord.getBody(),medicalRecordDto));

        }catch (Exception e){
            Assertions.fail(e);
        }

    }

}
