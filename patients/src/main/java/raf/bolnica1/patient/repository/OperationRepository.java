package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Operation;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT op FROM Operation op WHERE op.medicalRecord=:mr")
    public List<Operation> findOperationsByMedicalRecord(@Param("mr") MedicalRecord medicalRecord);

}
