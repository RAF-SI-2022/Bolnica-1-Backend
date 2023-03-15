package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.ExaminationHistory;
import raf.bolnica1.patient.domain.MedicalRecord;

import java.sql.Date;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminationHistoryRepository extends JpaRepository<ExaminationHistory, Long> {
    Optional<List<ExaminationHistory>> findByMedicalRecord_IdAndExamDateEquals(Long id, Date currDate);
    Optional<List<ExaminationHistory>> findByMedicalRecord_IdAndExamDateGreaterThanAndExamDateLessThan(Long id,Date fromDate,Date toDate);

    @Query("SELECT eh FROM ExaminationHistory eh WHERE eh.medicalRecord=:mr")
    List<ExaminationHistory> findExaminationHistoryByMedicalRecord(@Param("mr")MedicalRecord medicalRecord);
}
