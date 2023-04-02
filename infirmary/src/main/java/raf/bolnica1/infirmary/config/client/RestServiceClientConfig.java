package raf.bolnica1.infirmary.config.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
@Configuration
public class RestServiceClientConfig {
    @Bean
    @Qualifier("departmentRestTemplate")
    public RestTemplate departmentRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api/department/"));
        return restTemplate;
    }

    @Bean
    @Qualifier("patientRestTemplate")
    public RestTemplate patientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api/patient/"));
        return restTemplate;
    }
}
