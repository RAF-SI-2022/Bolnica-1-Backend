package raf.bolnica1.infirmary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRoomRepository extends JpaRepository<HospitalRoom, Long> {
    Optional<List<HospitalRoom>> findAllByIdDepartment(Long idDepartment);

    Optional<HospitalRoom> findByIdDepartment(Long idDepartment);

    @Transactional
    @Modifying
    @Query("UPDATE HospitalRoom SET capacity = capacity - 1 WHERE idDepartment = :idDepartment")
    void decrementCapasity(@Param("idDepartment") Long idDepartment);

    @Transactional
    @Modifying
    @Query("UPDATE HospitalRoom SET capacity = capacity + 1 WHERE idDepartment = :idDepartment")
    void incrementCapasity(@Param("idDepartment") Long idDepartment);
}
