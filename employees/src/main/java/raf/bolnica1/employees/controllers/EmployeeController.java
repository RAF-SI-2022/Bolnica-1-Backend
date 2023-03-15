package raf.bolnica1.employees.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.dto.role.RoleDto;
import raf.bolnica1.employees.services.EmployeeRoleService;
import raf.bolnica1.employees.services.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeRoleService employeeRoleService;

    @PostMapping(headers = {"Authorization"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(dto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    //pen - personal employee number
    @PutMapping(path = "/edit/{lbz}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeDto> editEmployeeInfo(@PathVariable String lbz, @Valid @RequestBody EmployeeUpdateDto dto) {
        return new ResponseEntity<>(employeeService.editEmployeeInfo(dto, lbz), HttpStatus.OK);
    }

    @PutMapping(path = "/edit/admin/{lbz}")
    public ResponseEntity<EmployeeDto> editEmployeeInfoByAdmin(@PathVariable String lbz, @Valid @RequestBody EmployeeUpdateAdminDto dto) {
        return new ResponseEntity<>(employeeService.editEmployeeInfoByAdmin(dto, lbz), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{lbz}")
    public ResponseEntity<EmployeeMessageDto> softDeleteEmployee(@PathVariable String lbz) {
        return new ResponseEntity<>(employeeService.softDeleteEmployee(lbz), HttpStatus.OK);
    }

    @PutMapping(path = "/password_reset/{lbz}")
    public ResponseEntity<EmployeeMessageDto> passwordReset(@Valid @RequestBody PasswordResetDto passwordResetDto, @PathVariable String lbz) {
        return new ResponseEntity<>(employeeService.passwordReset(passwordResetDto, lbz), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/password_reset/{lbz}/{token}")
    public ResponseEntity<EmployeeDto> passwordResetToken(@PathVariable("lbz") String lbz, @PathVariable("token") String token) {
        return new ResponseEntity<>(employeeService.passwordResetToken(lbz, token), HttpStatus.OK);
    }

    @GetMapping(path = "/find/{lbz}")
    public ResponseEntity<EmployeeDto> findEmployeeInfo(@PathVariable String lbz) {
        return new ResponseEntity<>(employeeService.findEmployeeInfo(lbz), HttpStatus.FOUND);
    }

    @GetMapping(path = "/admin/find/{lbz}")
    public ResponseEntity<EmployeeDto> findEmployeeInfoByAdmin(@PathVariable String lbz) {
        return new ResponseEntity<>(employeeService.findEmployeeInfo(lbz), HttpStatus.FOUND);
    }

    @GetMapping(path = "/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeDto>> listEmployeesWithFilters(
            @RequestParam String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String deleted,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String hospitalShortName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size) {
        return new ResponseEntity<>(employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size), HttpStatus.OK);
    }

    @GetMapping("/permissions/{lbz}")
    public ResponseEntity<List<RoleDto>> getAllEmployeePermissions(@PathVariable String lbz) {
        return new ResponseEntity<>(employeeRoleService.privilegeForEmployee(lbz), HttpStatus.OK);
    }
}
