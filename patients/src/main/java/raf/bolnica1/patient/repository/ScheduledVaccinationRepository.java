package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.ScheduledVaccination;
import raf.bolnica1.patient.domain.constants.PatientArrival;

import java.sql.Date;

@Repository
public interface ScheduledVaccinationRepository extends JpaRepository<ScheduledVaccination, Long> {

    @Query("SELECT sv FROM ScheduledVaccination sv WHERE " +
            "(:startDate IS NULL OR :startDate<=sv.dateAndTime) AND " +
            "(:endDate IS NULL OR :endDate>sv.dateAndTime) AND " +
            "(:lbp IS NULL OR :lbp=sv.patient.lbp) AND " +
            "(:lbz IS NULL OR :lbz=sv.lbz) AND " +
            "(:covid IS NULL OR :covid=sv.vaccination.covid) AND " +
            "(:pa IS NULL OR :pa=sv.arrivalStatus)")
    Page<ScheduledVaccination> getScheduledVaccinationsWithFilter(Pageable pageable,
                                                                  @Param("startDate") Date startDate,
                                                                  @Param("endDate") Date endDate,
                                                                  @Param("lbp")String lbp,
                                                                  @Param("lbz")String lbz,
                                                                  @Param("covid")Boolean covid,
                                                                  @Param("pa")PatientArrival patientArrival);

}
