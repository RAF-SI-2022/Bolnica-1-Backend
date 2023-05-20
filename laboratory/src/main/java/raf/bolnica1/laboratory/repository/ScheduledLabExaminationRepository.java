package raf.bolnica1.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;

import javax.persistence.LockModeType;
import java.sql.Date;
import java.util.List;

@Repository
public interface ScheduledLabExaminationRepository extends JpaRepository<ScheduledLabExamination, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE sle.id = :id")
    ScheduledLabExamination findByIdLock(@Param("id") Long id);

    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE sle.departmentId=:departmentId")
    List<ScheduledLabExamination> findScheduledLabExaminationsByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE sle.scheduledDate=:date AND sle.departmentId=:departmentId")
    List<ScheduledLabExamination> findScheduledLabExaminationsByDateAndDepartmentId(@Param("date") Date date,@Param("departmentId") Long departmentId);

    @Query("SELECT sle FROM ScheduledLabExamination sle WHERE " +
            "(:lbp IS NULL OR :lbp=sle.lbp) AND " +
            "(:startDate IS NULL OR sle.scheduledDate>=:startDate) AND " +
            "(:endDate IS NULL OR sle.scheduledDate<=:endDate) AND " +
            "(:depId=sle.departmentId)")
    Page<ScheduledLabExamination> findScheduledLabExaminationByLbpAndDateAndDepartmentId(Pageable pageable,
                                                                                         @Param("lbp")String lbp,
                                                                                         @Param("startDate")Date startDate,
                                                                                         @Param("endDate")Date endDate,
                                                                                         @Param("depId")Long departmentId);

}
