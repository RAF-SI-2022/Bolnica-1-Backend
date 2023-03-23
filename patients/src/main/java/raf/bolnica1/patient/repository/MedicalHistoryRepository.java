package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.domain.MedicalRecord;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    Optional<List<MedicalHistory>> findByMedicalRecord_IdAndDiagnosisCode_Id(Long id, Long mkb10);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.medicalRecord=:mr")
    List<MedicalHistory> findMedicalHistoryByMedicalRecord(@Param("mr")MedicalRecord medicalRecord);

    @Query("SELECT mh FROM MedicalHistory mh JOIN DiagnosisCode dc " +
            "ON mh.diagnosisCode.id = dc.id " +
            "WHERE (mh.diagnosisCode.code LIKE %:diagnosis%) AND " +
            "(mh.startDate < :startDate) AND " +
            "(mh.valid = true)")
    Optional<MedicalHistory> findByDiagnosisCodeAndStartDateAndValid(@Param("diagnosis") String diagnosis,
                                                                     @Param("startDate") Date startDate);

    @Query("SELECT mh FROM MedicalHistory mh JOIN DiagnosisCode dc " +
            "ON mh.diagnosisCode.id = dc.id " +
            "WHERE (mh.diagnosisCode.code LIKE %:diagnosis%) AND " +
            "(mh.medicalRecord.id = :record)")
    Optional<MedicalHistory> findPrev(@Param("diagnosis") String diagnosis,
                                      @Param("record") Long recordId);

}
