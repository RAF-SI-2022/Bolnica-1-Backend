package raf.bolnica1.employees.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesPrivilege;
import raf.bolnica1.employees.repository.EmployeesPrivilegeRepository;
import raf.bolnica1.employees.services.EmployeeService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserDetailService implements UserDetailsService {

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private EmployeesPrivilegeRepository employeesPrivilegeRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   Employee employee = employeeService.getUserByUsername(username);

    return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), !employee.isDeleted(), true, true, true, getAuthorities(employee));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Employee employee){
    Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
    List<EmployeesPrivilege> employeesPrivilegeList = employeesPrivilegeRepository.findByEmployee(employee);
    for(EmployeesPrivilege employeesPrivilege : employeesPrivilegeList) {
      grantedAuthoritySet.add(new SimpleGrantedAuthority(employeesPrivilege.getPrivilege().getPrivilegeShort().name()));
    }
    return grantedAuthoritySet;
  }
}
