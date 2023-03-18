package raf.bolnica1.employees.services;

import raf.bolnica1.employees.dto.role.RoleDto;

import java.util.List;

public interface EmployeeRoleService {
    List<RoleDto> privilegeForEmployee(String lbz);
}
