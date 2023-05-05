package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;

import java.sql.Date;
import java.util.List;

@Repository
public interface DischargeListRepository extends JpaRepository<DischargeList, Long> {

    @Query("SELECT dl FROM DischargeList dl WHERE dl.hospitalization.id=:id")
    DischargeList findDischargeListByHospitalizationId(@Param("id") Long hospitalizationId);

    @Query("SELECT dl FROM DischargeList dl WHERE " +
            "(:hosId IS NULL OR :hosId=dl.hospitalization.id) AND " +
            "(:startDate IS NULL OR :startDate<=dl.creation) AND " +
            "(:endDate IS NULL OR :endDate>dl.creation) AND " +
            "(:lbp IS NULL OR :lbp=dl.hospitalization.prescription.lbp)")
    Page<DischargeList> findDischargeListWithFilter(Pageable pageable, @Param("hosId") Long hospitalizationId,
                                                    @Param("startDate")Date startDate,
                                                    @Param("endDate")Date endDate,
                                                    @Param("lbp")String lbp);

}
