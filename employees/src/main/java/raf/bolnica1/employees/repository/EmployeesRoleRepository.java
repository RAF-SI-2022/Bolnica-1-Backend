package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.EmployeesRole;
import raf.bolnica1.employees.domain.constants.RoleShort;

import java.util.List;

@Repository
public interface EmployeesRoleRepository extends JpaRepository<EmployeesRole, Long> {
    List<EmployeesRole> findByEmployee(Employee emp);

    @Query("SELECT er.employee.name,er.employee.surname,er.employee.lbz,er.employee.department.id FROM EmployeesRole er WHERE er.employee.department.pbo=:pbo AND er.role.roleShort IN :roleList")
    List<Object[]> findEmployeesByDepartmentPboAndRoleList(@Param("pbo") String pbo, @Param("roleList")List<RoleShort> roleList);
}
