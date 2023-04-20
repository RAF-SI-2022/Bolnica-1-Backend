package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Prescription;

import java.sql.Date;
import java.util.List;

@Repository
public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {

    @Query("SELECT h FROM Hospitalization h WHERE h.id=:id")
    Hospitalization findHospitalizationById(@Param("id") Long id);


    @Query("SELECT h FROM Hospitalization h WHERE h.hospitalRoom.idDepartment=:id")
    Page<Hospitalization> findHospitalizationsByDepartmentId(Pageable pageable, @Param("id") Long departmentId);


    @Query("SELECT h FROM Hospitalization h WHERE h.hospitalRoom.id=:id")
    Page<Hospitalization> findHospitalizationsByHospitalRoomId(Pageable pageable,@Param("id") Long hospitalRoomId);


    @Query("SELECT h FROM Hospitalization h WHERE " +
            "(:name IS NULL OR :name=h.name) AND " +
            "(:surname IS NULL OR :surname=h.surname) AND " +
            "(:jmbg IS NULL OR :jmbg=h.jmbg) AND " +
            "(:depId IS NULL OR :depId=h.hospitalRoom.idDepartment) AND " +
            "(:hrId IS NULL OR :hrId=h.hospitalRoom.id) AND " +
            "(:lbp IS NULL OR :lbp=h.prescription.lbp) AND " +
            "(:startDate IS NULL OR :startDate<=h.patientAdmission ) AND " +
            "(:endDate IS NULL OR :endDate>h.patientAdmission )")
    Page<Hospitalization> findHospitalizationsWithFilter(Pageable pageable,@Param("name") String name,
                                                         @Param("surname") String surname,
                                                         @Param("jmbg") String jmbg,
                                                         @Param("depId") Long departmentId,
                                                         @Param("hrId") Long hospitalRoomId,
                                                         @Param("lbp") String lbp,
                                                         @Param("startDate") Date startDate,
                                                         @Param("endDate") Date endDate);

}
