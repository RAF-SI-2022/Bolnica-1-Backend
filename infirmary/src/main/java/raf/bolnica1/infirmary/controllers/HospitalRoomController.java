package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomCreateDto;
import raf.bolnica1.infirmary.dto.hospitalRoom.HospitalRoomDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.services.HospitalRoomService;

@RestController
@AllArgsConstructor
@RequestMapping("/hospitalRoom")
public class HospitalRoomController {

    private final HospitalRoomService hospitalRoomService;

    @PostMapping("/createHospitalRoom")
    public ResponseEntity<HospitalRoomDto> createHospitalRoom(@RequestBody HospitalRoomCreateDto hospitalRoomCreateDto){
        return new ResponseEntity<>(hospitalRoomService.createHospitalRoom(hospitalRoomCreateDto), HttpStatus.OK);
    }

    @PutMapping("/deleteHospitalRoom")
    public ResponseEntity<MessageDto> deleteHospitalRoom(@RequestParam Long hospitalRoomId){
        return new ResponseEntity<>(hospitalRoomService.deleteHospitalRoom(hospitalRoomId),HttpStatus.OK);
    }

    @GetMapping("/getHospitalRoomsByDepartmentId")
    public ResponseEntity<Page<HospitalRoomDto>> getHospitalRoomsByDepartmentId(@RequestParam Long departmentId,
                                                                                @RequestParam(defaultValue = "0")Integer page,
                                                                                @RequestParam(defaultValue = "2")Integer size){
        return new ResponseEntity<>(hospitalRoomService.getHospitalRoomsByDepartmentId(departmentId, page, size),HttpStatus.OK);
    }

    @GetMapping("/getHospitalRoomsById")
    public ResponseEntity<HospitalRoomDto> getHospitalRoomsById(@RequestParam Long id){
        return new ResponseEntity<>(hospitalRoomService.getHospitalRoomById(id),HttpStatus.OK);
    }

}
