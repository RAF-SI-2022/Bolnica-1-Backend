package raf.bolnica1.employees.services;

import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;

import java.util.List;

public interface DepartmentService {

     List<DepartmentDto> listAllDepartments();

     List<HospitalDto> listAllHospitals();
}
