package raf.bolnica1.employees.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;
import raf.bolnica1.employees.domain.Role;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.RoleRepository;
import raf.bolnica1.employees.services.EmployeeService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeesRoleRepository employeesRoleRepository;
    private final RoleRepository roleRepository;
    @Qualifier("exceptionsMessageSource")
    private final MessageSource messageSource;

    public EmployeeServiceImpl(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeesRoleRepository employeesRoleRepository, RoleRepository roleRepository, MessageSource messageSource) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeesRoleRepository = employeesRoleRepository;
        this.roleRepository = roleRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Optional<Employee> employee = employeeRepository.findByLbz(dto.getLbz());

        if (employee.isPresent()) {
            String message = messageSource.getMessage("employee.already.exists", new Object[]{dto.getLbz()}, Locale.getDefault());
            throw new EmployeeAlreadyExistsException(message);
        }

        Employee newEmployee = employeeMapper.toEntity(dto);
        newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
        newEmployee = employeeRepository.save(newEmployee);
        addEmployeePermission(newEmployee, dto.getPermissions());
        return employeeMapper.toDto(newEmployee);
    }

    private void addEmployeePermission(Employee newEmployee, List<String> permissions) {
        for (String permission : permissions) {
            EmployeesRole employeesRole = new EmployeesRole();
            employeesRole.setEmployee(newEmployee);

            RoleShort found = RoleShort.valueOf(permission);

            Role role = roleRepository.findByRoleShort(found).orElseThrow(() ->
                    new RuntimeException(String.format("Role %s is not supported", permission))
            );
            employeesRole.setRole(role);

            employeesRoleRepository.save(employeesRole);
        }
    }

    @Override
    public EmployeeDto findEmployeeInfo(String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeMessageDto softDeleteEmployee(String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );
        employee.setDeleted(true);
        employeeRepository.save(employee);
        return new EmployeeMessageDto(String.format("Employee with lbz <%s> deleted", lbz));
    }

    @Override
    @Transactional
    public EmployeeMessageDto passwordReset(PasswordResetDto passwordResetDto, String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        if (!passwordEncoder.matches(passwordResetDto.getOldPassword(), employee.getPassword()))
            throw new EmployeePasswordException("The password you entered does not match your current password.");

        employee.setNewPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        employee.setResetPassword(UUID.randomUUID().toString() + employee.getId());

        return new EmployeeMessageDto(String.format("http://localhost:8080/api/employee/password_reset/%s/%s", lbz, employeeRepository.save(employee).getResetPassword()));
    }

    @Override
    @Transactional
    public EmployeeDto passwordResetToken(String lbz, String token) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        if (!employee.getResetPassword().equals("NO") && employee.getResetPassword().equals(token)) {
            employee.setPassword(employee.getNewPassword());
            employee.setNewPassword("NO");
            employee.setResetPassword("NO");
        } else
            throw new EmployeePasswordException("Invalid password reset request!");

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toDto(saved);
    }

    @Override
    public Page<EmployeeDto> listEmployeesWithFilters(String name, String surname, String deleted, String departmentName, String hospitalShortName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (deleted == null || deleted.equals("")) {
            return employeeRepository
                    .listEmployeesWithFilters(pageable, name, surname, departmentName, hospitalShortName)
                    .map(employeeMapper::toDto);
        }
        if (!deleted.equals("false") && !deleted.equals("true")) {
            throw new RuntimeException("Nije dobar parametar <deleted>");
        }
        return employeeRepository
                .listEmployeesWithFilters(pageable, name, surname, Boolean.parseBoolean(deleted), departmentName, hospitalShortName)
                .map(employeeMapper::toDto);
    }

    @Override
    @Transactional
    public EmployeeDto editEmployeeInfo(EmployeeUpdateDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        if (!passwordEncoder.matches(dto.getOldPassword(), employee.getPassword())) {
            throw new EmployeePasswordException(("The password you entered does not match your current password."));
        }

        passwordReset(new PasswordResetDto(dto.getOldPassword(), dto.getNewPassword()), lbz);
        employee.setPhone(dto.getPhone());
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public EmployeeDto editEmployeeInfoByAdmin(EmployeeUpdateAdminDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );
        Employee employeeChanged = employeeMapper.toEntity(dto, employee);
        if (dto.getPassword() != null && !dto.getPassword().equals(""))
            employeeChanged.setPassword(passwordEncoder.encode(employeeChanged.getPassword()));
        employeeChanged = employeeRepository.save(employeeChanged);
        changePermissions(employeeChanged, dto.getPermissions());
        return employeeMapper.toDto(employeeChanged);
    }

    private void changePermissions(Employee employeeChanged, List<String> permissions) {
        employeesRoleRepository.deleteAll(employeesRoleRepository.findByEmployee(employeeChanged));
        addEmployeePermission(employeeChanged, permissions);
    }

}
