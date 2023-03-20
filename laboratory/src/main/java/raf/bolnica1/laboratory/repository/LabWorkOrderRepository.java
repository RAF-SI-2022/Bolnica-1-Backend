package raf.bolnica1.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;

@Repository
public interface LabWorkOrderRepository extends JpaRepository<LabWorkOrder, Long> {

    @Query("SELECT lw FROM LabWorkOrder lw " +
            "WHERE (lw.lbp = :lbp) AND " +
            "(lw.creationDateTime BETWEEN :fromDate AND :toDate) AND " +
            "(lw.status = :status)")
    Page<LabWorkOrder> findWorkOrdersByLab(
            Pageable pageable,
            @Param("lbp") String lbp,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate,
            @Param("status") OrderStatus status);
}
