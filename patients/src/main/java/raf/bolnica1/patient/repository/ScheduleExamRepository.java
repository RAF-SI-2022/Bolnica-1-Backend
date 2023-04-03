package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleExamRepository extends JpaRepository<ScheduleExam, Long> {
    Optional<ScheduleExam> deleteScheduleExamById(Long id);

    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz AND se.arrivalStatus = :status")
    Page<ScheduleExam> findScheduleForDoctor(Pageable pageable, @Param("lbz") String lbz, @Param("status") PatientArrival status);

    @Query("SELECT se FROM ScheduleExam se WHERE se.arrivalStatus = :status " +
            "AND YEAR(:date) = YEAR(dateAndTime) AND MONTH(:date) = MONTH(dateAndTime) and DAY(:date) = DAY(dateAndTime)")
    Page<ScheduleExam> findScheduleForMedSister(Pageable pageable, @Param("date") Date date, @Param("status") PatientArrival status);

    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz " +
            "AND YEAR(:date) = YEAR(dateAndTime) AND MONTH(:date) = MONTH(dateAndTime) and DAY(:date) = DAY(dateAndTime)")
    List<ScheduleExam> findFromCurrDateAndDoctor(@Param("date") Date date, @Param("lbz") String lbz);
}
