package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.HospitalRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRoomRepository extends JpaRepository<HospitalRoom, Long> {
    Optional<List<HospitalRoom>> findAllByIdDepartment(Long idDepartment);
}
