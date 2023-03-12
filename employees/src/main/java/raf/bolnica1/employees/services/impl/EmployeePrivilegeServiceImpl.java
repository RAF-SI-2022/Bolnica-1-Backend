package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesPrivilege;
import raf.bolnica1.employees.dto.privilege.PrivilegeDto;
import raf.bolnica1.employees.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesPrivilegeRepository;
import raf.bolnica1.employees.repository.PrivilegeRepository;
import raf.bolnica1.employees.services.EmployeePrivilegeService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeePrivilegeServiceImpl implements EmployeePrivilegeService {

    private EmployeeRepository employeeRepository;
    private PrivilegeRepository privilegeRepository;
    private EmployeesPrivilegeRepository employeesPrivilegeRepository;


    @Override
    public List<PrivilegeDto> privilegeForEmployee(String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz)));
        List<PrivilegeDto> privilegeDtos = new ArrayList<>();

        for(EmployeesPrivilege employeesPrivilege : employeesPrivilegeRepository.findByEmployee(employee)){
            privilegeDtos.add(new PrivilegeDto(employeesPrivilege.getPrivilege().getId(), employeesPrivilege.getPrivilege().getPrivilegeShort().name()));
        }
        return privilegeDtos;
    }
}
