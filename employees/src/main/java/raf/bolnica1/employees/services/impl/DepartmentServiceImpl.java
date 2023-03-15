package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;
import raf.bolnica1.employees.exceptionHandler.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.HospitalRepository;
import raf.bolnica1.employees.services.DepartmentService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public List<DepartmentDto> listAllDepartments() {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departmentRepository.findAll()) {
            departmentDtos.add(new DepartmentDto(department.getId(), department.getPbo(), department.getName(), department.getHospital().getShortName()));
        }
        return departmentDtos;
    }

    @Override
    public DepartmentDto getEmployeesDepartment(String pbo) {
        Department department = departmentRepository.findByPbo(pbo).orElseThrow(() -> new DepartmentNotFoundException(String.format("Department with pbo %s not found", pbo)));
        return new DepartmentDto(department.getId(), department.getPbo(), department.getName(), department.getHospital().getShortName());
    }


    @Override
    public List<HospitalDto> listAllHospitals() {
        List<HospitalDto> hospitalDtos = new ArrayList<>();
        for (Hospital hospital : hospitalRepository.findAll()) {
            hospitalDtos.add(new HospitalDto(hospital.getId(), hospital.getShortName()));
        }
        return hospitalDtos;
    }
}
