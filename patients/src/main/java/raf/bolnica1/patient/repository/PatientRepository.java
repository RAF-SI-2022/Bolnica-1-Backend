package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Patient;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByLbp(String lbp);
    Optional<Patient> findByJmbg(String jmbg);
    Optional<List<Patient>> findByName(String name);
    Optional<List<Patient>> findBySurname(String surname);

    @Query("SELECT p FROM Patient p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:surname IS NULL OR p.surname LIKE %:surname%) AND " +
            "(:jmbg IS NULL OR p.jmbg LIKE %:jmbg%) AND " +
            "(:lbp IS NULL OR p.lbp LIKE %:lbp%)")
    Page<Patient> listPatientsWithFilters(Pageable pageable,
                                          @Param("name") String name,
                                          @Param("surname") String surname,
                                          @Param("jmbg") String jmbg,
                                          @Param("lbp") String lbp);
}
