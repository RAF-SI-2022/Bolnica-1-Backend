package raf.bolnica1.employees.services;

import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;

import java.util.List;

public interface DepartmentService {


    Long findDepartmentIdByLbz(String lbz);

    List<DepartmentDto> listAllDepartments();

    DepartmentDto getEmployeesDepartment(String pbo);


     List<HospitalDto> listAllHospitals();

     List<DepartmentDto> getDepartments(String pbb);
}
