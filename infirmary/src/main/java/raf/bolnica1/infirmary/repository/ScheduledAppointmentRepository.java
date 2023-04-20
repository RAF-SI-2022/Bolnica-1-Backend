package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;

import java.sql.Date;

@Repository
public interface ScheduledAppointmentRepository extends JpaRepository<ScheduledAppointment, Long> {


    @Query("SELECT sa FROM ScheduledAppointment sa WHERE sa.id=:id")
    ScheduledAppointment findScheduledAppointmentById(@Param("id") Long id);


    @Query("SELECT sa FROM ScheduledAppointment sa WHERE sa.prescription.id=:id")
    ScheduledAppointment findScheduledAppointmentByPrescriptionId(@Param("id") Long id);


    @Query("SELECT sa FROM ScheduledAppointment sa WHERE " +
            "(:lbp IS NULL OR sa.prescription.lbp=:lbp) AND " +
            "(:depId IS NULL OR sa.prescription.departmentToId=:depId) AND " +
            "(:startDate IS NULL OR sa.patientAdmission>=:startDate) AND " +
            "(:endDate IS NULL OR sa.patientAdmission<:endDate) AND " +
            "(:status IS NULL OR sa.admissionStatus=:status)")
    Page<ScheduledAppointment> findScheduledAppointmentWithFilter(Pageable pageable,
                                                                  @Param("lbp") String lbp,
                                                                  @Param("depId") Long departmentId,
                                                                  @Param("startDate") Date startDate,
                                                                  @Param("endDate") Date endDate,
                                                                  @Param("status") AdmissionStatus admissionStatus);

}
