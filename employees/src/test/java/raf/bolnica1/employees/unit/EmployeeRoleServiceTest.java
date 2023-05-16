package raf.bolnica1.employees.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.employees.dataGenerators.domain.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.domain.HospitalDepartmentGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeCreateDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateAdminDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateDtoGenerator;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;
import raf.bolnica1.employees.domain.Role;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.role.RoleDto;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.services.EmployeeRoleService;
import raf.bolnica1.employees.services.impl.EmployeeRoleServiceImpl;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class EmployeeRoleServiceTest {

    private EmployeeGenerator employeeGenerator=EmployeeGenerator.getInstance();
    private EmployeeCreateDtoGenerator employeeCreateDtoGenerator=EmployeeCreateDtoGenerator.getInstance();
    private HospitalDepartmentGenerator hospitalDepartmentGenerator=HospitalDepartmentGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();
    private EmployeeUpdateDtoGenerator employeeUpdateDtoGenerator=EmployeeUpdateDtoGenerator.getInstance();
    private EmployeeUpdateAdminDtoGenerator employeeUpdateAdminDtoGenerator=EmployeeUpdateAdminDtoGenerator.getInstance();
    private RandomLong randomLong=RandomLong.getInstance();

    private EmployeeRepository employeeRepository;
    private EmployeesRoleRepository employeesRoleRepository;
    private EmployeeRoleService employeeRoleService;

    @BeforeEach
    public void prepare(){
        employeeRepository=mock(EmployeeRepository.class);
        employeesRoleRepository=mock(EmployeesRoleRepository.class);
        employeeRoleService=new EmployeeRoleServiceImpl(employeeRepository,employeesRoleRepository);
    }

    @Test
    public void privilegeForEmployeeTest(){

        hospitalDepartmentGenerator.fill();
        Department department=hospitalDepartmentGenerator.getRandomDepartment();
        employeeGenerator.fill(department);

        Employee employee = employeeGenerator.getRandomEmployee();
        String lbz="mojLbz";

        given(employeeRepository.findByLbzLock(lbz)).willReturn(Optional.ofNullable(employee));

        int roleCount=5;
        List<EmployeesRole>employeesRoles=new ArrayList<>();
        for(int i=0;i<roleCount;i++){
            Role pom=new Role();
            pom.setId((long)i);
            pom.setRoleShort(RoleShort.values()[ randomLong.getLong((long)RoleShort.values().length).intValue() ]);
            pom.setName(pom.getRoleShort().name());
            EmployeesRole pom2=new EmployeesRole();
            pom2.setEmployee(employee);
            pom2.setRole(pom);
            employeesRoles.add(pom2);
        }

        given(employeesRoleRepository.findByEmployee(employee)).willReturn(employeesRoles);

        List<RoleDto> ret=employeeRoleService.privilegeForEmployee(lbz);

        Assertions.assertTrue(ret.size()==employeesRoles.size());
        for(int i=0;i<ret.size();i++){
            Assertions.assertTrue(classJsonComparator.compareCommonFields(ret.get(i),
                    employeesRoles.get(i).getRole()));
        }

    }

}
