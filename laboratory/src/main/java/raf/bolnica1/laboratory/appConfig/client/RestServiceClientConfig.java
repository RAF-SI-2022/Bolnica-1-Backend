package raf.bolnica1.laboratory.appConfig.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestServiceClientConfig {

    @Value("${employee.service.url}")
    private String employeeServiceUrl;

    private static String employeeServiceUrlStatic;
    private static String patientsServiceUrlStatic;

    @Bean
    @Qualifier("departmentRestTemplate")
    @DependsOn("setEmployeeServiceUrlStatic")
    public static RestTemplate departmentRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic+"/department"));
        return restTemplate;
    }

    @Bean
    @Qualifier("loginRestTemplate")
    @DependsOn("setEmployeeServiceUrlStatic")
    public static RestTemplate loginRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic+"/auth/login"));
        return restTemplate;
    }

    @Bean
    @Qualifier("patientRestTemplate")
    @DependsOn("setPatientsServiceUrl")
    public static RestTemplate patientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/patient"));
        return restTemplate;
    }

    @Bean
    @Qualifier("prescriptionRestTemplate")
    @DependsOn("setPatientsServiceUrl")
    public static RestTemplate prescriptionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/prescription"));
        return restTemplate;
    }

    @Bean
    @Qualifier("infoRestTemplate")
    @DependsOn("setPatientsServiceUrl")
    public static RestTemplate infoRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/info"));
        return restTemplate;
    }

    @Bean
    @Qualifier("diagnosisCodeRestTemplate")
    @DependsOn("setPatientsServiceUrl")
    public static RestTemplate diagnosisCodeRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
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
    @Bean
    public String setEmployeeServiceUrlStatic(String employeeServiceUrl){
        RestServiceClientConfig.employeeServiceUrlStatic = employeeServiceUrl;
        return "";
    }
    @Bean
    @Value("${patients.service.url}")
    public String setPatientsServiceUrl(String patientsServiceUrl){
        RestServiceClientConfig.patientsServiceUrlStatic = patientsServiceUrl;
        return "";
    }


}

