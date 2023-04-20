package raf.bolnica1.employees.domainTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.constants.Profession;
import raf.bolnica1.employees.domain.constants.Title;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EmployeeTest {
  @Autowired
  private EntityManager entityManager;

  private Employee employee;

  @BeforeEach
  void setUp() {
    employee = new Employee();
    employee.setLbz("1234567890123");
    employee.setName("John");
    employee.setSurname("Doe");
    employee.setDateOfBirth(Date.valueOf("1980-01-01"));
    employee.setGender("Male");
    employee.setJmbg("0101980170000");
    employee.setAddress("123 Main St");
    employee.setPlaceOfLiving("New York");
    employee.setPhone("123-456-7890");
    employee.setEmail("johndoe@ibis.raf");
    employee.setUsername("johndoe");
    employee.setPassword("password");
    employee.setTitle(Title.DR_MED_SPEC);
    employee.setProfession(Profession.SPEC_KARDIOLOG);

    Department department = new Department();
    department.setName("Cardiology");
    entityManager.persist(department);
    employee.setDepartment(department);

    entityManager.persist(employee);
    entityManager.flush();
  }

  @Test
  void testEmployeeFields() {
    Employee foundEmployee = entityManager.find(Employee.class, employee.getId());

    assertEquals(employee.getLbz(), foundEmployee.getLbz());
    assertEquals(employee.getName(), foundEmployee.getName());
    assertEquals(employee.getSurname(), foundEmployee.getSurname());
    assertEquals(employee.getDateOfBirth(), foundEmployee.getDateOfBirth());
    assertEquals(employee.getGender(), foundEmployee.getGender());
    assertEquals(employee.getJmbg(), foundEmployee.getJmbg());
    assertEquals(employee.getAddress(), foundEmployee.getAddress());
    assertEquals(employee.getPlaceOfLiving(), foundEmployee.getPlaceOfLiving());
    assertEquals(employee.getPhone(), foundEmployee.getPhone());
    assertEquals(employee.getEmail(), foundEmployee.getEmail());
    assertEquals(employee.getUsername(), foundEmployee.getUsername());
    assertEquals(employee.getPassword(), foundEmployee.getPassword());
    assertEquals(employee.isDeleted(), foundEmployee.isDeleted());
    assertEquals(employee.getTitle(), foundEmployee.getTitle());
    assertEquals(employee.getProfession(), foundEmployee.getProfession());
    assertEquals(employee.getDepartment(), foundEmployee.getDepartment());
  }

  @Test
  void testEmployeeUniqueness() {
    Employee duplicateEmployee = new Employee();
    duplicateEmployee.setLbz("1234567890123");
    duplicateEmployee.setName("Jane");
    duplicateEmployee.setSurname("Doe");
    duplicateEmployee.setDateOfBirth(Date.valueOf("1980-01-01"));
    duplicateEmployee.setGender("Female");
    duplicateEmployee.setJmbg("0101980170001");
    duplicateEmployee.setAddress("456 Main St");
    duplicateEmployee.setPlaceOfLiving("New York");
    duplicateEmployee.setPhone("123-456-7890");
    duplicateEmployee.setEmail("janedoe@ibis.raf");
    duplicateEmployee.setUsername("janedoe");
    duplicateEmployee.setPassword("password");
    duplicateEmployee.setTitle(Title.PROF_DR_MED);
    duplicateEmployee.setProfession(Profession.SPEC_HIRURG);
    duplicateEmployee.setDepartment(employee.getDepartment());

    assertThrows(PersistenceException.class, () -> entityManager.persist(duplicateEmployee));
  }

  @Test
  void testUpdateEmployee() {
    // Update the employee's email address
    employee.setEmail("newemail@ibis.raf");
    entityManager.merge(employee);
    entityManager.flush();

    // Check that the update was successful
    Employee updatedEmployee = entityManager.find(Employee.class, employee.getId());
    assertEquals("newemail@ibis.raf", updatedEmployee.getEmail());
  }

  @Test
  void testDeleteEmployee() {
    Employee foundEmployee = entityManager.find(Employee.class, employee.getId());

    assertNotNull(foundEmployee);
    assertFalse(foundEmployee.isDeleted());

    foundEmployee = entityManager.find(Employee.class, employee.getId());
    foundEmployee.setDeleted(true);
    entityManager.merge(foundEmployee);
    entityManager.flush();

    Assertions.assertTrue(foundEmployee.isDeleted());
  }

  @Test
  void testEmptyFields() {
    // Create an employee with empty fields
    Employee emptyEmployee = new Employee();
    entityManager.persist(emptyEmployee);
    entityManager.flush();

    // Check that the employee was saved with null values for empty fields
    Employee foundEmployee = entityManager.find(Employee.class, emptyEmployee.getId());
    assertNull(foundEmployee.getLbz());
    assertNull(foundEmployee.getName());
    assertNull(foundEmployee.getSurname());
    assertNull(foundEmployee.getDateOfBirth());
    assertNull(foundEmployee.getGender());
    assertNull(foundEmployee.getJmbg());
    assertNull(foundEmployee.getAddress());
    assertNull(foundEmployee.getPlaceOfLiving());
    assertNull(foundEmployee.getPhone());
    assertNull(foundEmployee.getEmail());
    assertNull(foundEmployee.getUsername());
    assertNull(foundEmployee.getPassword());
    assertEquals(false, foundEmployee.isDeleted());
    assertNull(foundEmployee.getTitle());
    assertNull(foundEmployee.getProfession());
    assertNull(foundEmployee.getDepartment());
  }

  @Test
  void testNullFields() {
    // Create an employee with null fields
    Employee nullEmployee = new Employee();
    nullEmployee.setLbz(null);
    nullEmployee.setName(null);
    nullEmployee.setSurname(null);
    nullEmployee.setDateOfBirth(null);
    nullEmployee.setGender(null);
    nullEmployee.setJmbg(null);
    nullEmployee.setAddress(null);
    nullEmployee.setPlaceOfLiving(null);
    nullEmployee.setPhone(null);
    nullEmployee.setEmail(null);
    nullEmployee.setUsername(null);
    nullEmployee.setPassword(null);
    nullEmployee.setTitle(null);
    nullEmployee.setProfession(null);
    nullEmployee.setDepartment(null);

    // Save the employee and check that it was saved successfully
    entityManager.persist(nullEmployee);
    entityManager.flush();
    Employee foundEmployee = entityManager.find(Employee.class, nullEmployee.getId());
    assertNotNull(foundEmployee);
  }
}
