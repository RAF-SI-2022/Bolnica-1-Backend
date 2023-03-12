package raf.bolnica1.employees.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.repository.EmployeeRepository;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class UserDetailsServiceProvider {

    private final EmployeeRepository employeeRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Employee> emp = employeeRepository.findByUsername(username);
                if(emp.isPresent()) {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(username)
                            .password(emp.get().getPassword())
//                    .roles(emp.get().getRole().toString())
                            .roles(" ")
                            .build();
                }
                throw new UsernameNotFoundException("User not found with this username: " + username);
            }
        };
    }
}
