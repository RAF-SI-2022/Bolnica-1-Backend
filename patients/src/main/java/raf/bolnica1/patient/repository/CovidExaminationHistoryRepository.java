package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.CovidExaminationHistory;
import raf.bolnica1.patient.domain.MedicalRecord;

@Repository
public interface CovidExaminationHistoryRepository extends JpaRepository<CovidExaminationHistory,Long> {

    @Query("SELECT ceh FROM CovidExaminationHistory ceh WHERE :mr=ceh.medicalRecord")
    Page<CovidExaminationHistory> findCovidExaminationHHistoryByMedicalRecordPaged(Pageable pageable,
                                                                                   @Param("mr") MedicalRecord mr);

}
