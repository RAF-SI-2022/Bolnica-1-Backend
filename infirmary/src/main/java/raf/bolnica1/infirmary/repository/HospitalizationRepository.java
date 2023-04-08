package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {
    Hospitalization findByPrescription (Prescription prescription);

    @Query("select h.prescription.lbp from Hospitalization h " +
            "where (h.dischargeDateAndTime is null ) " +
            "and (h.prescription.getIdDepartmentTo = :pbo) " +
            "and h.prescription.lbp in :lbps")
    List<String> findHospitalizedPatients(@Param("pbo") String pbo, @Param("lbps") List<String> lbps);


    @Query("select h from Hospitalization h " +
            "where (h.dischargeDateAndTime is null) " +
            "and (h.prescription.getIdDepartmentTo = :pbo) " +
            "and (h.prescription in :lbps) ")
    List<Hospitalization> findHospitalizations(@Param("pbo") String pbo, @Param("lbps") List<String> lbps);

    @Query("select h from Hospitalization h " +
            "where (h.dischargeDateAndTime is null) " +
            "and (h.prescription.lbp = :lbp) ")
    Optional<Hospitalization> findHospitalizationByLbp(@Param("lbp") String lbp);

}
