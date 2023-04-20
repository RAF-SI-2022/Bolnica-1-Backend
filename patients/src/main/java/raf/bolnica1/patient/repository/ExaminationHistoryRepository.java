package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Anamnesis;
import raf.bolnica1.patient.domain.DiagnosisCode;
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

    @Query("SELECT eh FROM ExaminationHistory eh WHERE eh.medicalRecord=:mr AND eh.examDate>=:sdt AND eh.examDate<=:edt")
    Page<ExaminationHistory> findExaminationHistoryByMedicalRecordAndDateRange(Pageable pageable,
                                                                                     @Param("mr") MedicalRecord medicalRecord,
                                                                                     @Param("sdt") Date startDate,
                                                                                     @Param("edt") Date endDate);


    @Query("SELECT eh FROM ExaminationHistory eh WHERE eh.examDate=:examDate AND eh.lbz>=:lbz AND eh.confidential<=:confidential AND eh.objectiveFinding<=:objectiveFinding AND eh.advice<=:advice AND eh.therapy<=:therapy")
    ExaminationHistory findByStartDateAndLbzAndConfidentialAndObjectiveFindingAndAdviceAndTherapy(@Param("examDate") Date examDate,
                                                                                                   @Param("lbz") String lbz,
                                                                                                   @Param("confidential") boolean confidential,
                                                                                                   @Param("objectiveFinding") String objectiveFinding,
                                                                                                   @Param("advice") String advice,
                                                                                                   @Param("therapy") String therapy);


}
