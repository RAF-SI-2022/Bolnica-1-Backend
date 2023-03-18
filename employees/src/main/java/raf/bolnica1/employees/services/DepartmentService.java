package raf.bolnica1.employees.services;

import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> listAllDepartments();

    DepartmentDto getEmployeesDepartment(String pbo);

<<<<<<< HEAD
     List<HospitalDto> listAllHospitals();

     List<DepartmentDto> getDepartments(String pbb);
=======
    List<HospitalDto> listAllHospitals();
>>>>>>> 07cda1985b946f47ae423802e15156289d7b9190
}
