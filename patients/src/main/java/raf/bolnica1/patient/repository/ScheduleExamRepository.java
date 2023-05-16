package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import javax.persistence.LockModeType;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleExamRepository extends JpaRepository<ScheduleExam, Long> {

    Optional<ScheduleExam> deleteScheduleExamById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ScheduleExam s WHERE s.id = :id")
    Optional<ScheduleExam> findByIdLock(@Param("id") Long id);


    @Query("SELECT s FROM ScheduleExam s WHERE s.doctorLbz = :lbz AND s.dateAndTime = :date")
    Optional<ScheduleExam> findByDoctorLbzAndDateAndTime(@Param("lbz") String doctorLbz, @Param("date") Timestamp dateAndTime);

    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz AND se.arrivalStatus = :status")
    Page<ScheduleExam> findScheduleForDoctor(Pageable pageable, @Param("lbz") String lbz, @Param("status") PatientArrival status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz AND se.arrivalStatus = :status")
    Page<ScheduleExam> findScheduleForDoctorLock(Pageable pageable, @Param("lbz") String lbz, @Param("status") PatientArrival status);

    @Query("SELECT se FROM ScheduleExam se WHERE se.arrivalStatus = :status " +
            "AND :date <= se.dateAndTime")
    Page<ScheduleExam> findScheduleForMedSister(Pageable pageable, @Param("date") Date date, @Param("status") PatientArrival status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT se FROM ScheduleExam se WHERE se.arrivalStatus = :status " +
            "AND :date <= se.dateAndTime")
    Page<ScheduleExam> findScheduleForMedSisterLock(Pageable pageable, @Param("date") Date date, @Param("status") PatientArrival status);

    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz " +
            "AND :date <= se.dateAndTime")
    List<ScheduleExam> findFromCurrDateAndDoctor(@Param("date") Date date, @Param("lbz") String lbz);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT se FROM ScheduleExam se WHERE se.doctorLbz = :lbz " +
            "AND :date <= se.dateAndTime")
    List<ScheduleExam> findFromCurrDateAndDoctorLock(@Param("date") Date date, @Param("lbz") String lbz);
}
