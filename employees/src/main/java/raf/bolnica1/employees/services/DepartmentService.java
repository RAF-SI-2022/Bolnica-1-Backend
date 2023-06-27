package raf.bolnica1.employees.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;
import raf.bolnica1.employees.dto.employee.DoctorDepartmentDto;

import java.util.List;

public interface DepartmentService {


    Long findDepartmentIdByLbz(String lbz);

    List<DepartmentDto> listAllDepartments();

    DepartmentDto getEmployeesDepartment(String pbo);


     List<HospitalDto> listAllHospitals();

     Page<HospitalDto> findHospitalsByDepartmentName(String name, Integer page, Integer size);

     List<DepartmentDto> getDepartments(String pbb);

     List<DoctorDepartmentDto> getAllDoctorsByPbo(String pbo);

     Page<DepartmentDto> findHospitalsByDepartmentNameSecond(String name, Integer page, Integer size);

    DepartmentDto getEmployeesDepartmentById(Long id);
}
