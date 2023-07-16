package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.prescription.LabResults;

import java.sql.Date;
import java.util.List;

@Repository
public interface LabResultsRepository extends JpaRepository<LabResults, Long> {

    @Query("SELECT DISTINCT l.analysisName FROM LabResults l " +
            "WHERE (l.labPrescription.id = :prescriptionId)")
    List<String> findAnalysisForPrescription(@Param("prescriptionId") Long prescriptionId);

    @Query("SELECT l FROM LabResults l " +
            "WHERE (l.labPrescription.id = :prescriptionId) " +
            "AND (l.analysisName = :analysis)")
    List<LabResults> findResultsForPrescription(@Param("prescriptionId") Long prescriptionId,
                                                @Param("analysis") String analysis);

    @Query("SELECT lr FROM LabResults lr WHERE DATEDIFF(:date, lr.labPrescription.date) >= 7 AND lr.labPrescription.covid = true AND lr.result='POZITIVAN' AND lr.labPrescription.done=false")
    List<LabResults> findAllPerscriptionsForCovidStats(@Param("date") Date date);

}
