package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Department;
import raf.bolnica1.employees.domain.Hospital;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByPbo(String pbo);

    List<Department> findByHospital(Hospital hospital);


    @Query(value = "select d from Department d where d.hospital.pbb = :pbb")
    List<Department> findByHostpitalPbb(@Param("pbb") String pbb);

}
