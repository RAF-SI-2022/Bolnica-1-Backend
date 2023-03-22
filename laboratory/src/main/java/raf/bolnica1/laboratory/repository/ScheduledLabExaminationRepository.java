package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScheduledLabExaminationRepository extends JpaRepository<ScheduledLabExamination, Long> {

    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE sle.departmentId=:departmentId")
    List<ScheduledLabExamination> findScheduledLabExaminationsByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE sle.scheduledDate=:date AND sle.departmentId=:departmentId")
    List<ScheduledLabExamination> findScheduledLabExaminationsByDateAndDepartmentId(@Param("date") Date date,@Param("departmentId") Long departmentId);

}
