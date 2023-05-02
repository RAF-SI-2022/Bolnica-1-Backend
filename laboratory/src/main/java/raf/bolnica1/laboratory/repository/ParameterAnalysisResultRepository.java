package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParameterAnalysisResultRepository extends JpaRepository<ParameterAnalysisResult, Long> {

    @Query("SELECT par FROM ParameterAnalysisResult par WHERE par.labWorkOrder.id = :workOrderId AND par.labWorkOrder.status IN (:allowedStatuses)")
    List<ParameterAnalysisResult> findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(@Param("workOrderId") Long workOrderId, @Param("allowedStatuses") List<OrderStatus> allowedStatuses);

    List<ParameterAnalysisResult> findParameterAnalysisResultsByLabWorkOrderId(Long id);


    Optional<ParameterAnalysisResult> findByLabWorkOrderIdAndAnalysisParameterId(Long workOrderId, Long analysisParameterId);

    @Query("SELECT par FROM ParameterAnalysisResult par WHERE par.labWorkOrder.id=:lid AND par.analysisParameter.id=:aid")
    ParameterAnalysisResult findParameterAnalysisResultByLabWorkOrderIdAndAnalysisParameterId(@Param("lid") Long labWorkOrderId,
                                                                                              @Param("aid") Long analysisParameterId);

    @Query("SELECT count(par) FROM ParameterAnalysisResult par WHERE par.labWorkOrder.id=:id AND par.result IS NULL")
    Integer countParameterAnalysisResultWithNullResultByLabWorkOrderId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from ParameterAnalysisResult par where par.analysisParameter.id = :analysisParameterId")
    void deleteByAnalysisParameterId(@Param("analysisParameterId") Long analysisParameterId);
}
