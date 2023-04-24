package raf.bolnica1.infirmary.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
@Configuration
public class RestServiceClientConfig {

    private static String employeeServiceUrlStatic;
    private static String patientsServiceUrlStatic;
    private static String laboratoryServiceUrlStatic;
    @Bean
    @Qualifier("departmentRestTemplate")
    @DependsOn("setEmployeeServiceUrlStatic")
    public static RestTemplate departmentRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic + "/department"));
        return restTemplate;
    }

    @Bean
    @Qualifier("loginRestTemplate")
    @DependsOn("setEmployeeServiceUrlStatic")
    public static RestTemplate loginRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic + "/auth/login"));
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
    @DependsOn("setLaboratoryServiceUrl")
    public static RestTemplate labPrescriptionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(laboratoryServiceUrlStatic+"/laboratory/prescription/get_patient"));
        return restTemplate;
    }

    @Bean
    @Value("${employee.service.url}")
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
    @Bean
    @Value("${laboratory.service.url}")
    public String setLaboratoryServiceUrl(String laboratoryServiceUrl){
        RestServiceClientConfig.laboratoryServiceUrlStatic = laboratoryServiceUrl;
        return "";
    }

}

