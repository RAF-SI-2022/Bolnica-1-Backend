package raf.bolnica1.employees.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.mappers.DepartmentMapper;
import raf.bolnica1.employees.mappers.EmployeeMapper;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.RoleRepository;
import raf.bolnica1.employees.security.util.JwtUtils;
import raf.bolnica1.employees.services.EmployeeService;
import raf.bolnica1.employees.services.impl.EmployeeServiceImpl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest2 {

    private EmployeeGenerator employeeGenerator=EmployeeGenerator.getInstance();


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


    @Test
    public void createEmployeeTest(){

        /*Employee employee=employeeGenerator.getRandomEmployee();

        given()*/

    }


}
