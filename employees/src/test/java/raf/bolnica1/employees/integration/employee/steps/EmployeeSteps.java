package raf.bolnica1.employees.integration.employee.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.employees.dataGenerators.domain.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.domain.HospitalDepartmentGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeCreateDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateAdminDtoGenerator;
import raf.bolnica1.employees.dataGenerators.dto.EmployeeUpdateDtoGenerator;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.dto.employee.EmployeeCreateDto;
import raf.bolnica1.employees.dto.employee.EmployeeDto;
import raf.bolnica1.employees.integration.employee.EmployeeIntegrationTestConfig;
import raf.bolnica1.employees.repository.*;
import raf.bolnica1.employees.services.DepartmentService;
import raf.bolnica1.employees.services.EmployeeService;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSteps extends EmployeeIntegrationTestConfig {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private EmployeesRoleRepository employeesRoleRepository;
    @Autowired
    private EmployeeGenerator employeeGenerator;
    @Autowired
    private EmployeeCreateDtoGenerator employeeCreateDtoGenerator;
    @Autowired
    private HospitalDepartmentGenerator hospitalDepartmentGenerator;
    @Autowired
    private ClassJsonComparator classJsonComparator;
    @Autowired
    private EmployeeUpdateDtoGenerator employeeUpdateDtoGenerator;
    @Autowired
    private EmployeeUpdateAdminDtoGenerator employeeUpdateAdminDtoGenerator;


    private List<EmployeeCreateDto> employeeCreateDtos;

    @Given("napravljen {int} zaposleni")
    public void napravljen_zaposleni(Integer employeeCount) {
        try{

            Hospital hospital= hospitalDepartmentGenerator.generateHospital();
            hospital.setId(null);
            hospital=hospitalRepository.save(hospital);

            Department department = hospitalDepartmentGenerator.generateDepartments(hospital);
            department.setId(null);
            department=departmentRepository.save(department);

            employeeCreateDtos=new ArrayList<>();
            for(int i=0;i<employeeCount;i++) {
                EmployeeCreateDto employeeCreateDto = employeeCreateDtoGenerator.generateEmployee(department.getPbo());
                employeeCreateDtos.add(employeeCreateDto);
                for(String permission:employeeCreateDtos.get(i).getPermissions()){
                    Role role=new Role();
                    role.setRoleShort(RoleShort.valueOf(permission));
                    role.setName(permission);
                    System.out.println(role.getRoleShort()+" "+roleRepository.findByRoleShort(role.getRoleShort()).isPresent());
                    if(roleRepository.findByRoleShort(role.getRoleShort()).isPresent())continue;
                    roleRepository.save(role);
                }
                EmployeeDto employee=employeeService.createEmployee(employeeCreateDto);
                Assertions.assertTrue(classJsonComparator.compareCommonFields(employee,employeeCreateDto));
            }


        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("on se nalazi u bazi")
    public void on_se_nalazi_u_bazi() {
        try{

            Employee employee=employeeRepository.findByLbz(employeeCreateDtos.get(0).getLbz()).get();

            Assertions.assertTrue(classJsonComparator.compareCommonFields(employee,
                    employeeCreateDtos.get(0)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("njegove permisije su upisane u bazu")
    public void njegove_permisije_su_upisane_u_bazu() {
        try{

            List<EmployeesRole> employeesRoles=employeesRoleRepository.findAll();

            for(String permission:employeeCreateDtos.get(0).getPermissions()){
                boolean flag=false;

                for(EmployeesRole employeesRole:employeesRoles){

                    if(classJsonComparator.compareCommonFields(employeesRole.getEmployee(),
                            employeeCreateDtos.get(0))){

                        if(employeesRole.getRole().getName().equals(
                                permission)){
                            flag=true;
                        }
                    }

                }

                Assertions.assertTrue(flag);
            }

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @Then("dobavljanje njega po njegovom lbz funkcionise")
    public void dobavljanje_njega_po_njegovom_lbz_funkcionise() {
        try{

            EmployeeDto employee=employeeService.findEmployeeInfo(employeeCreateDtos.get(0).getLbz());

            Assertions.assertTrue(classJsonComparator.compareCommonFields(employee,
                    employeeCreateDtos.get(0)));

        }catch (Exception e){
            Assertions.fail(e);
        }
    }



    @When("taj zaposleni obrisan")
    public void taj_zaposleni_obrisan() {
        try{

            employeeService.softDeleteEmployee(employeeCreateDtos.get(0).getLbz());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }
    @Then("taj zaposleni se ne nalazi u bazi")
    public void taj_zaposleni_se_ne_nalazi_u_bazi() {
        try{

            EmployeeDto employee=employeeService.findEmployeeInfo(employeeCreateDtos.get(0).getLbz());
            Assertions.assertTrue(employee.isDeleted());

        }catch (Exception e){
            Assertions.fail(e);
        }
    }

}
