package raf.bolnica1.employees.runner;


import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.*;
import raf.bolnica1.employees.repository.*;

import java.sql.Date;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private DepartmentRepository departmentRepository;
    private HospitalRepository hospitalRepository;
    private EmployeeRepository employeeRepository;
    private PrivilegeRepository privilegeRepository;
    private EmployeesPrivilegeRepository employeesPrivilegeRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // hospitals
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
        department1.setPbo("12345");
        department1.setHospital(hospital1);

        Department department2 = new Department();
        department2.setName("Department2");
        department2.setPbo("123456");
        department2.setHospital(hospital2);

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        // privileges

        Privilege privilege1 = new Privilege();
        privilege1.setName("Administrator");
        privilege1.setPrivilegeShort(PrivilegeShort.ADMIN);

        Privilege privilege2 = new Privilege();
        privilege2.setName("Korisnik");
        privilege2.setPrivilegeShort(PrivilegeShort.MED_SESTRA);

        privilegeRepository.save(privilege1);
        privilegeRepository.save(privilege2);

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
        employee1.setEmail("john.doe@example.com");
        employee1.setUsername("johndoe");
        employee1.setPassword(passwordEncoder.encode("password1"));
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
        employee2.setUsername("johndoe1");
        employee2.setPassword(passwordEncoder.encode("password2"));
        employee2.setTitle(Title.DR_MED_SPEC);
        employee2.setProfession(Profession.MED_SESTRA);
        employee2.setDepartment(department2);


        employeeRepository.save(employee2);
        employeeRepository.save(employee1);
        // employees privilege


        EmployeesPrivilege employeesPrivilege1 = new EmployeesPrivilege();
        employeesPrivilege1.setEmployee(employee1);
        employeesPrivilege1.setPrivilege(privilege1);

        EmployeesPrivilege employeesPrivilege2 = new EmployeesPrivilege();
        employeesPrivilege2.setEmployee(employee1);
        employeesPrivilege2.setPrivilege(privilege2);

        EmployeesPrivilege employeesPrivilege3 = new EmployeesPrivilege();
        employeesPrivilege3.setEmployee(employee2);
        employeesPrivilege3.setPrivilege(privilege2);

        employeesPrivilegeRepository.save(employeesPrivilege1);
        employeesPrivilegeRepository.save(employeesPrivilege2);
        employeesPrivilegeRepository.save(employeesPrivilege3);
    }
}
