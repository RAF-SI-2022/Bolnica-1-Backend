package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByLbp(String lbp);
    Optional<Patient> findByJmbg(String jmbg);
    Optional<List<Patient>> findByName(String name);
    Optional<List<Patient>> findBySurname(String surname);
}
