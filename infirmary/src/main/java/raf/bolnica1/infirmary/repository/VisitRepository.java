package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.Visit;

import java.sql.Date;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {


    @Query("SELECT v FROM Visit v WHERE " +
            "(:depId IS NULL OR :depId=v.hospitalization.hospitalRoom.idDepartment) AND " +
            "(:hrId IS NULL OR :hrId=v.hospitalization.hospitalRoom.id) AND " +
            "(:hosId IS NULL OR :hosId=v.hospitalization.id) AND " +
            "(:startDate IS NULL OR :startDate<=v.visitTime ) AND " +
            "(:endDate IS NULL OR :endDate>=v.visitTime )")
    Page<Visit> findVisitsWithFilter(Pageable pageable,
                                     @Param("depId") Long departmentId,
                                     @Param("hrId") Long hospitalRoomId,
                                     @Param("hosId") Long hospitalizationId,
                                     @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate);

}
