package raf.bolnica1.laboratory.services.employee;

import raf.bolnica1.laboratory.dto.employee.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDto> getEmployee(String lbz, String authorizationHeader);

}
