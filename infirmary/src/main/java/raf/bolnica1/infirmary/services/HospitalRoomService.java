package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;

public interface HospitalRoomService {


    HospitalRoomDto createHospitalRoom(HospitalRoomCreateDto hospitalRoomCreateDto);

    MessageDto deleteHospitalRoom(Long hospitalRoomId);

    Page<HospitalRoomDto> getHospitalRoomsByDepartmentId(Long departmentId,Integer page,Integer size);

    HospitalRoomDto getHospitalRoomById(Long hospitalRoomId);


}
