package raf.bolnica1.employees.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.employee.EmployeeCreateDto;
import raf.bolnica1.employees.dto.employee.EmployeeDto;
import raf.bolnica1.employees.dto.employee.EmployeeUpdateAdminDto;
import raf.bolnica1.employees.exceptionHandler.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.repository.DepartmentRepository;

@Component
@AllArgsConstructor
public class DepartmentMapper {

    public DepartmentDto toDto(Department entity) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(entity.getId());
        dto.setPbo(entity.getPbo());
        dto.setName(entity.getName());
        dto.setHospitalName(entity.getHospital().getFullName());
        return dto;
    }

}
