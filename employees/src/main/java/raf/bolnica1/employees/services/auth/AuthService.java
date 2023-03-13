package raf.bolnica1.employees.services.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesPrivilege;
import raf.bolnica1.employees.dto.privilege.PermissionsCheckDto;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesPrivilegeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private EmployeeRepository employeeRepository;
    private EmployeesPrivilegeRepository employeesPrivilegeRepository;

    public Boolean checkPermission(PermissionsCheckDto permissions, String lbz){
        Employee employee = employeeRepository.findByLbz(lbz).orElse(null);

        if(employee == null)
            return false;

        List<EmployeesPrivilege> employeesPrivilegeList = this.employeesPrivilegeRepository.findByEmployee(employee);
        for(String neededPermission : permissions.getPermissionsCheckDtoList()) {
          for (EmployeesPrivilege employeesPrivilege : employeesPrivilegeList) {
              System.out.println("POREDIM " + neededPermission + " VS " + employeesPrivilege.getPrivilege().getPrivilegeShort().name());
            if (employeesPrivilege.getPrivilege().getPrivilegeShort().name().equals(neededPermission))
              return true;
          }
        }

        return false;
    }
}
