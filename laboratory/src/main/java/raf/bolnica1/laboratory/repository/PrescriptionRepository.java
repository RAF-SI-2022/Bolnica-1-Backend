package raf.bolnica1.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.Prescription;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p WHERE p.id=:id")
    Prescription findPrescriptionById(@Param("id")Long id);

    @Query("SELECT p FROM Prescription p WHERE p.lbp = :lbp AND p.doctorLbz = :doctorLbz AND p.status = :status")
    List<Prescription> findPrescriptionsByLbpAndDoctorLbz(@Param("lbp") String lbp, @Param("doctorLbz") String doctorLbz, @Param("status") PrescriptionStatus status);

    @Query("SELECT p FROM Prescription p WHERE p.lbp = :lbp")
    List<Prescription> findPrescriptionsByLbp(@Param("lbp") String lbp);

    @Query("SELECT p FROM Prescription p WHERE p.lbp = :lbp AND p.status = :status")
    List<Prescription> findPrescriptionsByLbpNotRealized(@Param("lbp") String lbp, @Param("status") PrescriptionStatus status);

    @Query("SELECT p FROM Prescription p WHERE p.status = :status")
    List<Prescription> findPrescriptionsNotRealized(@Param("status") PrescriptionStatus status);
}
