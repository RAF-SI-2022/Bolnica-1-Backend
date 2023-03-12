package raf.bolnica1.employees.serviceTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Profession;
import raf.bolnica1.employees.domain.Title;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.services.impl.EmployeeServiceImpl;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployee_whenEmployeeDoesNotExist_shouldCreateNewEmployee() {
        String lbz = "12345";
        EmployeeCreateDto dto = EmployeeServiceTest.createEmployeeCreateDto();
        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.empty());
        when(employeeMapper.toEntity(dto)).thenReturn(employee);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.createEmployee(dto);

        assertNotNull(result);
        assertEquals(lbz, result.getLbz());
    }

    @Test
    void createEmployee_whenEmployeeAlreadyExists_shouldThrowException() {
        String lbz = "12345";
        EmployeeCreateDto dto = new EmployeeCreateDto();
        dto.setLbz(lbz);

        Employee employee = new Employee();
        employee.setLbz(lbz);

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));

        assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.createEmployee(dto));
    }

    @Test
    void findEmployeeInfo_whenEmployeeExists_shouldReturnEmployeeDto() {
        String lbz = "12345";
        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.findEmployeeInfo(lbz);

        assertNotNull(result);
        assertEquals(lbz, result.getLbz());
    }

    @Test
    void findEmployeeInfo_whenEmployeeDoesNotExist_shouldThrowException() {
        String lbz = "12345";

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeInfo(lbz));
    }

    @Test
    void softDeleteEmployee_whenEmployeeExists_shouldSetDeletedFlagAndReturnMessage() {
        String lbz = "12345";

        Employee employee = EmployeeServiceTest.createEmployee();

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeMessageDto result = employeeService.softDeleteEmployee(lbz);

        assertNotNull(result);
        assertEquals(String.format("Employee with lbz <%s> deleted", lbz), result.getMessage());
        assertTrue(employee.isDeleted());
        verify(employeeRepository).save(employee);
    }

    @Test
    void softDeleteEmployee_whenEmployeeDoesNotExist_shouldThrowException() {
        String lbz = "12345";

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.softDeleteEmployee(lbz));
    }

    // LIST WITH FILTERS
    @Test
    void listEmployeesWithFilters_whenDeletedIsNull_shouldReturnPageOfEmployeeDto() {
        String name = "John";
        String surname = "Doe";
        String deleted = null;
        String departmentName = "Department";
        String hospitalShortName = "ABC";
        int page = 0;
        int size = 2;

        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        Pageable pageable = PageRequest.of(page, size);
        PageImpl<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee), pageable, 1);

        when(employeeRepository.listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), departmentName.toLowerCase(), hospitalShortName.toLowerCase())).thenReturn(employeePage);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        Page<EmployeeDto> result = employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(employeeDto, result.getContent().get(0));
    }

    @Test
    void listEmployeesWithFilters_whenDeletedIsEmptyString_shouldReturnPageOfEmployeeDto() {
        String name = "John";
        String surname = "Doe";
        String deleted = "";
        String departmentName = "Department";
        String hospitalShortName = "ABC";
        int page = 0;
        int size = 2;

        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        Pageable pageable = PageRequest.of(page, size);
        PageImpl<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee), pageable, 1);

        when(employeeRepository.listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), departmentName.toLowerCase(), hospitalShortName.toLowerCase())).thenReturn(employeePage);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        Page<EmployeeDto> result = employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(employeeDto, result.getContent().get(0));
    }

    @Test
    void listEmployeesWithFilters_whenDeletedIsTrue_shouldReturnPageOfEmployeeDto() {
        String name = "John";
        String surname = "Doe";
        String deleted = "true";
        String departmentName = "Department";
        String hospitalShortName = "ABC";
        int page = 0;
        int size = 2;

        Employee employee = EmployeeServiceTest.createEmployee();

        Pageable pageable = PageRequest.of(page, size);
        PageImpl<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee), pageable, 1);

        when(employeeRepository.listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), true, departmentName.toLowerCase(), hospitalShortName.toLowerCase())).thenReturn(employeePage);
        when(employeeMapper.toDto(employee)).thenReturn(new EmployeeDto());

        Page<EmployeeDto> result = employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository).listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), true, departmentName.toLowerCase(), hospitalShortName.toLowerCase());
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void listEmployeesWithFilters_whenDeletedIsFalse_shouldReturnPageOfEmployeeDto() {
        String name = "John";
        String surname = "Doe";
        String deleted = "false";
        String departmentName = "Department";
        String hospitalShortName = "ABC";
        int page = 0;
        int size = 2;

        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        Pageable pageable = PageRequest.of(page, size);
        PageImpl<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee), pageable, 1);

        when(employeeRepository.listEmployeesWithFilters(pageable, name.toLowerCase(), surname.toLowerCase(), false, departmentName.toLowerCase(), hospitalShortName.toLowerCase())).thenReturn(employeePage);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        Page<EmployeeDto> result = employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(employeeDto, result.getContent().get(0));
    }

    @Test
    void listEmployeesWithFilters_whenDeletedIsInvalid_shouldThrowException() {
        String name = "John";
        String surname = "Doe";
        String deleted = "invalid";
        String departmentName = "Department";
        String hospitalShortName = "ABC";
        int page = 0;
        int size = 2;

        assertThrows(RuntimeException.class, () -> employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size));
    }

    // Edit employee info test
    @Test
    void editEmployeeInfo_whenEmployeeExistsAndOldPasswordMatches_shouldReturnEmployeeDto() {
        String lbz = "12345";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto();
        employeeUpdateDto.setOldPassword(oldPassword);
        employeeUpdateDto.setNewPassword(newPassword);
        employeeUpdateDto.setPhone(phone);

        Employee employee = EmployeeServiceTest.createEmployee();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLbz(lbz);

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(oldPassword, employee.getPassword())).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.editEmployeeInfo(employeeUpdateDto, lbz);

        assertNotNull(result);
        assertEquals(lbz, result.getLbz());
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void editEmployeeInfo_whenEmployeeDoesNotExist_shouldThrowException() {
        String lbz = "12345";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto();
        employeeUpdateDto.setOldPassword(oldPassword);
        employeeUpdateDto.setNewPassword(newPassword);
        employeeUpdateDto.setPhone(phone);

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.editEmployeeInfo(employeeUpdateDto, lbz));
    }

    @Test
    void editEmployeeInfo_whenOldPasswordDoesNotMatch_shouldThrowException() {
        String lbz = "12345";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto();
        employeeUpdateDto.setOldPassword(oldPassword);
        employeeUpdateDto.setNewPassword(newPassword);
        employeeUpdateDto.setPhone(phone);

        Employee employee = new Employee();
        employee.setLbz(lbz);
        employee.setPassword("differentPassword");

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(oldPassword, employee.getPassword())).thenReturn(false);

        assertThrows(EmployeePasswordException.class, () -> employeeService.editEmployeeInfo(employeeUpdateDto, lbz));
    }

    // Edit by Admin
    @Test
    void editEmployeeInfoByAdmin_whenEmployeeExists_shouldReturnEmployeeDto() {
        String lbz = "12345";

        EmployeeUpdateAdminDto employeeUpdateAdminDto = EmployeeServiceTest.createEmployeeUpdateByAdminDto();
        Employee employee = EmployeeServiceTest.createEmployee();
        Employee updatedEmployee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEntity(employeeUpdateAdminDto, employee)).thenReturn(updatedEmployee);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.toDto(updatedEmployee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto, lbz);

        assertNotNull(result);
        assertEquals(lbz, result.getLbz());
        verify(employeeRepository).save(updatedEmployee);
        verify(employeeMapper).toDto(updatedEmployee);
    }

    @Test
    void editEmployeeInfoByAdmin_whenEmployeeDoesNotExist_shouldThrowException() {
        String lbz = "12345";

        EmployeeUpdateAdminDto employeeUpdateAdminDto = EmployeeServiceTest.createEmployeeUpdateByAdminDto();

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto, lbz));
    }

    // Helpers
    public static Employee createEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setLbz("12345");
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setDateOfBirth(Date.valueOf("1990-01-01"));
        employee.setGender("Male");
        employee.setJmbg("1234567890123");
        employee.setAddress("123 Main St");
        employee.setPlaceOfLiving("Anytown");
        employee.setPhone("555-12345");
        employee.setEmail("johndoe@ibis.rs");
        employee.setUsername("johndoe");
        employee.setPassword("johndoe");
        employee.setEmail("johndoe@ibis.rs");
        employee.setTitle(Title.MR);
        employee.setProfession(Profession.MED_SESTRA);
        Department department = new Department();
        department.setName("Department");
        department.setId(1L);
        employee.setDepartment(department);
        return employee;
    }

    public static EmployeeCreateDto createEmployeeCreateDto(){
        EmployeeCreateDto dto = new EmployeeCreateDto();
        dto.setLbz("12345");
        dto.setName("John");
        dto.setSurname("Doe");
        dto.setDateOfBirth(Date.valueOf("1990-01-01"));
        dto.setGender("Male");
        dto.setJmbg("1234567890123");
        dto.setAddress("123 Main St");
        dto.setPlaceOfLiving("Anytown");
        dto.setPhone("555-12345");
        dto.setEmail("johndoe@ibis.rs");
        dto.setTitle(Title.MR);
        dto.setProfession(Profession.MED_SESTRA);
        dto.setDepartment(1L);
        return dto;
    }

    public static EmployeeDto createEmployeeDto(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setLbz("12345");
        employeeDto.setName("John");
        employeeDto.setSurname("Doe");
        employeeDto.setDateOfBirth(Date.valueOf("1990-01-01"));
        employeeDto.setGender("Male");
        employeeDto.setJmbg("1234567890123");
        employeeDto.setAddress("123 Main St");
        employeeDto.setPlaceOfLiving("Anytown");
        employeeDto.setPhone("555-12345");
        employeeDto.setEmail("johndoe@ibis.rs");
        employeeDto.setTitle(Title.MR);
        employeeDto.setProfession(Profession.MED_SESTRA);
        Department department = new Department();
        department.setId(1L);
        department.setName("Department");
        employeeDto.setDepartment(department);
        return employeeDto;
    }

    public static EmployeeUpdateDto createEmployeeUpdateDto(){
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setOldPassword("oldPassword");
        employeeUpdateDto.setNewPassword("newPassword");
        employeeUpdateDto.setPhone("1234567890");
        return employeeUpdateDto;
    }

    public static EmployeeUpdateAdminDto createEmployeeUpdateByAdminDto(){
        EmployeeUpdateAdminDto employeeUpdateAdminDto = new EmployeeUpdateAdminDto();
        employeeUpdateAdminDto.setName("John");
        employeeUpdateAdminDto.setSurname("Doe");
        employeeUpdateAdminDto.setDateOfBirth(Date.valueOf("1990-01-01"));
        employeeUpdateAdminDto.setGender("Male");
        employeeUpdateAdminDto.setJmbg("1234567890123");
        employeeUpdateAdminDto.setAddress("123 Main St");
        employeeUpdateAdminDto.setPlaceOfLiving("Anytown");
        employeeUpdateAdminDto.setPhone("555-12345");
        employeeUpdateAdminDto.setEmail("johndoe@ibis.rs");
        employeeUpdateAdminDto.setTitle(Title.MR);
        employeeUpdateAdminDto.setProfession(Profession.MED_SESTRA);
        Department department = new Department();
        department.setId(1L);
        department.setName("Department");
        employeeUpdateAdminDto.setDepartment(department);
        return employeeUpdateAdminDto;
    }

}
