package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;

import java.util.Optional;

@Repository
public interface LabWorkOrderRepository extends JpaRepository<LabWorkOrder, Long> {

    @Query("SELECT l FROM LabWorkOrder l WHERE l.prescription.id = :id")
    Optional<LabWorkOrder> findByPrescription(@Param("id") Long id);

}
