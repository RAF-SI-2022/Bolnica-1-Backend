package raf.bolnica1.employees.domainTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import raf.bolnica1.employees.domain.Hospital;
import raf.bolnica1.employees.repository.HospitalRepository;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class HospitalTest {

  @Autowired
  private HospitalRepository hospitalRepository;

  @Test
  public void testHospitalUniqueness() {
    Hospital hospital1 = new Hospital();
    hospital1.setPbb("PBB001");
    hospital1.setFullName("Hospital 1");
    hospital1.setShortName("Hosp 1");
    hospital1.setPlace("City 1");
    hospital1.setAddress("Address 1");
    hospital1.setDateOfEstablishment(new Date(System.currentTimeMillis()));
    hospital1.setActivity("Activity 1");

    Hospital hospital2 = new Hospital();
    hospital2.setPbb("PBB001"); // same PBB as hospital1
    hospital2.setFullName("Hospital 2");
    hospital2.setShortName("Hosp 2");
    hospital2.setPlace("City 2");
    hospital2.setAddress("Address 2");
    hospital2.setDateOfEstablishment(new Date(System.currentTimeMillis()));
    hospital2.setActivity("Activity 2");

    assertThrows(DataIntegrityViolationException.class, () -> {
      hospitalRepository.save(hospital1);
      hospitalRepository.save(hospital2);
    });
  }

  @Test
  public void testAddHospital() {
    Hospital hospital = new Hospital();
    hospital.setPbb("PBB001");
    hospital.setFullName("Hospital 1");
    hospital.setShortName("Hosp 1");
    hospital.setPlace("City 1");
    hospital.setAddress("Address 1");
    hospital.setDateOfEstablishment(new Date(System.currentTimeMillis()));
    hospital.setActivity("Activity 1");

    hospitalRepository.save(hospital);

    List<Hospital> hospitals = hospitalRepository.findAll();
    assertNotNull(hospitals);
    assertFalse(hospitals.isEmpty());
    assertEquals(1, hospitals.size());

    Hospital savedHospital = hospitals.get(0);
    assertNotNull(savedHospital);
    assertEquals(hospital.getPbb(), savedHospital.getPbb());
    assertEquals(hospital.getFullName(), savedHospital.getFullName());
    assertEquals(hospital.getShortName(), savedHospital.getShortName());
    assertEquals(hospital.getPlace(), savedHospital.getPlace());
    assertEquals(hospital.getAddress(), savedHospital.getAddress());
    assertEquals(hospital.getDateOfEstablishment(), savedHospital.getDateOfEstablishment());
    assertEquals(hospital.getActivity(), savedHospital.getActivity());
  }

  @Test
  public void testGetHospitalById() {
    Hospital hospital = new Hospital();
    hospital.setPbb("PBB001");
    hospital.setFullName("Hospital 1");
    hospital.setShortName("Hosp 1");
    hospital.setPlace("City 1");
    hospital.setAddress("Address 1");
    hospital.setDateOfEstablishment(new Date(System.currentTimeMillis()));
    hospital.setActivity("Activity 1");

    hospitalRepository.save(hospital);

    Hospital foundHospital = hospitalRepository.findById(hospital.getId()).orElse(null);
    assertNotNull(foundHospital);
    assertEquals(hospital.getPbb(), foundHospital.getPbb());
    assertEquals(hospital.getFullName(), foundHospital.getFullName());
    assertEquals(hospital.getShortName(), foundHospital.getShortName());
    assertEquals(hospital.getPlace(), foundHospital.getPlace());
    assertEquals(hospital.getAddress(), foundHospital.getAddress());
    assertEquals(hospital.getDateOfEstablishment(), foundHospital.getDateOfEstablishment());
    assertEquals(hospital.getActivity(), foundHospital.getActivity());
  }

  @Test
  void testDeleteHospital() {
    Hospital hospital = new Hospital();
    hospital.setPbb("pbb3");
    hospital.setFullName("Full Name 3");
    hospital.setShortName("Name 3");
    hospital.setPlace("Place 3");
    hospital.setAddress("Address 3");
    hospital.setDateOfEstablishment(Date.valueOf("2000-03-01"));
    hospital.setActivity("Activity 3");
    hospital.setDeleted(true);

    hospitalRepository.save(hospital);

    Assertions.assertTrue(hospital.isDeleted());
  }

  @Test
  void testFindHospitalByPbb() {
    Hospital hospital = new Hospital();
    hospital.setPbb("pbb4");
    hospital.setFullName("Full Name 4");
    hospital.setShortName("Name 4");
    hospital.setPlace("Place 4");
    hospital.setAddress("Address 4");
    hospital.setDateOfEstablishment(Date.valueOf("2000-04-01"));
    hospital.setActivity("Activity 4");
    hospital.setDeleted(false);

    hospitalRepository.save(hospital);

    Hospital foundHospital = hospitalRepository.findByPbb("pbb4").orElse(null);

    assertNotNull(foundHospital);
    Assertions.assertEquals(hospital, foundHospital);
  }

  @Test
  void testFindHospitalByPbbNotFound() {
    Hospital foundHospital = hospitalRepository.findByPbb("pbb5").orElse(null);

    Assertions.assertNull(foundHospital);
  }
}
