package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {
    Hospitalization findByPrescription (Prescription prescription);
}
