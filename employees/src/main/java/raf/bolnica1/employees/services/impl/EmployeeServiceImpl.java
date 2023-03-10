package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.services.EmployeeService;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDto createEmployee(EmployeeCreateDto dto) {
        Employee employee = employeeRepository.findByLbz(dto.getLbz()).orElse(null);
        if(employee == null){
            Employee newEmployee = employeeMapper.toEntity(dto);
            newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
            employeeRepository.save(newEmployee);
            return employeeMapper.toDto(newEmployee);
        }
        throw new EmployeeAlreadyExistsException(String.format("Employee with lbz <%s> already exists.", dto.getLbz()));
    }

    @Override
    public EmployeeDto findEmployeeInfo(String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz));
        }
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeMessageDto softDeleteEmployee(String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz));
        }
        employee.setDeleted(true);
        employeeRepository.save(employee);
        return new EmployeeMessageDto(String.format("Employee with lbz <%s> deleted", lbz));
    }

    @Override
    public Object passwordReset(Object object) {
        return null;
    }

    @Override
    public Page<EmployeeDto> listEmployeesWithFilters(String name, String surname, String deleted, String departmentName, String hospitalShortName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if(deleted == null || deleted.equals("")){
            return employeeRepository
                    .listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), departmentName.toLowerCase(), hospitalShortName.toLowerCase())
                    .map(employeeMapper::toDto);
        }
        if(!deleted.equals("false") && !deleted.equals("true")){
            throw new RuntimeException("Nije dobar parametar <deleted>");
        }
        return employeeRepository
                .listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), Boolean.parseBoolean(deleted), departmentName.toLowerCase(), hospitalShortName.toLowerCase())
                .map(employeeMapper::toDto);
    }

    @Override
    public EmployeeDto editEmployeeInfo(EmployeeUpdateDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz));
        }
        if(!passwordEncoder.matches(dto.getOldPassword(), employee.getPassword())){
            throw new EmployeePasswordException(("The password you entered does not match your current password."));
        }

        employee.setPassword(dto.getNewPassword());
        // treba odraditi proveru za broj telefona po regexu
        employee.setPhone(dto.getPhone());

        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto editEmployeeInfoByAdmin(EmployeeUpdateAdminDto dto, String lbz) {
        Employee employee = employeeRepository.findByLbz(lbz).orElse(null);
        if(employee == null){
            throw new EmployeeNotFoundException(String.format("Employee with lbz <%s> not found.", lbz));
        }
        // mozda treba raditi neke provere
        employeeRepository.save(employeeMapper.toEntity(dto, employee));
        return employeeMapper.toDto(employee);
    }

}
