package raf.bolnica1.employees.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import raf.bolnica1.employees.security.util.JwtUtils;
import raf.bolnica1.employees.services.EmployeeService;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeesRoleRepository employeesRoleRepository;
    private final RoleRepository roleRepository;
    @Qualifier("exceptionsMessageSource")
    private final MessageSource messageSource;

    private final JwtUtils jwtUtils;

    public EmployeeServiceImpl(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeesRoleRepository employeesRoleRepository, RoleRepository roleRepository, MessageSource messageSource, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeesRoleRepository = employeesRoleRepository;
        this.roleRepository = roleRepository;
        this.messageSource = messageSource;
        this.jwtUtils = jwtUtils;
    }

    @Override
    //    @CachePut(value = "employee", key="#dto.lbz")
    //@CacheEvict(value = "employees", allEntries = true)
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
    @Transactional(timeout = 20)
    //@Cacheable(value = "employee", key = "#lbz")
    public EmployeeDto findEmployeeInfo(String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional(timeout = 20)
    /*@Caching(evict = {
            @CacheEvict(value = "employee", key = "#lbz"),
            @CacheEvict(value = "employees"),
            @CacheEvict(value = "emplByDep"),
            @CacheEvict(value = "emplRoles", key = "#lbz")
    })*/
    public EmployeeMessageDto softDeleteEmployee(String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );
        employee.setDeleted(true);
        employeeRepository.save(employee);
        return new EmployeeMessageDto(String.format("Employee with lbz <%s> deleted", lbz));
    }

    @Override
    @Transactional(timeout = 20)
    public EmployeeMessageDto passwordReset(PasswordResetDto passwordResetDto, String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        if (!passwordEncoder.matches(passwordResetDto.getOldPassword(), employee.getPassword()))
            throw new EmployeePasswordException("The password you entered does not match your current password.");

        employee.setNewPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        employee.setResetPassword(UUID.randomUUID().toString() + employee.getId());
        String jwt = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                jwt = jwtUtils.generateToken(userDetails);
            }
        }
        return new EmployeeMessageDto(String.format("http://localhost:8080/api/employee/password-reset/%s/%s/%s", lbz, employeeRepository.save(employee).getResetPassword(), jwt));
    }

    @Override
    @Transactional(timeout = 20)
    public EmployeeDto passwordResetToken(String lbz, String token) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
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
    @Transactional(timeout = 20)
    //@CachePut(value = "employee", key = "#lbz")
    //@CacheEvict(value = "employees", allEntries = true)
    public EmployeeDto editEmployeeInfo(EmployeeUpdateDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );

        if (!passwordEncoder.matches(dto.getOldPassword(), employee.getPassword())) {
            throw new EmployeePasswordException(("The password you entered does not match your current password."));
        }
        // ? Zasto passwordReset ovde
        // passwordReset(new PasswordResetDto(dto.getOldPassword(), dto.getNewPassword()), lbz);
        // employee.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        employee.setPhone(dto.getPhone());
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDto(saved);
    }

    @Override
    @Transactional(timeout = 20)
    //@CachePut(value = "employee", key = "#lbz")
    /*@Caching(evict = {
            @CacheEvict("employees"),
            @CacheEvict(value = "emplRoles", key = "#lbz")
    })*/
    public EmployeeDto editEmployeeInfoByAdmin(EmployeeUpdateAdminDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbzLock(lbz).orElseThrow(() ->
                new EmployeeNotFoundException(
                        messageSource.getMessage("employee.already.exists", new Object[]{lbz}, Locale.getDefault())
                )
        );
        Employee employeeChanged = employeeMapper.toEntity(dto, employee);
        if(dto.getPassword() != null && !dto.getPassword().equals(""))
            employeeChanged.setPassword(passwordEncoder.encode(employeeChanged.getPassword()));
        employeeChanged = employeeRepository.save(employeeChanged);
        changePermissions(employeeChanged, dto.getPermissions());
        return employeeMapper.toDto(employeeChanged);
    }

    @Override
    //@Cacheable(value = "emplByDep", key = "#pbo")
    public List<EmployeeDto> findDoctorSpecialistsByDepartment(String pbo) {
        List<EmployeesRole> employeesRolesList = employeeRepository.listDoctorsSpecialistsByDepartment(pbo)
                .orElseThrow(() -> new RuntimeException(String.format("Doctor specialists for department with pbo %s not found.", pbo)));

        List<EmployeeDto> employees = new ArrayList<>();

        for(EmployeesRole er: employeesRolesList) {
            employees.add(employeeMapper.toDto(er.getEmployee()));
        }

        return employees;
    }

    public List<EmployeeDto> findNonDoctorsByDepartment(String pbo) {
        List<EmployeesRole> employeesRolesList = employeeRepository.listNonDoctorsByDepartment(pbo)
                .orElseThrow(() -> new RuntimeException(String.format("Doctor specialists for department with pbo %s not found.", pbo)));

        List<EmployeeDto> employees = new ArrayList<>();

        for(EmployeesRole er: employeesRolesList) {
            employees.add(employeeMapper.toDto(er.getEmployee()));
        }

        return employees;
    }

    private void changePermissions(Employee employeeChanged, List<String> permissions) {
        employeesRoleRepository.deleteAll(employeesRoleRepository.findByEmployee(employeeChanged));
        addEmployeePermission(employeeChanged, permissions);
    }

}
