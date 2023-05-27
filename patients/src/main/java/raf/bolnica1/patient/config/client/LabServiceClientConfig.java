package raf.bolnica1.patient.config.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class LabServiceClientConfig {

    @Value("${laboratory.service.url}")
    private String laboratoryServiceUrl;

    @Bean
    @Qualifier("labRestTemplate")
    public RestTemplate labServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(laboratoryServiceUrl));
        return restTemplate;
    }
}
