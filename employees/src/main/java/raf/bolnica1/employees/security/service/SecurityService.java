package raf.bolnica1.employees.security.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.security.domain.SecurityEmployee;
import raf.bolnica1.employees.security.util.JwtUtils;
import raf.bolnica1.employees.services.EmployeeRoleService;

@Service
@AllArgsConstructor
public class SecurityService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRoleService employeeRoleService;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws EmployeeNotFoundException {
        Employee e = employeeRepository.findByUsername(username).orElseThrow(() ->
                new EmployeeNotFoundException("<Security>Employee with lbz <%s> doesn't exist.")
        );
        return new SecurityEmployee(e, employeeRoleService);
    }

    public String generateToken(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return jwtUtils.generateToken(userDetails);
    }
}
