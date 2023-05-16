package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;
import raf.bolnica1.employees.dto.role.RoleDto;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.RoleRepository;
import raf.bolnica1.employees.services.EmployeeRoleService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    private final EmployeeRepository employeeRepository;
    private final EmployeesRoleRepository employeesRoleRepository;

    @Override
    public List<RoleDto> privilegeForEmployee(String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz)));
        List<RoleDto> roleDtos = new ArrayList<>();

        for (EmployeesRole employeesRole : employeesRoleRepository.findByEmployee(employee)) {
            roleDtos.add(new RoleDto(employeesRole.getRole().getId(), employeesRole.getRole().getRoleShort().name()));
        }
        return roleDtos;
    }
}
