package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.services.DepartmentService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> listAllDepartments() {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for(Department department : departmentRepository.findAll()){
            departmentDtos.add(new DepartmentDto(department.getId(), department.getPbo(), department.getName(), department.getHospital().getShortName()));
        }
        return departmentDtos;
    }
}
