package raf.bolnica1.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.domain.lab.Parameter;

import java.util.List;

@Repository
public interface AnalysisParameterRepository extends JpaRepository<AnalysisParameter, Long> {

    @Query("SELECT ap FROM AnalysisParameter ap WHERE ap.labAnalysis.id=:analysisId AND ap.parameter.id=:parameterId")
    AnalysisParameter findAnalysisParameterByAnalysisIdAndParameterId(@Param("analysisId")Long analysisId,@Param("parameterId")Long parameterId);

    /*@Query("SELECT ap.parameter.parameterName FROM AnalysisParameter ap WHERE ap.labAnalysis.id=:analysisId")
    List<String> findAnalysisParameterByAnalysisId(@Param("analysisId")Long analysisId);*/

    @Query("SELECT ap FROM AnalysisParameter ap WHERE ap.id=:id")
    AnalysisParameter findAnalysisParameterById(@Param("id")Long id);

    @Query("SELECT ap.parameter FROM AnalysisParameter ap WHERE ap.labAnalysis.id=:id")
    Page<Parameter> findParametersByAnalysisId(Pageable pageable, @Param("id")Long id);

}

