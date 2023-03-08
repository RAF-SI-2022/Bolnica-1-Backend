package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
