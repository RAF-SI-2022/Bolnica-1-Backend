package raf.bolnica1.infirmary.config.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
@Configuration
public class RestServiceClientConfig {

    private static String employeeServiceUrlStatic;
    private static String patientsServiceUrlStatic;
    private static String laboratoryServiceUrlStatic;
    @Bean
    @Qualifier("departmentRestTemplate")
    public static RestTemplate departmentRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic + "/department"));
        return restTemplate;
    }

    @Bean
    @Qualifier("loginRestTemplate")
    public static RestTemplate loginRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrlStatic + "/auth/login"));
        return restTemplate;
    }

    @Bean
    @Qualifier("patientRestTemplate")
    public static RestTemplate patientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/patient"));
        return restTemplate;
    }

    @Bean
    @Qualifier("infoRestTemplate")
    public static RestTemplate infoRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/info"));
        return restTemplate;
    }

    @Bean
    @Qualifier("diagnosisCodeRestTemplate")
    public static RestTemplate diagnosisCodeRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(patientsServiceUrlStatic + "/record/gather_diagnosis"));
        return restTemplate;
    }

    @Bean
    @Qualifier("labPrescriptionRestTemplate")
    public static RestTemplate labPrescriptionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(laboratoryServiceUrlStatic+"/laboratory/prescription/get_patient"));
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
    @Value("${laboratory.service.url}")
    public void setLaboratoryServiceUrl(String laboratoryServiceUrl){
        RestServiceClientConfig.laboratoryServiceUrlStatic = laboratoryServiceUrl;
    }

}

