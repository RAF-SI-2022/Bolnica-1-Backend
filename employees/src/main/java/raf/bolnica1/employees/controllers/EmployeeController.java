package raf.bolnica1.employees.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.services.EmployeeService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
        return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.CREATED);
    }

    //pen - personal employee number
    @PutMapping(path = "/edit/{lbz}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> editEmployeeInfo(@PathVariable String lbz, @Valid @RequestBody EmployeeUpdateDto dto) {
        // aspect
        return new ResponseEntity<>(employeeService.editEmployeeInfo(dto, lbz), HttpStatus.OK);
    }

    @PutMapping(path = "/edit/admin/{lbz}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> editEmployeeInfoByAdmin(@PathVariable String lbz, @Valid @RequestBody EmployeeUpdateAdminDto dto) {
        // aspect
        return new ResponseEntity<>(employeeService.editEmployeeInfoByAdmin(dto, lbz), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{lbz}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeMessageDto> softDeleteEmployee(@PathVariable String lbz) {
        return new ResponseEntity<>(employeeService.softDeleteEmployee(lbz), HttpStatus.OK);
    }

    @PutMapping(path = "/password_reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> passwordReset(@Valid @RequestBody Object object) {
        return null;
    }

    @GetMapping(path = "/find/{lbz}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findEmployeeInfo(@PathVariable String lbz, @RequestHeader("Authorization") String authorization) {
        // unpack token
        // aspect
        return new ResponseEntity<>(employeeService.findEmployeeInfo(lbz), HttpStatus.FOUND);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<EmployeeDto>> listEmployeesWithFilters(@RequestParam String name,
                                                         @RequestParam (required= false) String surname,
                                                         @RequestParam (required= false) String deleted,
                                                         @RequestParam (required= false) String departmentName,
                                                         @RequestParam (required= false) String hospitalShortName,
                                                         @RequestParam (defaultValue = "0") Integer page,
                                                         @RequestParam (defaultValue = "2") Integer size) {
        // System.out.println("name = " + name + "\n surname = " + surname + "\n deleted = " + deleted + "\n departmentName = " + departmentName + "\n hospitalShortName = " + hospitalShortName + "\n page = " + page + "\n size = " + size + "\n");
        return new ResponseEntity<>(employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size), HttpStatus.OK);
    }
}
