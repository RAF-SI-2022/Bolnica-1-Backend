package raf.bolnica1.employees.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.department.HospitalDto;
import raf.bolnica1.employees.services.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;


    @GetMapping("/employee/{lbz}")
    public ResponseEntity<Long> findDepartmentIdByLbz(@PathVariable("lbz")String lbz){
        return new ResponseEntity<>(departmentService.findDepartmentIdByLbz(lbz),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return new ResponseEntity<>(departmentService.listAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{pbo}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable String pbo) {
        return new ResponseEntity<>(departmentService.getEmployeesDepartment(pbo), HttpStatus.OK);
    }

    @GetMapping("/hospital/{pbb}")
    public ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable String pbb){
        return new ResponseEntity<>(departmentService.getDepartments(pbb), HttpStatus.OK);
    }

    @GetMapping("/hospital")
    public ResponseEntity<List<HospitalDto>> getAllHospitals() {
        return new ResponseEntity<>(departmentService.listAllHospitals(), HttpStatus.OK);
    }
}
