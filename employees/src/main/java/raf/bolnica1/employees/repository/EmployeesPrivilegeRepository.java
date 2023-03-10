package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.EmployeesPrivilege;

@Repository
public interface EmployeesPrivilegeRepository extends JpaRepository<EmployeesPrivilege, Long> {
}
