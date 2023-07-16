package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Hospitalization;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {
    @Query("SELECT h FROM Hospitalization h WHERE h.active=true AND h.covid=true AND h.medicalRecord.id=:id")
    Optional<Hospitalization> findActiveHospitalization(@Param("id") Long id);

    @Query("SELECT h FROM Hospitalization h WHERE h.active=true AND h.medicalRecord.patient.lbp=:lbp AND h.patientAdmission=:time")
    Optional<Hospitalization> findHospitalization(@Param("lbp") String lbp, @Param("time")Timestamp patientAdmission);
}
