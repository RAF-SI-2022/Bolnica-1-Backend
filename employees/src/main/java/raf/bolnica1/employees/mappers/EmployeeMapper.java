package raf.bolnica1.employees.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.dto.employee.EmployeeCreateDto;
import raf.bolnica1.employees.dto.employee.EmployeeDto;
import raf.bolnica1.employees.dto.employee.EmployeeUpdateAdminDto;
import raf.bolnica1.employees.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.repository.DepartmentRepository;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    private DepartmentRepository departmentRepository;

    public Employee toEntity(EmployeeCreateDto employeeDto) {
        Employee employee = new Employee();
        employee.setLbz(employeeDto.getLbz());
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setGender(employeeDto.getGender());
        employee.setJmbg(employeeDto.getJmbg());
        employee.setAddress(employeeDto.getAddress());
        employee.setPlaceOfLiving(employeeDto.getPlaceOfLiving());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmail(employeeDto.getEmail());

        String usernamePassword = employeeDto.getEmail().split("@")[0];
        employee.setUsername(usernamePassword);
        employee.setPassword(usernamePassword);

        employee.setTitle(employeeDto.getTitle());
        employee.setProfession(employeeDto.getProfession());
        Department department = departmentRepository.findByPbo(employeeDto.getDepartmentPbo()).orElseThrow(() -> new DepartmentNotFoundException(String.format("Department with pbo <%s> not found.", employeeDto.getDepartmentPbo())));
        employee.setDepartment(department);
        return employee;
    }

    public EmployeeDto toDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(entity.getId());
        dto.setLbz(entity.getLbz());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setGender(entity.getGender());
        dto.setJmbg(entity.getJmbg());
        dto.setAddress(entity.getAddress());
        dto.setPlaceOfLiving(entity.getPlaceOfLiving());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setDeleted(entity.isDeleted());
        dto.setTitle(entity.getTitle());
        dto.setProfession(entity.getProfession());
        dto.setDepartment(entity.getDepartment());
        return dto;

    }

    public Employee toEntity(EmployeeUpdateAdminDto dto, Employee employee) {
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setJmbg(dto.getJmbg());
        employee.setAddress(dto.getAddress());
        employee.setPlaceOfLiving(dto.getPlaceOfLiving());
        employee.setPhone(dto.getPhone());
        employee.setEmail(dto.getEmail());
        employee.setDeleted(dto.isDeleted());
        employee.setUsername(dto.getUsername());
        employee.setPassword(dto.getPassword());
        employee.setTitle(dto.getTitle());
        employee.setProfession(dto.getProfession());
        Department department = departmentRepository.findByPbo(dto.getDepartmentPbo()).orElseThrow(() -> new DepartmentNotFoundException(String.format("Department with pbo <%s> not found.", dto.getDepartmentPbo())));
        employee.setDepartment(department);
        return employee;
    }
}
