package raf.bolnica1.employees.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.ShiftSchedule;

import java.sql.Date;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {
    Page<ShiftSchedule> findByDateGreaterThanEqual(Date startDate, Pageable pageable);

    Page<ShiftSchedule> findByDateLessThanEqual(Date endDate, Pageable pageable);

    Page<ShiftSchedule> findByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.id = :id")
    Page<ShiftSchedule> findByEmployee(@Param("id") Long id, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.id = :id AND ss.date >= :start")
    Page<ShiftSchedule> findByEmployeeAndDateGreaterThanEqual(@Param("id") Long id, @Param("start") Date startDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.id = :id AND ss.date <= :end")
    Page<ShiftSchedule> findByEmployeeAndDateLessThanEqual(@Param("id") Long id, @Param("end") Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.id = :id AND ss.date >= :start AND ss.date <= :end")
    Page<ShiftSchedule> findByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(@Param("id") Long id, @Param("start") Date startDate, @Param("end") Date endDate, Pageable pageable);
}
