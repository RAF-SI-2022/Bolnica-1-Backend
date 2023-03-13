package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.Allergy;
import raf.bolnica1.patient.domain.AllergyData;
import raf.bolnica1.patient.domain.GeneralMedicalData;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergyDataRepository extends JpaRepository<AllergyData, Long> {

    @Query("SELECT r.allergy FROM AllergyData r WHERE r.generalMedicalData=:gmd")
    public List<Allergy> findAllergiesByGeneralMedicalData(@Param("gmd") GeneralMedicalData generalMedicalData);

}
