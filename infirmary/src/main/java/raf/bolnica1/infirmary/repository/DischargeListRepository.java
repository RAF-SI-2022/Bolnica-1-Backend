package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;

import java.util.List;

@Repository
public interface DischargeListRepository extends JpaRepository<DischargeList, Long> {

    @Query("SELECT dl FROM DischargeList dl WHERE dl.hospitalization.id=:id")
    DischargeList findDischargeListByHospitalizationId(@Param("id") Long hospitalizationId);

}
