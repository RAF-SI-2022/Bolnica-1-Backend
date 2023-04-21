package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;
import raf.bolnica1.employees.dto.employee.DoctorDepartmentDto;
import raf.bolnica1.employees.exceptionHandler.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.mappers.DepartmentMapper;
import raf.bolnica1.employees.mappers.HospitalMapper;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.HospitalRepository;
import raf.bolnica1.employees.services.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final HospitalRepository hospitalRepository;
    private final EmployeesRoleRepository employeesRoleRepository;
    private final HospitalMapper hospitalMapper;
    private final DepartmentMapper departmentMapper;

    @Override
    public Long findDepartmentIdByLbz(String lbz) {
        Employee employee=employeeRepository.findByLbz(lbz).orElseThrow(()->new EmployeeNotFoundException(String.format("Employee with lbz %s not found",lbz)));
        return employee.getDepartment().getId();
    }

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
    public List<DepartmentDto> getDepartments(String pbb) {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        List<Department> departments= departmentRepository.findByHostpitalPbb(pbb);
        for(Department department: departments){
            departmentDtos.add(new DepartmentDto(department.getId(), department.getPbo(), department.getName(), department.getHospital().getFullName()));
        }
        return departmentDtos;
    }

    @Override
    public List<DoctorDepartmentDto> getAllDoctorsByPbo(String pbo) {

        List<RoleShort> roleShortList=new ArrayList<>();
        roleShortList.add(RoleShort.ROLE_DR_SPEC);
        roleShortList.add(RoleShort.ROLE_DR_SPEC_ODELJENJA);
        roleShortList.add(RoleShort.ROLE_DR_SPEC_POV);

        List<Object[]> doctors= employeesRoleRepository.findEmployeesByDepartmentPboAndRoleList(pbo,roleShortList);

        List<DoctorDepartmentDto>doctorDtos=new ArrayList<>();
        for(Object[] info:doctors){
            DoctorDepartmentDto doctorDto=new DoctorDepartmentDto();
            doctorDto.setName((String)info[0]);
            doctorDto.setSurname((String)info[1]);
            doctorDto.setLbz((String)info[2]);
            doctorDto.setDepartmentId((Long)info[3]);
            doctorDtos.add(doctorDto);
        }

        return doctorDtos;

    }

    @Override
    public Page<DepartmentDto> findHospitalsByDepartmentNameSecond(String name, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Department> departments=departmentRepository.findDepartmentName(pageable,name);
        return departments.map(departmentMapper::toDto);
    }

    @Override
    public List<HospitalDto> listAllHospitals() {
        List<HospitalDto> hospitalDtos = new ArrayList<>();
        for (Hospital hospital : hospitalRepository.findAll()) {
            hospitalDtos.add(new HospitalDto(hospital.getId(), hospital.getShortName()));
        }
        return hospitalDtos;
    }

    @Override
    public Page<HospitalDto> findHospitalsByDepartmentName(String name, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Hospital> hospitals=departmentRepository.findHospitalsByDepartmentName(pageable,name);
        return hospitals.map(hospitalMapper::toDto);
    }
}
