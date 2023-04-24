package raf.bolnica1.laboratory.appConfig.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.File;
import java.util.Scanner;

@Configuration
public class RestServiceClientConfig {

    @Value("${employee.service.url}")
    private String employeeServiceUrl;

    private static String employeeServiceUrlStatic;
    private static String patientsServiceUrlStatic;

    @Bean
    @Qualifier("departmentRestTemplate")
    public static RestTemplate departmentRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(employeeServiceUrlStatic == null){
            if(System.getProperty("employee-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("employee-url")));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api"));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic+"/department"));
        return restTemplate;
    }

    @Bean
    @Qualifier("loginRestTemplate")
    public static RestTemplate loginRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(employeeServiceUrlStatic == null){
            if(System.getProperty("employee-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("employee-url")));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api"));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic+"/auth/login"));
        return restTemplate;
    }

    @Bean
    @Qualifier("patientRestTemplate")
    public static RestTemplate patientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(patientsServiceUrlStatic == null){
            if(System.getProperty("patients-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("patients-url")+"/patient"));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api/patient"));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/patient"));
        return restTemplate;
    }

    @Bean
    @Qualifier("prescriptionRestTemplate")
    public static RestTemplate prescriptionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(patientsServiceUrlStatic == null){
            if(System.getProperty("patients-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("patients-url")+"/prescription"));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api/prescription"));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/prescription"));
        return restTemplate;
    }

    @Bean
    @Qualifier("infoRestTemplate")
    public static RestTemplate infoRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(patientsServiceUrlStatic == null){
            if(System.getProperty("patients-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("patients-url")+"/info"));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("http://localhost:8081/api/info")));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/info"));
        return restTemplate;
    }

    @Bean
    @Qualifier("diagnosisCodeRestTemplate")
    public static RestTemplate diagnosisCodeRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(patientsServiceUrlStatic == null){
            if(System.getProperty("patients-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("patients-url")+"/record/gather_diagnosis"));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api/record/gather_diagnosis"));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/record/gather_diagnosis"));
        return restTemplate;
    }

    @Bean
    @Qualifier("labPrescriptionRestTemplate")
    public static RestTemplate labPrescriptionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8083/api/laboratory/prescription/get_patient"));
        return restTemplate;
    }

    @Value("${employee.service.url}")
    public void setEmployeeServiceUrlStatic(String employeeServiceUrl){
        RestServiceClientConfig.employeeServiceUrlStatic = employeeServiceUrl;
    }
    @Value("${patients.service.url}")
    public void setPatientsServiceUrl(String patientsServiceUrl){
        RestServiceClientConfig.patientsServiceUrlStatic = patientsServiceUrl;
    }


}

