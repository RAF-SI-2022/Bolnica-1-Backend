package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    public HospitalRoomDto createHospitalRoom(HospitalRoomCreateDto hospitalRoomCreateDto) {
        HospitalRoom hospitalRoom=hospitalRoomMapper.toEntity(hospitalRoomCreateDto);
        hospitalRoom=hospitalRoomRepository.save(hospitalRoom);
        return hospitalRoomMapper.toDto(hospitalRoom);
    }

    @Override
    public MessageDto deleteHospitalRoom(Long hospitalRoomId) {
        hospitalRoomRepository.deleteById(hospitalRoomId);
        return new MessageDto("HospitalRoom with ID "+hospitalRoomId+" deleted. ");
    }

    @Override
    public Page<HospitalRoomDto> getHospitalRoomsByDepartmentId(Long departmentId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);

        Page<HospitalRoom> hospitalRooms=hospitalRoomRepository.findHospitalRoomsByDepartmentId(pageable,departmentId);

        return hospitalRooms.map(hospitalRoomMapper::toDto);
    }

    @Override
    public HospitalRoomDto getHospitalRoomById(Long hospitalRoomId) {
        HospitalRoom hospitalRoom= hospitalRoomRepository.findHospitalRoomById(hospitalRoomId);
        return hospitalRoomMapper.toDto(hospitalRoom);
    }


}
