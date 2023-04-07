package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;


@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p WHERE p.id=:id")
    Prescription findPrescriptionById(@Param("id") Long id);


    @Query("SELECT p FROM Prescription p WHERE " +
            "(:lbp IS NULL OR p.lbp=:lbp) AND " +
            "(:depId IS NULL OR p.idDepartmentTo=:depId) AND " +
            "(:status IS NULL OR p.prescriptionStatus=:status)")
    Page<Prescription> findPrescriptionWithFilter(Pageable pageable, @Param("lbp") String lbp, @Param("depId") Long departmentId, @Param("status") PrescriptionStatus prescriptionStatus);


}
