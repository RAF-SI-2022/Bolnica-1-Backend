package raf.bolnica1.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.stats.CovidStats;

import java.sql.Date;
import java.util.Optional;

public interface CovidStatsRepository extends JpaRepository<CovidStats, Long> {
    Optional<CovidStats> findByDateAndGenderAndAgeCategory(Date date, Gender gender, int age);

    @Query("SELECT c FROM CovidStats c " +
            "WHERE (:start IS NULL OR c.date >= :start) AND " +
            "(:end IS NULL OR c.date <= :end) AND " +
            "(:gender IS NULL OR c.gender=:gender) AND " +
            "(:age = 0 OR c.ageCategory = :age)")
    Page<CovidStats> findByRequests(Pageable pageable, @Param("start") Date startDate, @Param("end") Date endDate, @Param("gender") Gender gender, @Param("age") int ageCategory);


}