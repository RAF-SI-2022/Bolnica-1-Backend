package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    Allergy findByName(String name);
}
