package raf.bolnica1.laboratory.appConfig.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class EmployeeServiceClientConfig {
    @Bean
    @Qualifier("employeeRestTemplate")
    public RestTemplate userServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api"));
        return restTemplate;
    }

    // Novija opcija, moze isto sto i rest + async ako zatreba
    @Bean
    @Qualifier("employeeWebClient")
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .build();
    }

}
