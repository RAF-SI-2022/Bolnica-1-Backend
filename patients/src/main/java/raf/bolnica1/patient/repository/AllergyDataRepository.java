package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.AllergyData;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergyDataRepository extends JpaRepository<AllergyData, Long> {

    List<AllergyData> findAllByGeneralMedicalDataId(Long id);
}
