package raf.bolnica1.laboratory.appConfig.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class EmployeeServiceClientConfig {
    @Value("${employee.service.url}")
    private String employeeServiceUrl;

    @Bean
    @Qualifier("employeeRestTemplate")
    public RestTemplate userServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if(employeeServiceUrl == null){
            if(System.getProperty("employee-url") != null)
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("employee-url")));
            else
                restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(System.getProperty("http://localhost:8080/api")));
        }
        else
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(employeeServiceUrl));
        return restTemplate;
    }

    // Novija opcija, moze isto sto i rest + async ako zatreba
    @Bean
    @Qualifier("employeeWebClient")
    public WebClient employeeWebClient() {
        if(employeeServiceUrl == null){
            if(System.getProperty("employee-url") != null)
                return WebClient.builder()
                        .baseUrl(System.getProperty("employee-url"))
                        .build();
            else
                return WebClient.builder()
                        .baseUrl(System.getProperty("http://localhost:8080/api"))
                        .build();
        }
        else
            return WebClient.builder()
                    .baseUrl(employeeServiceUrl)
                    .build();
    }

}
