package raf.bolnica1.employees.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.employees.dto.employee.*;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeCreateDto dto);

    EmployeeDto findEmployeeInfo(String lbz);

    EmployeeMessageDto softDeleteEmployee(String lbz);

    Object passwordReset(Object object);

    Page<EmployeeDto> listEmployeesWithFilters(String name, String surname, String deleted, String departmentName, String hospitalShortName, int page, int size);

    EmployeeDto editEmployeeInfo(EmployeeUpdateDto dto, String lbz);

    EmployeeDto editEmployeeInfoByAdmin(EmployeeUpdateAdminDto dto, String lbz);
}
