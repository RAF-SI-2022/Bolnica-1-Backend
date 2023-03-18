package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
