package raf.bolnica1.employees.security.domain_dep;


import org.springframework.security.core.GrantedAuthority;
import raf.bolnica1.employees.domain.Role;

public class SecurityAuthority implements GrantedAuthority {
    private final Role role;

    public SecurityAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getRoleShort().name();
    }
}
