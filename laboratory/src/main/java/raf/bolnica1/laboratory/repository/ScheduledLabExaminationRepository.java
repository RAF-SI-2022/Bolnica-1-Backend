package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;

@Repository
public interface ScheduledLabExaminationRepository extends JpaRepository<ScheduledLabExamination, Long> {
}
