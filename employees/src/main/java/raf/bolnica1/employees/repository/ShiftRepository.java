package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Shift;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    Optional<Shift> findByShiftNumAndActive(int num, boolean active);
    List<Shift> findByActive(boolean active);

    @Query("SELECT s FROM Shift s WHERE s.startTime <= s.endTime AND s.startTime <= :givenTime AND s.endTime >= :givenTime AND s.active = true " +
            "OR s.startTime > s.endTime AND (s.startTime <= :givenTime OR s.endTime >= :givenTime) AND s.active = true")
    Shift findByTime(@Param("givenTime") Time givenTime);
}
