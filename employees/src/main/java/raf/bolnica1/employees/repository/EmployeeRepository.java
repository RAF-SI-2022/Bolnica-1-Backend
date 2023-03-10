package raf.bolnica1.employees.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByLbz(String lbz);

    @Query("SELECT e FROM Employee e JOIN Department d " +
            "ON e.department.id = d.id " +
            "WHERE (:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:surname IS NULL OR e.surname LIKE %:surname%) AND " +
            "(:departmentName IS NULL OR d.name LIKE %:departmentName%) AND " +
            "(:hospitalShortName IS NULL OR d.hospital.shortName LIKE %:hospitalShortName%) AND " +
            "(:deleted IS NULL OR e.deleted=:deleted)")
    Page<Employee> listEmployeesWithFilters(Pageable pageable,
                                 @Param("name") String name,
                                 @Param("surname") String surname,
                                 @Param("deleted") boolean deleted,
                                 @Param("departmentName") String departmentName,
                                 @Param("hospitalShortName") String hospitalShortName);

    @Query("SELECT e FROM Employee e JOIN Department d " +
            "ON e.department.id = d.id " +
            "WHERE (:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:surname IS NULL OR e.surname LIKE %:surname%) AND " +
            "(:departmentName IS NULL OR d.name LIKE %:departmentName%) AND " +
            "(:hospitalShortName IS NULL OR d.hospital.shortName LIKE %:hospitalShortName%)")
    Page<Employee> listEmployeesWithFilters(Pageable pageable,
                                            @Param("name") String name,
                                            @Param("surname") String surname,
                                            @Param("departmentName") String departmentName,
                                            @Param("hospitalShortName") String hospitalShortName);

}
