package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.prescription.Prescription;

import java.util.Date;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p " +
            "WHERE (p.medicalRecord.patient.lbp = :lbp) " +
            "AND (:fromDate IS NULL OR :toDate IS NULL OR p.date BETWEEN :fromDate AND :toDate)")
    Page<Prescription> findPrescriptionByPatientAndDate(
            Pageable pageable,
            @Param("lbp") String lbp,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
}
