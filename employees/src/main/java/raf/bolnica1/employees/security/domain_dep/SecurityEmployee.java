package raf.bolnica1.employees.security.domain_dep;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Role;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.role.RoleDto;
import raf.bolnica1.employees.services.EmployeeRoleService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityEmployee implements UserDetails, UserDetailsPasswordService {
    private final Employee employee;
    private final EmployeeRoleService service;

    public SecurityEmployee(Employee employee, EmployeeRoleService service) {
        this.employee = employee;
        this.service = service;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleDto> roleDtoList = service.privilegeForEmployee(employee.getLbz());

        return roleDtoList.stream()
                .map(obj -> {
                    Role p = new Role();
                    p.setRoleShort(RoleShort.valueOf(obj.getShortName()));
                    return p;
                })
                .map(SecurityAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !employee.isDeleted();
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        employee.setPassword(newPassword);
        return this;
    }

    public String getLbz() {
        return employee.getLbz();
    }

}
