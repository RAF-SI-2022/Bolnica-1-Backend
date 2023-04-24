package raf.bolnica1.employees.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.employees.dataGenerators.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.HospitalDepartmentGenerator;
import raf.bolnica1.employees.dataGenerators.primitives.RandomLong;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.employee.DoctorDepartmentDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private HospitalDepartmentGenerator hospitalDepartmentGenerator = HospitalDepartmentGenerator.getInstance();
    private EmployeeGenerator employeeGenerator = EmployeeGenerator.getInstance();
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

    private void checkDepartments(List<Department> departments, List<DepartmentDto> returned){
        int count = 0;
        for(DepartmentDto departmentDto : returned){
            for(Department department : departments){
                if(departmentDto.getId().equals(department.getId())){
                    Assertions.assertTrue(classJsonComparator.compareCommonFields(department, departmentDto));
                    Assertions.assertEquals(departmentDto.getHospitalName(), department.getHospital().getShortName());
                    count++;
                }
            }
        }
        Assertions.assertEquals(count, departments.size());
    }

    @Test
    public void getAllDepartments(){

        hospitalDepartmentGenerator.fill();

        List<Department> departments=hospitalDepartmentGenerator.getDepartments();
        /// Page<Department> page= new PageImpl<>(departments);

        given(departmentRepository.findAll()).willReturn(departments);
        List<DepartmentDto> returned = departmentService.listAllDepartments();

        checkDepartments(departments, returned);
    }

    @Test
    public void getAllDepForHospital(){
        hospitalDepartmentGenerator.fill();

        Hospital hospital = hospitalDepartmentGenerator.getHospitals().get(RandomLong.getInstance().getLong((long)hospitalDepartmentGenerator.getHospitals().size()).intValue());
        List<Department> departments = new ArrayList<>();

        for(Department department : hospitalDepartmentGenerator.getDepartments()){
            if(department.getHospital().getPbb().equals(hospital.getPbb()))
                departments.add(department);
        }
        System.out.println(departments);

        given(departmentRepository.findByHostpitalPbb(hospital.getPbb())).willReturn(departments);

        List<DepartmentDto> departmentDtos = departmentService.getDepartments(hospital.getPbb());

        checkDepartments(departments, departmentDtos);

    }

    @Test
    public void getDepartmentByLbz(){
        hospitalDepartmentGenerator.fill();
        Department department = hospitalDepartmentGenerator.getRandomDepartment();

        employeeGenerator.fill(department);
        Employee employee = employeeGenerator.getRandomEmployee();

        given(employeeRepository.findByLbz(employee.getLbz())).willReturn(java.util.Optional.of(employee));
        Long id = departmentService.findDepartmentIdByLbz(employee.getLbz());

        Assertions.assertEquals(id, employee.getDepartment().getId());
    }

    @Test
    public void getEmployeeDepartment(){
        hospitalDepartmentGenerator.fill();
        Department department = hospitalDepartmentGenerator.getRandomDepartment();

        given(departmentRepository.findByPbo(department.getPbo())).willReturn(java.util.Optional.of(department));
        DepartmentDto departmentDto = departmentService.getEmployeesDepartment(department.getPbo());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(department, departmentDto));
        Assertions.assertEquals(department.getHospital().getShortName(), departmentDto.getHospitalName());
    }

    @Test
    public void getAllDoctorsByPbo(){
        hospitalDepartmentGenerator.fill();
        Department department = hospitalDepartmentGenerator.getRandomDepartment();

        employeeGenerator.fill(department);
        List<Employee> employees = employeeGenerator.getEmployees();
        List<Object[]> objects = new ArrayList<>();

        for(Employee employee : employees){
            Object[] object = new Object[4];
            object[0] = employee.getName();
            object[1] = employee.getSurname();
            object[2] = employee.getLbz();
            object[3] = employee.getDepartment().getId();
            objects.add(object);
        }

        given(employeesRoleRepository.findEmployeesByDepartmentPboAndRoleList(eq(department.getPbo()), any())).willReturn(objects);
        List<DoctorDepartmentDto> returned = departmentService.getAllDoctorsByPbo(department.getPbo());

        int count = 0;
        for(DoctorDepartmentDto doctorDepartmentDto : returned){
            for(Object[] object : objects){
                if( doctorDepartmentDto.getName().equals(object[0]) &&
                    doctorDepartmentDto.getSurname().equals(object[1]) &&
                    doctorDepartmentDto.getLbz().equals(object[2]) && doctorDepartmentDto.getDepartmentId().equals(object[3])){
                    count++;
                }
            }
        }
        Assertions.assertEquals(count, objects.size());
    }

}
