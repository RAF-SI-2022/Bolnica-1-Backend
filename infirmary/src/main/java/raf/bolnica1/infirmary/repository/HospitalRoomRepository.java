package raf.bolnica1.infirmary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRoomRepository extends JpaRepository<HospitalRoom, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT hr FROM HospitalRoom hr WHERE hr.id=:id")
    HospitalRoom findHospitalRoomById(@Param("id") Long id);


    @Query("SELECT hr FROM HospitalRoom hr WHERE (:id IS NULL OR hr.idDepartment=:id)")
    Page<HospitalRoom> findHospitalRoomsByDepartmentId(Pageable pageable,@Param("id") Long departmentId);

}
