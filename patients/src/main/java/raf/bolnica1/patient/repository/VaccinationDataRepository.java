package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.Vaccination;
import raf.bolnica1.patient.domain.VaccinationData;

import java.util.List;
import java.util.Optional;

import raf.bolnica1.patient.domain.AllergyData;
import raf.bolnica1.patient.domain.VaccinationData;

import java.util.List;


@Repository
public interface VaccinationDataRepository extends JpaRepository<VaccinationData, Long> {


    @Query("SELECT r.vaccination,r.vaccinationDate FROM VaccinationData r WHERE r.generalMedicalData=:gmd")
    public List<Object[]> findVaccinationsByGeneralMedicalData(@Param("gmd")GeneralMedicalData generalMedicalData);


    List<VaccinationData> findAllByGeneralMedicalDataId(Long id);

}
