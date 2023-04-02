package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;

import java.sql.Date;

@Repository
public interface ScheduledAppointmentRepository extends JpaRepository<ScheduledAppointment, Long> {

    @Query("select ap from ScheduledAppointment ap join Prescription p " +
            "on ap.prescription.id = p.id " +
            "where (:lbp is null or ap.prescription.lbp like %:lbp%) " +
            "and (:scheduleDate is null or ap.patientAdmission > :scheduleDate)" +
            "and (ap.prescription.getIdDepartmentTo = :depId)")
    Page<ScheduledAppointment> findAppointment(Pageable pageRequest, @Param("depId") Long depId, @Param("lbp") String lbp, @Param("scheduleDate")Date date);

}
