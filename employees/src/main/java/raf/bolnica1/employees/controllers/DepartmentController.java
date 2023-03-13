package raf.bolnica1.employees.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.services.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll(){
        return new ResponseEntity<>(departmentService.listAllDepartments(), HttpStatus.OK);
    }
}
