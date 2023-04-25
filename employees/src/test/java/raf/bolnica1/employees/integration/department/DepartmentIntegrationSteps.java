package raf.bolnica1.employees.integration.department;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import raf.bolnica1.employees.dataGenerators.domain.EmployeeGenerator;
import raf.bolnica1.employees.dataGenerators.domain.HospitalDepartmentGenerator;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.dto.department.DepartmentDto;
import raf.bolnica1.employees.dto.employee.DoctorDepartmentDto;
import raf.bolnica1.employees.repository.*;
import raf.bolnica1.employees.services.DepartmentService;
import raf.bolnica1.employees.services.EmployeeService;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentIntegrationSteps extends DepartmentIntegrationTestConfig{

    @Autowired
    private EmployeeGenerator employeeGenerator;
    @Autowired
    private HospitalDepartmentGenerator hospitalDepartmentGenerator;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeesRoleRepository employeesRoleRepository;
    @Autowired
    private ClassJsonComparator classJsonComparator;

    private List<Object> objects = new ArrayList<>();
    private Map<String, List<Department>> mapa = new HashMap<>();

    @Given("{int} zaposljenih")
    public void genrisiZaposljene(int brojZaposljenih){
        objects.clear();

        hospitalDepartmentGenerator.fill();

        for(Hospital hospital : hospitalDepartmentGenerator.getHospitals())
            hospitalRepository.save(hospital);

        for(Department department : hospitalDepartmentGenerator.getDepartments())
            departmentRepository.save(department);

        for(int i = 0; i<brojZaposljenih; i++){
            Employee employee = employeeGenerator.generateEmployee(hospitalDepartmentGenerator.getRandomDepartment());
            employee = employeeRepository.save(employee);
            objects.add(employee);
        }
    }

    @Then("vidimo na kom odeljenju ko radi")
    public void pronadjiOdeljenjaZaposljenih(){
        List<Employee> employeeList = employeeRepository.findAll();

        int count = 0;

        for(Employee employee : employeeList){
            for(Object employee2 : objects){
                if(employee2 instanceof Employee && employee.getId().equals(((Employee) employee2).getId())){
                    Assertions.assertEquals(employee.getDepartment().getId(), ((Employee) employee2).getDepartment().getId());
                    count++;
                    break;
                }
            }
        }
        if(objects.size() > 0 && objects.get(0) instanceof Employee)
            Assertions.assertEquals(count, objects.size());
    }

    @Given("{int} zdravstvenih ustanova")
    public void generisiUstanove(int brojUstanova){
        hospitalDepartmentGenerator.fill();

        for(Hospital hospital : hospitalDepartmentGenerator.getHospitals()){
            hospital = hospitalRepository.save(hospital);
            mapa.put(hospital.getPbb(), new ArrayList<>());
        }

        for(Department department : hospitalDepartmentGenerator.getDepartments()){
            department = departmentRepository.save(department);
            mapa.get(department.getHospital().getPbb()).add(department);
        }

    }

    @Then("vidimo koja sve odeljenja ima")
    public void nadjiOdeljenjaNaUstanovi(){
        int count = 0;
        for(Hospital hospital : hospitalRepository.findAll()) {
            List<DepartmentDto> departmentDtos = departmentService.getDepartments(hospital.getPbb());
            Assertions.assertTrue(departmentDtos != null);
            if(mapa.containsKey(hospital.getPbb())) {
                count = 0;
                List<Department> departments = mapa.get(hospital.getPbb());
                Assertions.assertTrue(departments != null);
                for (Department department : departments)
                {
                    for (DepartmentDto departmentDto : departmentDtos) {
                        if(departmentDto.getId().equals(department.getId())) {
                            Assertions.assertTrue(classJsonComparator.compareCommonFields(department, departmentDto));
                            count++;
                            break;
                        }
                    }
                }
                Assertions.assertEquals(count, departments.size());
            }
        }
    }

    @Given("Odeljenja sa po {int} doktora")
    public void generisiOdeljenjaSaDoktorima(int brojDoktora){
        objects.clear();

        hospitalDepartmentGenerator.fill();

        for(Hospital hospital : hospitalDepartmentGenerator.getHospitals())
            hospitalRepository.save(hospital);

        for(Department department : hospitalDepartmentGenerator.getDepartments())
            departmentRepository.save(department);

        Role role = employeeGenerator.generateRole("Doktor");
        role = roleRepository.save(role);

        for(int i = 0; i<brojDoktora; i++){
            Employee employee = employeeGenerator.generateEmployee(hospitalDepartmentGenerator.getRandomDepartment());
            employee = employeeRepository.save(employee);
            objects.add(employee);

            EmployeesRole employeesRole = new EmployeesRole();
            employeesRole.setRole(role);
            employeesRole.setEmployee(employee);
            employeesRoleRepository.save(employeesRole);
        }
    }

    @Then("vidimo koji doktori rade na kom odeljenju")
    public void doktoriPoOdeljenjima() {

        int count = 0;
        for (Department department : departmentRepository.findAll()) {
            List<DoctorDepartmentDto> doctorDepartmentDtos = departmentService.getAllDoctorsByPbo(department.getPbo());

            for (Object employee2 : objects) {
                for (DoctorDepartmentDto doctorDepartmentDto : doctorDepartmentDtos) {
                    if (employee2 instanceof Employee && doctorDepartmentDto.getLbz().equals(((Employee) employee2).getLbz())) {
                        Assertions.assertTrue(classJsonComparator.compareCommonFields(employee2, doctorDepartmentDto));
                        count++;
                        break;
                    }
                }
            }
        }
        Assertions.assertEquals(count, objects.size());
    }

    @Given("odeljenja cije ustanove pretrazujemo")
    public void odeljenjazaUstanove(){
        objects.clear();

        for(Hospital hospital : hospitalDepartmentGenerator.getHospitals())
            hospitalRepository.save(hospital);

        for(Department department : hospitalDepartmentGenerator.getDepartments())
            departmentRepository.save(department);

    }

    @Then("vidimo koje zdravstvene ustanove imaju odeljenje sa odredjenim nazivom")
    public void nesto(){

    }
}
