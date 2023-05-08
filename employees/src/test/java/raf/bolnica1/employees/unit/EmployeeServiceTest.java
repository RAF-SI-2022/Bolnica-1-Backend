package raf.bolnica1.employees.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import raf.bolnica1.employees.dataGenerators.domain.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.domain.HospitalDepartmentGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeCreateDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateAdminDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateDtoGenerator;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;
import raf.bolnica1.employees.domain.Role;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.employee.*;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.mappers.DepartmentMapper;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.RoleRepository;
import raf.bolnica1.employees.security.util.JwtUtils;
import raf.bolnica1.employees.services.EmployeeService;
import raf.bolnica1.employees.services.impl.EmployeeServiceImpl;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private EmployeeGenerator employeeGenerator=EmployeeGenerator.getInstance();
    private EmployeeCreateDtoGenerator employeeCreateDtoGenerator=EmployeeCreateDtoGenerator.getInstance();
    private HospitalDepartmentGenerator hospitalDepartmentGenerator=HospitalDepartmentGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private EmployeeUpdateDtoGenerator employeeUpdateDtoGenerator=EmployeeUpdateDtoGenerator.getInstance();
    private EmployeeUpdateAdminDtoGenerator employeeUpdateAdminDtoGenerator=EmployeeUpdateAdminDtoGenerator.getInstance();


    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeMapper employeeMapper;
    private DepartmentMapper departmentMapper;
    private EmployeesRoleRepository employeesRoleRepository;
    private RoleRepository roleRepository;
    private MessageSource messageSource;
    private JwtUtils jwtUtils;

    private EmployeeService employeeService;

    @BeforeEach
    public void prepare(){
        departmentMapper=new DepartmentMapper();
        passwordEncoder=mock(PasswordEncoder.class);
        employeeRepository=mock(EmployeeRepository.class);
        departmentRepository=mock(DepartmentRepository.class);
        employeeMapper=new EmployeeMapper(departmentRepository,departmentMapper);
        employeesRoleRepository=mock(EmployeesRoleRepository.class);
        roleRepository=mock(RoleRepository.class);
        messageSource=mock(MessageSource.class);
        jwtUtils=mock(JwtUtils.class);
        employeeService=new EmployeeServiceImpl(passwordEncoder,employeeRepository,employeeMapper,employeesRoleRepository,
                roleRepository,messageSource,jwtUtils);
    }


    private List<Role> roles;
    private void insertRoles(List<String> list){
        roles=new ArrayList<>();
        int i=0;
        for(String pom:list) {
            Role role=new Role();
            role.setId((long)i);
            role.setName(pom);
            role.setRoleShort(RoleShort.valueOf(pom));
            roles.add(role);
            given(roleRepository.findByRoleShort(RoleShort.valueOf(pom))).willReturn(Optional.of(role));
            i++;
        }
    }

    @Test
    public void createEmployeeTest(){

        Long employeeId=3L;

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        given(departmentRepository.findByPbo(department.getPbo())).willReturn(Optional.of(department));

        employeeCreateDtoGenerator.fill(department.getPbo());
        EmployeeCreateDto employeeCreateDto=employeeCreateDtoGenerator.getRandomEmployee();

        insertRoles(employeeCreateDto.getPermissions());

        given(employeeRepository.findByLbz(employeeCreateDto.getLbz())).willReturn(Optional.ofNullable(null));

        Employee employee=employeeMapper.toEntity(employeeCreateDto);
        String mojPassword="mojPassword";
        given(passwordEncoder.encode(any())).willReturn(mojPassword);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setId(employeeId);
        given(employeeRepository.save(any())).willReturn(employee);

        EmployeeDto employeeDto=employeeService.createEmployee(employeeCreateDto);

        assertTrue(classJsonComparator.compareCommonFields(employeeDto,employeeCreateDto));

        ArgumentCaptor<EmployeesRole> employeesRoleArgumentCaptor=ArgumentCaptor.forClass(EmployeesRole.class);
        verify(employeesRoleRepository).save(employeesRoleArgumentCaptor.capture());

        assertTrue(employeeCreateDto.getPermissions().size()==employeesRoleArgumentCaptor.getAllValues().size());
        for(int i=0;i<employeeCreateDto.getPermissions().size();i++){
            String pom1=employeeCreateDto.getPermissions().get(i);
            RoleShort pom2=employeesRoleArgumentCaptor.getAllValues().get(i).getRole().getRoleShort();
            assertEquals(pom1, pom2.name());
        }

    }


    @Test
    public void findEmployeeInfoTest(){

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        Employee employee=employeeGenerator.getRandomEmployee();

        given(employeeRepository.findByLbz(employee.getLbz())).willReturn(Optional.of(employee));

        EmployeeDto ret=employeeService.findEmployeeInfo(employee.getLbz());

        assertTrue(classJsonComparator.compareCommonFields(employee.getDepartment(),ret.getDepartment()));
        employee.setDepartment(null);
        ret.setDepartment(null);
        assertTrue(classJsonComparator.compareCommonFields(ret,employee));

    }

    @Test
    public void softDeleteEmployeeTest(){

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        Employee employee=employeeGenerator.getRandomEmployee();

        given(employeeRepository.findByLbz(employee.getLbz())).willReturn(Optional.of(employee));

        employeeService.softDeleteEmployee(employee.getLbz());

        ArgumentCaptor<Employee> arg=ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(arg.capture());

        assertTrue(arg.getValue().isDeleted());
        arg.getValue().setDeleted(false);
        assertTrue(classJsonComparator.compareCommonFields(arg.getValue(),employee));

    }



    private Employee replicateEmployee(Employee employee){
        Employee ret=new Employee();
        ret.setDeleted(employee.isDeleted());
        ret.setLbz(employee.getLbz());
        ret.setEmail(employee.getEmail());
        ret.setPassword(employee.getPassword());
        ret.setPhone(employee.getPhone());
        ret.setId(employee.getId());
        ret.setAddress(employee.getAddress());
        ret.setGender(employee.getGender());
        ret.setName(employee.getName());
        ret.setProfession(employee.getProfession());
        ret.setSurname(employee.getSurname());
        ret.setDepartment(employee.getDepartment());
        ret.setDateOfBirth(employee.getDateOfBirth());
        ret.setPlaceOfLiving(employee.getPlaceOfLiving());
        ret.setTitle(employee.getTitle());
        ret.setJmbg(employee.getJmbg());
        ret.setUsername(employee.getUsername());
        ret.setResetPassword(employee.getResetPassword());

        return ret;
    }
    @Test
    public void passwordReset_whenEmployeeExistsAndOldPasswordMatches_shouldReturnEmployeeMessageDto() {
        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        // Arrange
        Employee employee = employeeGenerator.getRandomEmployee();
        String lbz=employee.getLbz();
        when(passwordEncoder.encode("oldPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword2");
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.matches("newPassword", "encodedPassword2")).thenReturn(true);
        employee.setPassword(passwordEncoder.encode("oldPassword"));

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setOldPassword("oldPassword");
        passwordResetDto.setNewPassword("newPassword");

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        EmployeeMessageDto actualResult = employeeService.passwordReset(passwordResetDto, lbz);

        // Assert
        verify(employeeRepository).findByLbz(lbz);
        verify(employeeRepository).save(employee);

        assertTrue(passwordEncoder.matches(passwordResetDto.getNewPassword(), employee.getNewPassword()));
    }

    @Test
    public void passwordReset_whenEmployeeExistsAndOldPasswordDoesntMatch_shouldThrowException() {
        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        // Arrange
        Employee employee = employeeGenerator.getRandomEmployee();
        String lbz=employee.getLbz();
        when(passwordEncoder.matches("oldPasswordNijeIsti","oldPassword")).thenReturn(false);
        employee.setPassword("oldPassword");

        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setOldPassword("oldPasswordNijeIsti");
        passwordResetDto.setNewPassword("newPassword");

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));

        assertThrows(EmployeePasswordException.class, () -> employeeService.passwordReset(passwordResetDto, lbz));
    }

    // password resetToken
    @Test
    public void passwordResetToken_whenEmployeeExistsAndConditionTrue_shouldReturnEmployeeDto() {
        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        String token = "token";

        Employee employee = employeeGenerator.getRandomEmployee();
        employee.setResetPassword("razlicitToken");

        String lbz=employee.getLbz();

        Employee updatedEmployee =  replicateEmployee(employee);
        updatedEmployee.setPassword("newPassword");

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));

        assertThrows(EmployeePasswordException.class, () -> employeeService.passwordResetToken(lbz, token));
    }

    @Test
    public void passwordResetToken_whenEmployeeExistsAndConditionFalse_shouldThrowException() {
        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);
        String token = "token";

        Employee employee = employeeGenerator.getRandomEmployee();
        employee.setResetPassword(token);

        String lbz=employee.getLbz();

        Employee updatedEmployee = replicateEmployee(employee);
        assertTrue(classJsonComparator.compareCommonFields(employee,updatedEmployee));
        updatedEmployee.setPassword("newPassword");

        when(employeeRepository.findByLbz(lbz)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);

        EmployeeDto result = employeeService.passwordResetToken(lbz, token);

        verify(employeeRepository).save(employee);
        assertEquals("NO", employee.getResetPassword());
        assertEquals("NO", employee.getNewPassword());
        assertEquals("newPassword", updatedEmployee.getPassword());

    }


    @Test
    public void listEmployeesWithFiltersTest(){

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);

        Employee employee = employeeGenerator.getRandomEmployee();
        List<Employee> employees=new ArrayList<>();
        employees.add(employee);
        Page<Employee> page=new PageImpl<>(employees);

        given(employeeRepository
                .listEmployeesWithFilters(any(), eq(employee.getName()), eq(employee.getSurname()),
                        eq(true), eq(department.getName()), eq(department.getHospital().getShortName()) ))
                .willReturn(page);

        Page<EmployeeDto> ret=employeeService.listEmployeesWithFilters(employee.getName(),employee.getSurname(),"true",
                department.getName(),department.getHospital().getShortName(),0,2);


        Assertions.assertTrue(ret.getContent().size()==employees.size());
        for(int i=0;i<employees.size();i++) {
            Assertions.assertTrue(classJsonComparator.compareCommonFields(employees.get(i).getDepartment(),
                    ret.getContent().get(i).getDepartment()));
            employees.get(i).setDepartment(null);
            ret.getContent().get(i).setDepartment(null);
            Assertions.assertTrue(classJsonComparator.compareCommonFields(employees.get(i), ret.getContent().get(i)));
        }

    }


    @Test
    public void editEmployeeInfoTest(){

        employeeUpdateDtoGenerator.fill();

        String lbz="mojLbz";

        EmployeeUpdateDto employeeUpdateDto=employeeUpdateDtoGenerator.getRandomEmployee();
        Employee employee=new Employee();
        employee.setPhone("");
        employee.setPassword(employeeUpdateDto.getOldPassword());

        given(passwordEncoder.matches(employeeUpdateDto.getOldPassword(),employeeUpdateDto.getOldPassword())).willReturn(true);
        given(employeeRepository.findByLbz(lbz)).willReturn(Optional.of(employee));

        employeeService.editEmployeeInfo(employeeUpdateDto,lbz);

        Assertions.assertTrue(employee.getPhone().equals(employeeUpdateDto.getPhone()));
    }


    @Test
    public void editEmployeeInfoByAdminTest(){

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();

        employeeUpdateAdminDtoGenerator.fill(department.getPbo());
        EmployeeUpdateAdminDto employeeUpdateAdminDto=employeeUpdateAdminDtoGenerator.getRandomEmployee();

        insertRoles(employeeUpdateAdminDto.getPermissions());

        String lbz="mojLbz";

        Employee employee=new Employee();
        employee.setId(3L);
        given(employeeRepository.findByLbz(lbz)).willReturn(Optional.of(employee));
        given(departmentRepository.findByPbo(department.getPbo())).willReturn(Optional.of(department));
       /// given(passwordEncoder.encode(any())).willReturn("noviPassword");
        given(employeeRepository.save(employee)).willReturn(employee);

        EmployeeDto ret=employeeService.editEmployeeInfoByAdmin(employeeUpdateAdminDto,lbz);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,employeeUpdateAdminDto));

        ArgumentCaptor<EmployeesRole> employeesRoleArgumentCaptor=ArgumentCaptor.forClass(EmployeesRole.class);
        verify(employeesRoleRepository).save(employeesRoleArgumentCaptor.capture());

        assertTrue(employeeUpdateAdminDto.getPermissions().size()==employeesRoleArgumentCaptor.getAllValues().size());
        for(int i=0;i<employeeUpdateAdminDto.getPermissions().size();i++){
            String pom1=employeeUpdateAdminDto.getPermissions().get(i);
            RoleShort pom2=employeesRoleArgumentCaptor.getAllValues().get(i).getRole().getRoleShort();
            System.out.println(pom1+" "+pom2.name()+"  ROLES");
            assertEquals(pom1, pom2.name());
        }

    }

    @Test
    public void findDoctorSpecialistsByDepartmentTest(){

        int employeeCount=5;

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();

        employeeGenerator.fill(department);
        List<EmployeesRole>employeesRoles=new ArrayList<>();
        for(int i=0;i<employeeCount;i++){
            EmployeesRole pom=new EmployeesRole();
            pom.setEmployee(employeeGenerator.getRandomEmployee());
            employeesRoles.add(pom);
        }

        given(employeeRepository.listDoctorsSpecialistsByDepartment(department.getPbo())).willReturn(Optional.of(employeesRoles));

        List<EmployeeDto>ret=employeeService.findDoctorSpecialistsByDepartment(department.getPbo());
        List<Employee>ret2=new ArrayList<>();
        for(EmployeesRole er:employeesRoles) {
            ret2.add(er.getEmployee());
        }

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret.get(0).getDepartment(),ret2.get(0).getDepartment()));
        Assertions.assertTrue(ret.size()==ret2.size());
        for(int i=0;i<ret.size();i++){

            ret.get(i).setDepartment(null);
            ret2.get(i).setDepartment(null);
            Assertions.assertTrue(classJsonComparator.compareCommonFields(ret.get(i),ret2.get(i)));
        }

    }

}
