package raf.bolnica1.employees.runner;


import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.RoleShort;
import raf.bolnica1.employees.domain.constants.Title;
import raf.bolnica1.employees.repository.*;

import java.sql.Date;
import java.util.Arrays;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private DepartmentRepository departmentRepository;
    private HospitalRepository hospitalRepository;
    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private EmployeesRoleRepository employeesRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

       ///clearAllRepositories();
        defaultData();
       /* // hospitals
        Hospital hospital1 = new Hospital();
        hospital1.setShortName("H1");
        hospital1.setFullName("Hospital1");
        hospital1.setActivity("Ustanova1");
        hospital1.setPbb("12345");
        hospital1.setDateOfEstablishment(Date.valueOf("2020-01-01"));
        hospital1.setAddress("Adresa1");
        hospital1.setPlace("Place1");

        Hospital hospital2 = new Hospital();
        hospital2.setShortName("H2");
        hospital2.setFullName("Hospital2");
        hospital2.setActivity("Ustanova2");
        hospital2.setPbb("54321");
        hospital2.setDateOfEstablishment(Date.valueOf("2019-01-01"));
        hospital2.setAddress("Adresa2");
        hospital2.setPlace("Place2");

        hospitalRepository.save(hospital1);
        hospitalRepository.save(hospital2);

        // departments
        Department department1 = new Department();
        department1.setName("Department1");
        department1.setPbo("001");
        department1.setHospital(hospital1);

        Department department2 = new Department();
        department2.setName("Department2");
        department2.setPbo("002");
        department2.setHospital(hospital2);

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        // privileges

        Role role1 = new Role();
        role1.setName("Administrator");
        role1.setRoleShort(RoleShort.ROLE_ADMIN);

        Role role2 = new Role();
        role2.setName("Korisnik");
        role2.setRoleShort(RoleShort.ROLE_MED_SESTRA);

        Role role3 = new Role();
        role3.setName("Specijalista");
        role3.setRoleShort(RoleShort.ROLE_DR_SPEC);

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        // employees

        Employee employee1 = new Employee();
        employee1.setLbz("ABC1233");
        employee1.setName("John");
        employee1.setSurname("Doe");
        employee1.setDateOfBirth(Date.valueOf("1999-05-05"));
        employee1.setGender("Male");
        employee1.setJmbg("1234567890123");
        employee1.setAddress("123 Main St");
        employee1.setPlaceOfLiving("City");
        employee1.setPhone("123-456-7890");
        employee1.setEmail("admin@example.com");
        employee1.setUsername("admin");
        employee1.setPassword(passwordEncoder.encode("admin"));
        employee1.setTitle(Title.DR_MED_SPEC);
        employee1.setProfession(Profession.MED_SESTRA);
        employee1.setDepartment(department1);

        Employee employee2 = new Employee();
        employee2.setLbz("ABC123");
        employee2.setName("Milica");
        employee2.setSurname("Pilica");
        employee2.setDateOfBirth(Date.valueOf("1999-05-05"));
        employee2.setGender("Male");
        employee2.setJmbg("1234567890123");
        employee2.setAddress("123 Main St");
        employee2.setPlaceOfLiving("City");
        employee2.setPhone("123-456-7890");
        employee2.setEmail("john@example.com");
        employee2.setDeleted(false);
        employee2.setUsername("user");
        employee2.setPassword(passwordEncoder.encode("password"));
        employee2.setTitle(Title.DR_MED_SPEC);
        employee2.setProfession(Profession.MED_SESTRA);
        employee2.setDepartment(department2);


        employeeRepository.save(employee2);
        employeeRepository.save(employee1);

        // employees privilege
        EmployeesRole employeesRole1 = new EmployeesRole();
        employeesRole1.setEmployee(employee1);
        employeesRole1.setRole(role1);

        EmployeesRole employeesRole2 = new EmployeesRole();
        employeesRole2.setEmployee(employee1);
        employeesRole2.setRole(role2);

        EmployeesRole employeesRole3 = new EmployeesRole();
        employeesRole3.setEmployee(employee2);
        employeesRole3.setRole(role2);

        employeesRoleRepository.save(employeesRole1);
        employeesRoleRepository.save(employeesRole2);
        employeesRoleRepository.save(employeesRole3);*/
    }

    private void clearAllRepositories(){
        departmentRepository.deleteAll();
        hospitalRepository.deleteAll();
        employeeRepository.deleteAll();
        roleRepository.deleteAll();
        employeesRoleRepository.deleteAll();
    }

    private void defaultData() {
        Hospital hospital = new Hospital();
        hospital.setPbb("H123");
        hospital.setFullName("General Hospital");
        hospital.setShortName("GH");
        hospital.setPlace("City Center");
        hospital.setAddress("123 Main St");
        hospital.setDateOfEstablishment(Date.valueOf("1995-04-05"));
        hospital.setActivity("Healthcare");

        hospitalRepository.save(hospital);

        Department department1 = new Department();
        department1.setPbo("D001");
        department1.setName("Cardiology");
        department1.setHospital(hospital);

        Department department2 = new Department();
        department2.setPbo("D002");
        department2.setName("Neurology");
        department2.setHospital(hospital);

        Department department3 = new Department();
        department3.setPbo("D003");
        department3.setName("Gastroenterology");
        department3.setHospital(hospital);

        Department department4 = new Department();
        department4.setPbo("D004");
        department4.setName("Urology");
        department4.setHospital(hospital);

        departmentRepository.saveAll(Arrays.asList(department1, department2, department3, department4));

        createEmployee("E0001", "John", "Doe", "M", RoleShort.ROLE_ADMIN, Title.PROF_DR_MED, Profession.MED_SESTRA, department1);
        createEmployee("E0002", "Jane", "Smith", "Z", RoleShort.ROLE_DR_SPEC_ODELJENJA, Title.DR_MED_SPEC, Profession.SPEC_KARDIOLOG, department1);
        createEmployee("E0003", "Mike", "Brown", "M", RoleShort.ROLE_DR_SPEC, Title.DR_SCI_ME, Profession.SPEC_NEUROLOG, department1);
        createEmployee("E0004", "Mary", "Johnson", "Z", RoleShort.ROLE_DR_SPEC_POV, Title.DIPL_FARM, Profession.SPEC_GASTROENTEROLOG, department1);
        createEmployee("E0005", "James", "Williams", "M", RoleShort.ROLE_MED_SESTRA, Title.MAG_FARM, Profession.SPEC_UROLOG, department1);
        createEmployee("E0006", "Lisa", "Jones", "Z", RoleShort.ROLE_VISA_MED_SESTRA, Title.MR, Profession.SPEC_ENDOKRINOLOG, department1);
        createEmployee("E0007", "Nancy", "Miller", "Z", RoleShort.ROLE_VISI_LAB_TEHNICAR, Title.DR_MED_SPEC, Profession.SPEC_BIOHEMICAR, department1);
        createEmployee("E0008", "Paul", "Davis", "M", RoleShort.ROLE_LAB_TEHNICAR, Title.DIPL_FARM, Profession.SPEC_BIOHEMICAR, department1);
        createEmployee("E0009", "Linda", "Garcia", "Z", RoleShort.ROLE_MED_BIOHEMICAR, Title.MAG_FARM, Profession.SPEC_BIOHEMICAR, department1);
        createEmployee("E0010", "Steven", "Taylor", "M", RoleShort.ROLE_SPEC_MED_BIOHEMIJE, Title.MR, Profession.SPEC_BIOHEMICAR, department1);

    }

    private Employee createEmployee(String lbz, String name, String surname, String gender, RoleShort roleShort, Title title, Profession profession, Department department) {
        Employee employee = new Employee();
        employee.setLbz(lbz);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setDateOfBirth(Date.valueOf("1992-05-04"));
        employee.setGender(gender);
        employee.setJmbg("123456789");
        employee.setAddress("Some Street");
        employee.setPlaceOfLiving("Some City");
        employee.setPhone("000-000-0000");
        employee.setEmail(name.toLowerCase() + "." + surname.toLowerCase() + "@hospital.com");
        employee.setUsername(name.toLowerCase() + "." + surname.toLowerCase());
        employee.setPassword(passwordEncoder.encode("password"));
        employee.setTitle(title);
        employee.setProfession(profession);
        employee.setDepartment(department);

        employeeRepository.save(employee);

        Role role = new Role();
        role.setName(roleShort.name());
        role.setRoleShort(roleShort);

        roleRepository.save(role);

        EmployeesRole employeesRole = new EmployeesRole();
        employeesRole.setEmployee(employee);
        employeesRole.setRole(role);

        employeesRoleRepository.save(employeesRole);

        return employee;
    }
}
