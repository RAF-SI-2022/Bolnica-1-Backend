package raf.bolnica1.employees.domainTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.repository.DepartmentRepository;
import raf.bolnica1.employees.repository.HospitalRepository;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DepartmentTest {

  @Autowired
  private DepartmentRepository departmentRepository;
  @Autowired
  private HospitalRepository hospitalRepository;

  private Department department;
  private Hospital hospital;

  @BeforeEach
  void setUp() {
    hospital = new Hospital();
    hospital.setPbb("PBB001");
    hospital.setFullName("Hospital 1");
    hospital.setShortName("Hosp 1");
    hospital.setPlace("City 1");
    hospital.setAddress("Address 1");
    hospital.setDateOfEstablishment(new Date(System.currentTimeMillis()));
    hospital.setActivity("Activity 1");

    department = new Department();
    department.setPbo("1234567890123");
    department.setName("Cardiology");
    department.setHospital(hospital);

    hospitalRepository.save(hospital);
    departmentRepository.save(department);
  }

  @Test
  void testDepartmentFields() {
    Department foundDepartment = departmentRepository.findById(department.getId()).orElse(null);

    assertNotNull(foundDepartment);
    assertEquals(department.getPbo(), foundDepartment.getPbo());
    assertEquals(department.getName(), foundDepartment.getName());
    assertEquals(department.isDeleted(), foundDepartment.isDeleted());
    assertEquals(department.getHospital(), foundDepartment.getHospital());
  }

  @Test
  void testDepartmentUniqueness() {
    Department duplicateDepartment = new Department();
    duplicateDepartment.setPbo(department.getPbo());
    duplicateDepartment.setName("Oncology");
    duplicateDepartment.setHospital(hospital);

    assertThrows(DataIntegrityViolationException.class, () -> departmentRepository.save(duplicateDepartment));
  }

  @Test
  void testDepartmentDeletion() {
    department.setDeleted(true);
    departmentRepository.save(department);

    Department deletedDepartment = departmentRepository.findById(department.getId()).orElse(null);

    assertNotNull(deletedDepartment);
    assertTrue(deletedDepartment.isDeleted());
  }

  @Test
  void testUpdateDepartment() {
    String newName = "New Department Name";
    department.setName(newName);
    departmentRepository.save(department);
    Department updatedDepartment = departmentRepository.findById(department.getId()).orElse(null);
    assertNotNull(updatedDepartment);
    assertEquals(newName, updatedDepartment.getName());
  }

  @Test
  void testFindDepartmentByPbo() {
    Department foundDepartment = departmentRepository.findByPbo(department.getPbo()).orElse(null);
    assertNotNull(foundDepartment);
    assertEquals(department.getName(), foundDepartment.getName());
  }

  @Test
  void testFindDepartmentByHospital() {
    List<Department> departments = departmentRepository.findByHospital(hospital);
    assertNotNull(departments);
    assertEquals(1, departments.size());
    Department foundDepartment = departments.get(0);
    assertEquals(department.getName(), foundDepartment.getName());
  }


}
