package raf.bolnica1.laboratory.services.employee;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import raf.bolnica1.laboratory.dto.employee.EmployeeDto;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Qualifier("employeeWebClient")
    private final WebClient webClient;

    @Value("${employee.service.base-url}")
    private String employeeServiceBaseUrl;

    public EmployeeServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Test za komunikaciju
     */
    @Override
    public Mono<EmployeeDto> getEmployee(String lbz, String authorizationHeader) {
        return webClient.get()
                .uri(employeeServiceBaseUrl + "/find/" + lbz)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .bodyToMono(EmployeeDto.class);
    }
}
