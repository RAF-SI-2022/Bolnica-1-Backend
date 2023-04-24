package raf.bolnica1.employees.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.employees.dataGenerators.HospitalDepartmentGenerator;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.mappers.DepartmentMapper;
import raf.bolnica1.employees.mappers.HospitalMapper;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.EmployeesRoleRepository;
import raf.bolnica1.employees.repository.HospitalRepository;
import raf.bolnica1.employees.services.DepartmentService;
import raf.bolnica1.employees.services.impl.DepartmentServiceImpl;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private HospitalDepartmentGenerator hospitalDepartmentGenerator = HospitalDepartmentGenerator.getInstance();
    private ClassJsonComparator classJsonComparator = ClassJsonComparator.getInstance();
    private DepartmentService departmentService;

    private DepartmentMapper departmentMapper;
    private HospitalMapper hospitalMapper;

    private DepartmentRepository departmentRepository;
    private HospitalRepository hospitalRepository;
    private EmployeeRepository employeeRepository;
    private EmployeesRoleRepository employeesRoleRepository;

    @BeforeEach
    public void prepare(){
        hospitalMapper=new HospitalMapper();
        departmentMapper=new DepartmentMapper();

        departmentRepository = mock(DepartmentRepository.class);
        hospitalRepository = mock(HospitalRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        employeesRoleRepository = mock(EmployeesRoleRepository.class);

        departmentService = new DepartmentServiceImpl(departmentRepository, employeeRepository, hospitalRepository, employeesRoleRepository, hospitalMapper, departmentMapper);
    }

    @Test
    public void getAllDepartments(){

        List<Department> departments=hospitalDepartmentGenerator.getDepartments();
        /// Page<Department> page= new PageImpl<>(departments);

        given(departmentRepository.findAll()).willReturn(departments);
        List<DepartmentDto> returned = departmentService.listAllDepartments();

        System.out.println(returned);
        int count = 0;
        for(DepartmentDto departmentDto : returned){
            for(Department department : departments){
                if(departmentDto.getId().equals(department.getId())){
                    Assertions.assertTrue(classJsonComparator.compareCommonFields(department, departmentDto));
                    count++;
                }
            }
        }
        Assertions.assertEquals(count, departments.size());
    }

    @Test
    public void getAllDepForHospital(){
        Hospital hospital = hospitalDepartmentGenerator.getHospitals().get(RandomLong.getInstance().getLong((long)hospitalDepartmentGenerator.getHospitals().size()).intValue());
        List<Department> departments = new ArrayList<>();

        for(Department department : hospitalDepartmentGenerator.getDepartments()){
            if(department.getHospital().getPbb().equals(hospital.getPbb()))
                departments.add(department);
        }




    }

}
