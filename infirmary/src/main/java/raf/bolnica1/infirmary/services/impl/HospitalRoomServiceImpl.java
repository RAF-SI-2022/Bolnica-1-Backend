package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.infirmary.domain.HospitalRoom;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.mapper.HospitalRoomMapper;
import raf.bolnica1.infirmary.repository.HospitalRoomRepository;
import raf.bolnica1.infirmary.services.HospitalRoomService;

@Service
@AllArgsConstructor
public class HospitalRoomServiceImpl implements HospitalRoomService {

    /// MAPPERS
    private final HospitalRoomMapper hospitalRoomMapper;


    /// REPOSITORIES
    private final HospitalRoomRepository hospitalRoomRepository;



    @Override
    //@CacheEvict(value = "hostRooms", allEntries = true)
    public HospitalRoomDto createHospitalRoom(HospitalRoomCreateDto hospitalRoomCreateDto) {
        HospitalRoom hospitalRoom=hospitalRoomMapper.toEntity(hospitalRoomCreateDto);
        hospitalRoom=hospitalRoomRepository.save(hospitalRoom);
        return hospitalRoomMapper.toDto(hospitalRoom);
    }

    @Override
    /*@Caching(evict = {
            @CacheEvict(value = "hospRoom", key = "#hospitalRoomId"),
            @CacheEvict(value = "hostRooms", allEntries = true)
    })*/
    public MessageDto deleteHospitalRoom(Long hospitalRoomId) {
        hospitalRoomRepository.deleteById(hospitalRoomId);
        return new MessageDto("HospitalRoom with ID "+hospitalRoomId+" deleted. ");
    }

    @Override
    //@Cacheable(value = "hostRooms", key = "{#departmentId, #page, #size}")
    public Page<HospitalRoomDto> getHospitalRoomsByDepartmentId(Long departmentId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);

        Page<HospitalRoom> hospitalRooms=hospitalRoomRepository.findHospitalRoomsByDepartmentId(pageable,departmentId);

        return hospitalRooms.map(hospitalRoomMapper::toDto);
    }

    @Override
    @Transactional(timeout = 20)
    //@Cacheable(value = "hospRoom", key = "#hospitalRoomId")
    public HospitalRoomDto getHospitalRoomById(Long hospitalRoomId) {
        HospitalRoom hospitalRoom= hospitalRoomRepository.findHospitalRoomById(hospitalRoomId);
        return hospitalRoomMapper.toDto(hospitalRoom);
    }


}
