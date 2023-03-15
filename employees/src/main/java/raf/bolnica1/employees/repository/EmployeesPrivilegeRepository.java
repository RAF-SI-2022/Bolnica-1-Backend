package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesPrivilege;

import java.util.List;

@Repository
public interface EmployeesPrivilegeRepository extends JpaRepository<EmployeesPrivilege, Long> {
    List<EmployeesPrivilege> findByEmployee(Employee emp);
}
