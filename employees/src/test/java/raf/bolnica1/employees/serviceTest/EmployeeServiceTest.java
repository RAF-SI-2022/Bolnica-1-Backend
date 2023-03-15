package raf.bolnica1.employees.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.domain.constants.Title;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.RoleRepository;
import raf.bolnica1.employees.services.impl.EmployeeServiceImpl;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmployeesRoleRepository employeesRoleRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void beforeEach() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
                .thenReturn("Unknown message");
    }

    // Create employee
    @Test
    void createEmployee_whenEmployeeDoesNotExist_shouldCreateNewEmployee() {
        EmployeeCreateDto employeeCreateDto = EmployeeServiceTest.createEmployeeCreateDto();

        Employee employee = EmployeeServiceTest.createEmployee();

        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.empty());
        when(employeeMapper.toEntity(any(EmployeeCreateDto.class))).thenReturn(employee);
        when(passwordEncoder.encode(employeeCreateDto.getEmail().split("@")[0])).thenReturn("encodedPassword");
        when(employeeRepository.save(employee)).thenReturn(employee);

        Role role = new Role();
        role.setRoleShort(RoleShort.ROLE_DR_SPEC);
        when(roleRepository.findByRoleShort(RoleShort.ROLE_DR_SPEC)).thenReturn(Optional.of(role));

        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);


        EmployeeDto result = employeeService.createEmployee(employeeCreateDto);

        assertNotNull(result);
        assertEquals(EMPLOYEE_LBZ, result.getLbz());
        //assertEquals(result.getPermissions(), employeeCreateDto.getPermissions()); // Da li treba u dto da vratimo i permisije
        verify(employeesRoleRepository).save(any(EmployeesRole.class));
    }

    @Test
    void createEmployee_whenEmployeeAlreadyExists_shouldThrowException() {
        EmployeeCreateDto dto = EmployeeServiceTest.createEmployeeCreateDto();

        Employee employee = EmployeeServiceTest.createEmployee();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));

        assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.createEmployee(dto));
    }

    // Find employeeInfo
    @Test
    void findEmployeeInfo_whenEmployeeExists_shouldReturnEmployeeDto() {
        Employee employee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.findEmployeeInfo(EMPLOYEE_LBZ);

        assertNotNull(result);
        assertEquals(EMPLOYEE_LBZ, result.getLbz());
    }

    @Test
    void findEmployeeInfo_whenEmployeeDoesNotExist_shouldThrowException() {
        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployeeInfo(EMPLOYEE_LBZ));
    }

    // Soft delete employee
    @Test
    void softDeleteEmployee_whenEmployeeExists_shouldSetDeletedFlagAndReturnMessage() {

        Employee employee = EmployeeServiceTest.createEmployee();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeMessageDto result = employeeService.softDeleteEmployee(EMPLOYEE_LBZ);

        assertNotNull(result);
        assertEquals(String.format("Employee with lbz <%s> deleted", EMPLOYEE_LBZ), result.getMessage());
        assertTrue(employee.isDeleted());
        verify(employeeRepository).save(employee);
    }

    @Test
    void softDeleteEmployee_whenEmployeeDoesNotExist_shouldThrowException() {

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.softDeleteEmployee(EMPLOYEE_LBZ));
    }

    // List with filters
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

        when(employeeRepository.listEmployeesWithFilters(pageable, name, surname, departmentName, hospitalShortName)).thenReturn(employeePage);
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

        when(employeeRepository.listEmployeesWithFilters(pageable, name, surname, departmentName, hospitalShortName)).thenReturn(employeePage);
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

        when(employeeRepository.listEmployeesWithFilters(pageable, name, surname, true, departmentName, hospitalShortName)).thenReturn(employeePage);
        when(employeeMapper.toDto(employee)).thenReturn(new EmployeeDto());

        Page<EmployeeDto> result = employeeService.listEmployeesWithFilters(name, surname, deleted, departmentName, hospitalShortName, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository).listEmployeesWithFilters(pageable, name, surname, true, departmentName, hospitalShortName);
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

        when(employeeRepository.listEmployeesWithFilters(pageable, name, surname, false, departmentName, hospitalShortName)).thenReturn(employeePage);
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
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto(oldPassword, newPassword, phone);

        Employee employee = EmployeeServiceTest.createEmployee();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLbz(EMPLOYEE_LBZ);

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(oldPassword, employee.getPassword())).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.editEmployeeInfo(employeeUpdateDto, EMPLOYEE_LBZ);

        assertNotNull(result);
        assertEquals(EMPLOYEE_LBZ, result.getLbz());
        verify(employeeRepository, times(2)).save(employee);
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void editEmployeeInfo_whenEmployeeDoesNotExist_shouldThrowException() {
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto(oldPassword, newPassword, phone);

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.editEmployeeInfo(employeeUpdateDto, EMPLOYEE_LBZ));
    }

    @Test
    void editEmployeeInfo_whenOldPasswordDoesNotMatch_shouldThrowException() {
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String phone = "1234567890";

        EmployeeUpdateDto employeeUpdateDto = EmployeeServiceTest.createEmployeeUpdateDto(oldPassword, newPassword, phone);

        Employee employee = EmployeeServiceTest.createEmployee();
        employee.setPassword("differentPassword");

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(passwordEncoder.matches(oldPassword, employee.getPassword())).thenReturn(false);

        assertThrows(EmployeePasswordException.class, () -> employeeService.editEmployeeInfo(employeeUpdateDto, EMPLOYEE_LBZ));
    }

    // Edit by Admin
    @Test
    void editEmployeeInfoByAdmin_whenEmployeeExists_shouldReturnEmployeeDto() {
        EmployeeUpdateAdminDto employeeUpdateAdminDto = EmployeeServiceTest.createEmployeeUpdateByAdminDto();
        Employee employee = EmployeeServiceTest.createEmployee();
        Employee updatedEmployee = EmployeeServiceTest.createEmployee();
        EmployeeDto employeeDto = EmployeeServiceTest.createEmployeeDto();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEntity(employeeUpdateAdminDto, employee)).thenReturn(updatedEmployee);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.toDto(updatedEmployee)).thenReturn(employeeDto);

        Role role = new Role();
        role.setRoleShort(RoleShort.ROLE_DR_SPEC);
        when(roleRepository.findByRoleShort(RoleShort.ROLE_DR_SPEC)).thenReturn(Optional.of(role));
        when(employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto, EMPLOYEE_LBZ)).thenReturn(employeeDto);
        EmployeeDto result = employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto, EMPLOYEE_LBZ);

        assertNotNull(result);
        assertEquals(EMPLOYEE_LBZ, result.getLbz());
        verify(employeeRepository, times(2)).save(updatedEmployee);
        verify(employeeMapper).toDto(updatedEmployee);
    }

    @Test
    void editEmployeeInfoByAdmin_whenEmployeeDoesNotExist_shouldThrowException() {

        EmployeeUpdateAdminDto employeeUpdateAdminDto = EmployeeServiceTest.createEmployeeUpdateByAdminDto();

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto, EMPLOYEE_LBZ));
    }

    // password reset
    @Test
    public void passwordReset_whenEmployeeExistsAndOldPasswordMatches_shouldReturnEmployeeMessageDto() {
        // Arrange
        Employee employee = EmployeeServiceTest.createEmployee();
        when(passwordEncoder.encode("oldPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword2");
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.matches("newPassword", "encodedPassword2")).thenReturn(true);
        employee.setPassword(passwordEncoder.encode("oldPassword"));

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setOldPassword("oldPassword");
        passwordResetDto.setNewPassword("newPassword");

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        EmployeeMessageDto actualResult = employeeService.passwordReset(passwordResetDto, EMPLOYEE_LBZ);

        // Assert
        verify(employeeRepository).findByLbz(EMPLOYEE_LBZ);
        verify(employeeRepository).save(employee);

        String expectedPattern = String.format("http://localhost:8080/api/employee/password_reset/%s/[a-z0-9-]+", EMPLOYEE_LBZ);
        assertTrue(actualResult.getMessage().matches(expectedPattern));
        assertTrue(passwordEncoder.matches(passwordResetDto.getNewPassword(), employee.getNewPassword()));
    }

    @Test
    public void passwordReset_whenEmployeeExistsAndOldPasswordDoesntMatch_shouldThrowException() {
        // Arrange
        Employee employee = EmployeeServiceTest.createEmployee();
        when(passwordEncoder.encode("oldPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword2");
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.matches("newPassword", "encodedPassword2")).thenReturn(true);
        employee.setPassword(passwordEncoder.encode("oldPassword"));

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setOldPassword("oldPasswordNijeIsti");
        passwordResetDto.setNewPassword("newPassword");

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        assertThrows(EmployeePasswordException.class, () -> employeeService.passwordReset(passwordResetDto, EMPLOYEE_LBZ));
    }

    // password resetToken
    @Test
    public void passwordResetToken_whenEmployeeExistsAndConditionTrue_shouldReturnEmployeeDto() {
        String token = "token";

        Employee employee = EmployeeServiceTest.createEmployee();
        employee.setResetPassword("razlicitToken");

        Employee updatedEmployee = EmployeeServiceTest.createEmployee();
        updatedEmployee.setPassword("newPassword");

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);

        assertThrows(EmployeePasswordException.class, () -> employeeService.passwordResetToken(EMPLOYEE_LBZ, token));
    }

    @Test
    public void passwordResetToken_whenEmployeeExistsAndConditionFalse_shouldThrowException() {
        String token = "token";

        Employee employee = EmployeeServiceTest.createEmployee();
        employee.setResetPassword(token);

        Employee updatedEmployee = EmployeeServiceTest.createEmployee();
        updatedEmployee.setPassword("newPassword");

        when(employeeRepository.findByLbz(EMPLOYEE_LBZ)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);

        EmployeeDto result = employeeService.passwordResetToken(EMPLOYEE_LBZ, token);

        verify(employeeRepository).save(employee);
        assertEquals("NO", updatedEmployee.getResetPassword());
        assertEquals("NO", updatedEmployee.getNewPassword());
        assertEquals("newPassword", updatedEmployee.getPassword());

        verify(employeeMapper).toDto(updatedEmployee);

        EmployeeDto expectedDto = employeeMapper.toDto(employee);
        assertEquals(expectedDto, result);
    }


    // Helpers below

    private static final Long EMPLOYEE_ID = 1L;
    private static final String EMPLOYEE_LBZ = "12345";
    private static final String EMPLOYEE_NAME = "John";
    private static final String EMPLOYEE_SURNAME = "Doe";
    private static final String EMPLOYEE_DATE_OF_BIRTH = "1990-01-01";
    private static final String EMPLOYEE_GENDER = "Male";
    private static final String EMPLOYEE_JMBG = "1234567890123";
    private static final String EMPLOYEE_ADDRESS = "123 Main St";
    private static final String EMPLOYEE_PLACE_OF_LIVING = "Anytown";
    private static final String EMPLOYEE_PHONE = "555-12345";
    private static final String EMPLOYEE_EMAIL = "johndoe@ibis.rs";
    private static final String EMPLOYEE_USERNAME = "johndoe";
    private static final String EMPLOYEE_PASSWORD = "johndoe";
    private static final Title EMPLOYEE_TITLE = Title.MR;
    private static final Profession EMPLOYEE_PROFESSION = Profession.MED_SESTRA;
    private static final List<String> EMPLOYEE_PERMISSIONS;

    private static final Department DEPARTMENT;
    private static final Long DEPARTMENT_ID = 1L;
    private static final String DEPARTMENT_PBO = "DEPT-2022-001";
    private static final String DEPARTMENT_NAME = "Department";

    private static final Hospital HOSPITAL;
    private static final Long HOSPITAL_ID = 1L;
    private static final String HOSPITAL_FULLNAME = "Hospital1";
    private static final String HOSPITAL_SHORTNAME = "H1";

    static {
        HOSPITAL = new Hospital();
        HOSPITAL.setId(HOSPITAL_ID);
        HOSPITAL.setFullName(HOSPITAL_FULLNAME);
        HOSPITAL.setShortName(HOSPITAL_SHORTNAME);

        DEPARTMENT = new Department();
        DEPARTMENT.setId(DEPARTMENT_ID);
        DEPARTMENT.setPbo(DEPARTMENT_PBO);
        DEPARTMENT.setName(DEPARTMENT_NAME);
        DEPARTMENT.setHospital(HOSPITAL);

        EMPLOYEE_PERMISSIONS = new ArrayList<>();
        EMPLOYEE_PERMISSIONS.add(RoleShort.ROLE_DR_SPEC.name());
    }

    private static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setLbz(EMPLOYEE_LBZ);
        employee.setName(EMPLOYEE_NAME);
        employee.setSurname(EMPLOYEE_SURNAME);
        employee.setDateOfBirth(Date.valueOf(EMPLOYEE_DATE_OF_BIRTH));
        employee.setGender(EMPLOYEE_GENDER);
        employee.setJmbg(EMPLOYEE_JMBG);
        employee.setAddress(EMPLOYEE_ADDRESS);
        employee.setPlaceOfLiving(EMPLOYEE_PLACE_OF_LIVING);
        employee.setPhone(EMPLOYEE_PHONE);
        employee.setEmail(EMPLOYEE_EMAIL);
        employee.setUsername(EMPLOYEE_USERNAME);
        employee.setPassword(EMPLOYEE_PASSWORD);
        employee.setTitle(EMPLOYEE_TITLE);
        employee.setProfession(EMPLOYEE_PROFESSION);
        employee.setDepartment(DEPARTMENT);
        return employee;
    }

    private static EmployeeCreateDto createEmployeeCreateDto() {
        EmployeeCreateDto dto = new EmployeeCreateDto();
        dto.setLbz(EMPLOYEE_LBZ);
        dto.setName(EMPLOYEE_NAME);
        dto.setSurname(EMPLOYEE_SURNAME);
        dto.setDateOfBirth(Date.valueOf(EMPLOYEE_DATE_OF_BIRTH));
        dto.setGender(EMPLOYEE_GENDER);
        dto.setJmbg(EMPLOYEE_JMBG);
        dto.setAddress(EMPLOYEE_ADDRESS);
        dto.setPlaceOfLiving(EMPLOYEE_PLACE_OF_LIVING);
        dto.setPhone(EMPLOYEE_PHONE);
        dto.setEmail(EMPLOYEE_EMAIL);
        dto.setTitle(EMPLOYEE_TITLE);
        dto.setProfession(EMPLOYEE_PROFESSION);
        dto.setDepartmentPbo(DEPARTMENT.getPbo());
        dto.setPermissions(EMPLOYEE_PERMISSIONS);
        return dto;
    }

    private static EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(EMPLOYEE_ID);
        employeeDto.setLbz(EMPLOYEE_LBZ);
        employeeDto.setName(EMPLOYEE_NAME);
        employeeDto.setSurname(EMPLOYEE_SURNAME);
        employeeDto.setDateOfBirth(Date.valueOf(EMPLOYEE_DATE_OF_BIRTH));
        employeeDto.setGender(EMPLOYEE_GENDER);
        employeeDto.setJmbg(EMPLOYEE_JMBG);
        employeeDto.setAddress(EMPLOYEE_ADDRESS);
        employeeDto.setPlaceOfLiving(EMPLOYEE_PLACE_OF_LIVING);
        employeeDto.setPhone(EMPLOYEE_PHONE);
        employeeDto.setEmail(EMPLOYEE_EMAIL);
        employeeDto.setTitle(EMPLOYEE_TITLE);
        employeeDto.setProfession(EMPLOYEE_PROFESSION);
        employeeDto.setDepartment(DEPARTMENT);
        return employeeDto;
    }

    private static EmployeeUpdateDto createEmployeeUpdateDto(String oldPassword, String newPassword, String phone) {
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setOldPassword(oldPassword);
        employeeUpdateDto.setNewPassword(newPassword);
        employeeUpdateDto.setPhone(phone);
        return employeeUpdateDto;
    }

    private static EmployeeUpdateAdminDto createEmployeeUpdateByAdminDto() {
        EmployeeUpdateAdminDto employeeUpdateAdminDto = new EmployeeUpdateAdminDto();
        employeeUpdateAdminDto.setName(EMPLOYEE_NAME);
        employeeUpdateAdminDto.setSurname(EMPLOYEE_SURNAME);
        employeeUpdateAdminDto.setDateOfBirth(Date.valueOf(EMPLOYEE_DATE_OF_BIRTH));
        employeeUpdateAdminDto.setGender(EMPLOYEE_GENDER);
        employeeUpdateAdminDto.setJmbg(EMPLOYEE_JMBG);
        employeeUpdateAdminDto.setAddress(EMPLOYEE_ADDRESS);
        employeeUpdateAdminDto.setPlaceOfLiving(EMPLOYEE_PLACE_OF_LIVING);
        employeeUpdateAdminDto.setPhone(EMPLOYEE_PHONE);
        employeeUpdateAdminDto.setEmail(EMPLOYEE_EMAIL);
        employeeUpdateAdminDto.setTitle(EMPLOYEE_TITLE);
        employeeUpdateAdminDto.setProfession(EMPLOYEE_PROFESSION);
        employeeUpdateAdminDto.setDepartmentPbo(DEPARTMENT.getPbo());
        employeeUpdateAdminDto.setPermissions(EMPLOYEE_PERMISSIONS);
        return employeeUpdateAdminDto;
    }

}
