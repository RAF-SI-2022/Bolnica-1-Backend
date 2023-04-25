package raf.bolnica1.employees.integration.employeeRole;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.employees.dataGenerators.domain.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.domain.HospitalDepartmentGenerator;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.dto.role.RoleDto;
import raf.bolnica1.employees.repository.*;
import raf.bolnica1.employees.services.EmployeeRoleService;

import java.util.*;

public class EmployeeRoleTestStep extends EmployeeRoleIntegrationTestConfig {

    @Autowired
    private EmployeeGenerator employeeGenerator;
    @Autowired
    private HospitalDepartmentGenerator hospitalDepartmentGenerator;

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeesRoleRepository employeesRoleRepository;

    @Autowired
    private EmployeeRoleService employeesRoleService;

    private Random random = new Random();
    private List<Employee> employees = new ArrayList<>();
    private Map<String, List<Role>> mapa = new HashMap<>();

    @Given("{int} zaposljenih")
    public void generisiZaposljene(int brojZaposljenih){
        hospitalDepartmentGenerator.fill();

        for(Hospital hospital : hospitalDepartmentGenerator.getHospitals())
            hospitalRepository.save(hospital);

        for(Department department : hospitalDepartmentGenerator.getDepartments())
            departmentRepository.save(department);

        for(int i = 0; i<brojZaposljenih; i++){
            Employee employee = employeeGenerator.generateEmployee(hospitalDepartmentGenerator.getRandomDepartment());
            employeeRepository.save(employee);
        }

        for(Employee employee : employeeRepository.findAll()){
            employees.add(employee);
            mapa.put(employee.getLbz(), new ArrayList<>());
        }

        Role r1 = employeeGenerator.generateRole("Doktor");
        Role r2 = employeeGenerator.generateRole("Mediciska sestra");
        Role r3 = employeeGenerator.generateRole("Nacelnik odeljenja");

        roleRepository.save(r1);
        roleRepository.save(r2);
        roleRepository.save(r3);
        List<Role> roles = Arrays.asList(r1, r2, r3);

        for(Employee employee : employees){
            EmployeesRole er = new EmployeesRole();
            er.setEmployee(employee);
            er.setRole(roles.get(Math.abs(random.nextInt())%roles.size()));
            employeesRoleRepository.save(er);
            mapa.get(employee.getLbz()).add(er.getRole());
        }
    }

    @Then("vidimo na koje privilegije imaju")
    public void vidiPrivilegije(){
        for(Employee employee : employeeRepository.findAll()) {
            List<RoleDto> roleDtoList = employeesRoleService.privilegeForEmployee(employee.getLbz());
            int count = 0;
            for(Role role : mapa.get(employee.getLbz())){
                for(RoleDto roleDto : roleDtoList){
                    if(roleDto.getId().equals(role.getId())){
                        Assertions.assertEquals(roleDto.getShortName(), role.getRoleShort().name());
                        count++;
                        break;
                    }
                }
            }

            Assertions.assertEquals(count, mapa.get(employee.getLbz()).size());
        }
    }
}
