package raf.bolnica1.employees.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.ShiftSchedule;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {
    Page<ShiftSchedule> findByDateGreaterThanEqual(Date startDate, Pageable pageable);

    Page<ShiftSchedule> findByDateLessThanEqual(Date endDate, Pageable pageable);

    Page<ShiftSchedule> findByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz = :lbz")
    Page<ShiftSchedule> findByEmployee(@Param("lbz") String lbz, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz = :lbz AND ss.date >= :start")
    Page<ShiftSchedule> findByEmployeeAndDateGreaterThanEqual(@Param("lbz") String lbz, @Param("start") Date startDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz = :lbz AND ss.date <= :end")
    Page<ShiftSchedule> findByEmployeeAndDateLessThanEqual(@Param("lbz") String lbz, @Param("end") Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz = :lbz AND ss.date >= :start AND ss.date <= :end")
    Page<ShiftSchedule> findByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(@Param("lbz") String lbz, @Param("start") Date startDate, @Param("end") Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz=:lbz AND ss.date=:curr")
    Optional<ShiftSchedule> findForEmployee(@Param("lbz") String lbz, @Param("curr") Date date);
}
