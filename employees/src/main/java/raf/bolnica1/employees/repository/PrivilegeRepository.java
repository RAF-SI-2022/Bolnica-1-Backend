package raf.bolnica1.employees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.employees.domain.Privilege;
import raf.bolnica1.employees.domain.PrivilegeShort;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByprivilegeShort(PrivilegeShort privilegeShort);
}
