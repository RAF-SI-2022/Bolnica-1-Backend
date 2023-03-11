package raf.bolnica1.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.repository.EmployeeRepository;

import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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
}
