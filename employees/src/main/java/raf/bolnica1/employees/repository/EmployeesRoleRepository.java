package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;

import java.util.List;

@Repository
public interface EmployeesRoleRepository extends JpaRepository<EmployeesRole, Long> {
    List<EmployeesRole> findByEmployee(Employee emp);
}
