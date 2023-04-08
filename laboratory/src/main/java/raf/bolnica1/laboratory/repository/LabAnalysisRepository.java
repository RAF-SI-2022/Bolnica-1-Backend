package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;

@Repository
public interface LabAnalysisRepository extends JpaRepository<LabAnalysis, Long> {

    @Query("SELECT la FROM LabAnalysis la WHERE la.id=:id")
    LabAnalysis findLabAnalysisById(@Param("id") Long id);

}
