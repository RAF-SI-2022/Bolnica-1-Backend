package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
