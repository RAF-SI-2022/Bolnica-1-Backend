package raf.bolnica1.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.laboratory.domain.lab.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query("SELECT p FROM Parameter p WHERE p.id=:id")
    Parameter findParameterById(@Param("id") Long id);

}
