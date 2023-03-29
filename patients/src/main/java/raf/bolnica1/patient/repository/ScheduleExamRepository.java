package raf.bolnica1.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.patient.domain.ScheduleExam;

import java.util.Optional;

@Repository
public interface ScheduleExamRepository extends JpaRepository<ScheduleExam, Long> {
    Optional<ScheduleExam> deleteScheduleExamById(Long id);
}
