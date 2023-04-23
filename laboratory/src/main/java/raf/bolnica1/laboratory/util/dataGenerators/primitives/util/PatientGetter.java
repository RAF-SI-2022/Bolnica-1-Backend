package raf.bolnica1.laboratory.util.dataGenerators.primitives.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.appConfig.client.RestServiceClientConfig;
import raf.bolnica1.laboratory.util.dataGenerators.jwtToken.JwtTokenGetter;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.util.patient.PatientDto;
import raf.bolnica1.laboratory.util.ExtractPageContentFromPageJson;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PatientGetter {

    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    @Qualifier("patientRestTemplate")
    private RestTemplate patientRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExtractPageContentFromPageJson extractPageContentFromPageJson;


    public static PatientGetter getInstance(){
        return new PatientGetter(JwtTokenGetter.getInstance(), RestServiceClientConfig.patientRestTemplate(),
                new ObjectMapper(),ExtractPageContentFromPageJson.getInstance());
    }


    public List<PatientDto> getAllPatients(){

        List<PatientDto> ret=new ArrayList<>();

        String token= jwtTokenGetter.getDrMedSpec();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        String uri=new String("/filter_patients?page=0&size=1000000000");
        ResponseEntity<String> patientList = patientRestTemplate.exchange(uri, HttpMethod.GET, httpEntity,String.class);
        if(patientList.getBody()==null) {
            System.out.println("nisu nadjeni pacijenti");
            return ret;
        }

        List<PatientDto>patients=null;
        try {
            patients=extractPageContentFromPageJson.extractPageContentFromPageJson(patientList.getBody(), PatientDto.class);
        } catch (JsonProcessingException e) {
            System.out.println("nije se mogao ocitati json u RandomLBP");
            return ret;
        }

        return patients;
    }

    public List<PatientDto> getPatientByLbp(String lbp){

        List<PatientDto> ret=new ArrayList<>();

        String token= jwtTokenGetter.getDrMedSpec();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        String uri=new String("/filter_patients?page=0&size=1000000000&lbp="+lbp);
        ResponseEntity<String> patientList = patientRestTemplate.exchange(uri, HttpMethod.GET, httpEntity,String.class);
        if(patientList.getBody()==null) {
            System.out.println("nisu nadjeni pacijenti");
            return ret;
        }

        List<PatientDto>patients=null;
        try {
            patients=extractPageContentFromPageJson.extractPageContentFromPageJson(patientList.getBody(), PatientDto.class);
        } catch (JsonProcessingException e) {
            System.out.println("nije se mogao ocitati json u RandomLBP");
            return ret;
        }

        return patients;
    }


}
