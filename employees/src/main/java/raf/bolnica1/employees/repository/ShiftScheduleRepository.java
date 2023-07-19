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
    @Query("SELECT ss FROM ShiftSchedule ss WHERE " +
            "(:startDate IS NULL OR :startDate<=ss.date) AND" +
            "(:endDate IS NULL OR :endDate>=ss.date)")
    Page<ShiftSchedule> findByDate(@Param("startDate")Date startDate,@Param("endDate") Date endDate, Pageable pageable);

    @Query("SELECT ss FROM ShiftSchedule ss WHERE " +
            "(:start IS NULL OR :start<=ss.date) AND " +
            "(:end IS NULL OR :end>=ss.date) AND " +
            "(:lbz=ss.employee.lbz)")
    Page<ShiftSchedule> findByEmployeeAndDate(@Param("lbz") String lbz,
                                              @Param("start") Date startDate,
                                              @Param("end") Date endDate, Pageable pageable);
    @Query("SELECT ss FROM ShiftSchedule ss WHERE ss.employee.lbz=:lbz AND ss.date=:curr")
    Optional<ShiftSchedule> findForEmployee(@Param("lbz") String lbz, @Param("curr") Date date);
}
