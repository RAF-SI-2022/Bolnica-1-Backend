package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;

import java.util.List;

@Repository
public interface DischargeListRepository extends JpaRepository<DischargeList, Long> {

    DischargeList findByHospitalization(Hospitalization hospitalization);
}
