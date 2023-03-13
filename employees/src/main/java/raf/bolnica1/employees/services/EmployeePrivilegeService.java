package raf.bolnica1.employees.services;

import raf.bolnica1.employees.dto.privilege.PrivilegeDto;

import java.util.List;

public interface EmployeePrivilegeService {

    List<PrivilegeDto> privilegeForEmployee(String lbz);
}
