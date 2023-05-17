package raf.bolnica1.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.Date;

@Repository
public interface LabWorkOrderRepository extends JpaRepository<LabWorkOrder, Long> {

    @Query("SELECT l FROM LabWorkOrder l WHERE l.prescription.id = :id")
    Optional<LabWorkOrder> findByPrescription(@Param("id") Long id);

    @Query("SELECT lw FROM LabWorkOrder lw " +
            "WHERE (:lbp IS NULL OR lw.lbp = :lbp) " +
            "AND (:fromDate IS NULL OR lw.creationDateTime>=:fromDate)" +
            "AND (:toDate IS NULL OR lw.creationDateTime<:toDate) " +
            "AND (:status IS NULL OR lw.status = :status)")
    Page<LabWorkOrder> findWorkOrdersByLab(
            Pageable pageable,
            @Param("lbp") String lbp,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("status") OrderStatus status);

    @Query("SELECT lw FROM LabWorkOrder lw " +
            "WHERE (:lbp IS NULL OR lw.lbp = :lbp) " +
            "AND (:fromDate IS NULL OR lw.creationDateTime>=:fromDate)" +
            "AND (:toDate IS NULL OR lw.creationDateTime<:toDate) " +
            "AND (lw.status IS 'U_OBRADI' OR lw.status = 'OBRADJEN')")
    Page<LabWorkOrder> workOrdersHistory(
            Pageable pageable,
            @Param("lbp") String lbp,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lwo FROM LabWorkOrder lwo WHERE lwo.id=:id")
    LabWorkOrder findLabWorkOrderById(@Param("id") Long id);

}
