package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.domain.MedicalRecord;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    Optional<List<MedicalHistory>> findByMedicalRecord_IdAndDiagnosisCode_Id(Long id, Long mkb10);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.medicalRecord=:mr")
    List<MedicalHistory> findMedicalHistoryByMedicalRecord(@Param("mr")MedicalRecord medicalRecord);

}
