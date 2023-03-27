package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription findByLbp(String lbp);

    @Transactional
    @Modifying
    @Query("UPDATE Prescription SET prescriptionStatus = :prescriptionStatus WHERE id = :id")
    void updatePrescriptionStatus(@Param("prescriptionStatus") PrescriptionStatus prescriptionStatus,@Param("id") Long id);
}
