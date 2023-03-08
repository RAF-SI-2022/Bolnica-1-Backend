package raf.bolnica1.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Object createEmployee(Object object) {
        return null;
    }

    public Object findEmployeeInfo(Object object) {
        return null;
    }

    public Object softDeleteEmployee(Object object) {
        return null;
    }

    public Object passwordReset(Object object) {
        return null;
    }

    public Object listEmployees(Object object) {
        return null;
    }

    public Object editEmployeeInfo(Object object) {
        return null;
    }
}
