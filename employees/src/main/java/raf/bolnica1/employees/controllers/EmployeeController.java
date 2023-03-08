package raf.bolnica1.employees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.employees.services.EmployeeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Object object) {
        return null;
    }

    //pen - personal employee number
    @PutMapping(path = "/edit/{pen}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editEmployeeInfo(@PathVariable Long pen, @Valid @RequestBody Object object) {
        return null;
    }

    @DeleteMapping(path = "/delete/{pen}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> softDeleteEmployee(@PathVariable Long pen) {
        return null;
    }

    @PutMapping(path = "/password_reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> passwordReset(@Valid @RequestBody Object object) {
        return null;
    }

    @GetMapping(path = "/find/{pen}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findEmployeeInfo(@PathVariable Long pen,@Valid @RequestBody Object object) {
        return null;
    }

    //potrebna paginacija
    @GetMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listEmployees(@Valid @RequestBody Object object,@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer size) {
        return null;
    }
}
