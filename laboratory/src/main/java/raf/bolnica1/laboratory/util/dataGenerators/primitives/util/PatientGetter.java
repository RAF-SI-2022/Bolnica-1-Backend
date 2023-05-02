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
public class PatientGetter {

    @Autowired
    private JwtTokenGetter jwtTokenGetter;
    @Autowired
    @Qualifier("patientRestTemplate")
    private RestTemplate patientRestTemplate;
    @Autowired
    private ExtractPageContentFromPageJson extractPageContentFromPageJson;
    private boolean isUnitTests;


    @Autowired
    public PatientGetter(JwtTokenGetter jwtTokenGetter,RestTemplate patientRestTemplate,
                         ExtractPageContentFromPageJson extractPageContentFromPageJson){
        this.jwtTokenGetter=jwtTokenGetter;
        this.patientRestTemplate=patientRestTemplate;
        this.extractPageContentFromPageJson=extractPageContentFromPageJson;
    }



    public void setUnitTests(boolean val){isUnitTests=val;}
    public static PatientGetter getInstance(){
        PatientGetter ret= new PatientGetter(JwtTokenGetter.getInstance(), RestServiceClientConfig.patientRestTemplate()
                ,ExtractPageContentFromPageJson.getInstance());
        ret.setUnitTests(true);
        return ret;
    }


    private List<String> getNames(){
        List<String>list=new ArrayList<>();
        list.add("James");
        list.add("Igor");
        list.add("Blake");
        list.add("Uros");
        list.add("Stefan");
        list.add("Marko");
        list.add("Ivan");
        list.add("Kosta");
        list.add("Ivana");
        list.add("Ana");
        list.add("Snezana");
        list.add("Katica");
        return list;
    }
    private List<String> getSurnames(){
        List<String> list=new ArrayList<>();
        list.add("Smith");
        list.add("Johnson");
        list.add("West");
        list.add("Lucic");
        list.add("Jovanovic");
        list.add("Kerestes");
        list.add("Kostic");
        list.add("Grahovac");
        list.add("Siflis");
        list.add("Najdic");
        list.add("Markovic");
        return list;
    }
    private List<String>getJMBG(){
        List<String>list=new ArrayList<>();
        list.add("0705003312430");
        list.add("0211974369304");
        list.add("0708982315363");
        list.add("0501000394873");
        list.add("0703991385976");
        list.add("1701973385013");
        list.add("2509973307476");
        list.add("1904982388910");
        list.add("1904995381770");
        list.add("2210989351313");
        list.add("2210992325650");
        list.add("2504005303457");
        return list;
    }
    private List<String>getLBP(){
        List<String>list=new ArrayList<>();
        list.add("LBP001");
        list.add("LBP002");
        list.add("LBP003");
        list.add("LBP004");
        list.add("LBP005");
        list.add("LBP006");
        list.add("LBP007");
        list.add("LBP008");
        list.add("LBP009");
        list.add("LBP010");
        return list;
    }
    public List<PatientDto> getAllPatients(){

        if(!isUnitTests) {
            List<PatientDto> ret = new ArrayList<>();

            String token = jwtTokenGetter.getDrMedSpec();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(token);
            HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
            String uri = new String("/filter_patients?page=0&size=1000000000");
            ResponseEntity<String> patientList = patientRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
            if (patientList.getBody() == null) {
                System.out.println("nisu nadjeni pacijenti");
                return ret;
            }

            List<PatientDto> patients = null;
            try {
                patients = extractPageContentFromPageJson.extractPageContentFromPageJson(patientList.getBody(), PatientDto.class);
            } catch (JsonProcessingException e) {
                System.out.println("nije se mogao ocitati json u RandomLBP");
                return ret;
            }


            try {
                ObjectMapper objectMapper=new ObjectMapper();
                System.out.println(objectMapper.writeValueAsString(patients));
                System.out.println(" ISPISAO PATIENTE");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return patients;
        }
        else{

            List<PatientDto> ret=new ArrayList<>();

            List<String>lbp=getLBP();
            List<String>jmbg=getJMBG();
            List<String>names=getNames();
            List<String>surnames=getSurnames();

            int minn=Math.min(lbp.size(),Math.min(jmbg.size(),Math.min(names.size(),surnames.size())));

            for(int i=0;i<minn;i++){
                PatientDto pom=new PatientDto();
                pom.setJmbg(jmbg.get(i));
                pom.setLbp(lbp.get(i));
                pom.setName(names.get(i));
                pom.setSurname(surnames.get(i));
                ret.add(pom);
            }

            return ret;
        }
    }

    public List<PatientDto> getPatientByLbp(String lbp){

        if(!isUnitTests) {
            List<PatientDto> ret = new ArrayList<>();

            String token = jwtTokenGetter.getDrMedSpec();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(token);
            HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
            String uri = new String("/filter_patients?page=0&size=1000000000&lbp=" + lbp);
            ResponseEntity<String> patientList = patientRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
            if (patientList.getBody() == null) {
                System.out.println("nisu nadjeni pacijenti");
                return ret;
            }

            List<PatientDto> patients = null;
            try {
                patients = extractPageContentFromPageJson.extractPageContentFromPageJson(patientList.getBody(), PatientDto.class);
            } catch (JsonProcessingException e) {
                System.out.println("nije se mogao ocitati json u RandomLBP");
                return ret;
            }

            return patients;
        }
        else{
            List<PatientDto> ret=new ArrayList<>();

            List<String>jmbg=getJMBG();
            List<String>names=getNames();
            List<String>surnames=getSurnames();

            int minn=Math.min(jmbg.size(),Math.min(names.size(),surnames.size()));

            for(int i=0;i<minn;i++){
                PatientDto pom=new PatientDto();
                pom.setJmbg(jmbg.get(i));
                pom.setLbp(lbp);
                pom.setName(names.get(i));
                pom.setSurname(surnames.get(i));
                ret.add(pom);
            }

            return ret;
        }
    }


}
