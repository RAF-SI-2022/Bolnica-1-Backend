package raf.bolnica1.patient.config.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class UserServiceClientConfig {

    @Value("${employees.service.url}")
    private String employeesServiceUrl;
    @Bean
    @Qualifier("userRestTemplate")
    public RestTemplate userServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeesServiceUrl+"/auth"));
        return restTemplate;
    }

    @Bean
    @Qualifier("employeeRestTemplate")
    public RestTemplate employeeServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeesServiceUrl+"/employee"));
        return restTemplate;
    }

}
