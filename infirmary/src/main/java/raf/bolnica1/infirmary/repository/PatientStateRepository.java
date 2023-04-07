package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.PatientState;

import java.sql.Date;

@Repository
public interface PatientStateRepository extends JpaRepository<PatientState, Long> {


    @Query("SELECT ps FROM PatientState ps WHERE ps.hospitalization.id=:id AND " +
            "(:startDate IS NULL OR :startDate<=ps.dateExamState) AND " +
            "(:endDate IS NULL OR :endDate>=ps.dateExamState)")
    Page<PatientState> findPatientStatesByDate(Pageable pageable, @Param("id") Long hospitalizationId,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

}
