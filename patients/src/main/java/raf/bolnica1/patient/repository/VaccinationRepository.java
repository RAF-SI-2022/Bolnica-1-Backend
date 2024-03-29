package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Vaccination;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    Vaccination findByName(String name);
}
