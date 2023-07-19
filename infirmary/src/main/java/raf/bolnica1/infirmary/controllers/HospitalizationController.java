package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.services.HospitalizationService;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/hospitalization")
public class HospitalizationController {

    private final HospitalizationService hospitalizationService;

    @GetMapping("/getHospitalizationsByDepartmentId")
    public ResponseEntity<Page<HospitalizationDto>> getHospitalizationsByDepartmentId(@RequestParam Long departmentId,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(hospitalizationService.getHospitalizationsByDepartmentId(departmentId, page, size), HttpStatus.OK);
    }

    @GetMapping("/getHospitalizationsByHospitalRoomId")
    public ResponseEntity<Page<HospitalizationDto>> getHospitalizationsByHospitalRoomId(@RequestParam Long hospitalRoomId,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(hospitalizationService.getHospitalizationsByHospitalRoomId(hospitalRoomId, page, size), HttpStatus.OK);
    }

    @GetMapping("/getHospitalizationsWithFilter")
    public ResponseEntity<Page<HospitalizationDto>> getHospitalizationsWithFilter(@RequestParam(required = false) String name,
                                                                                  @RequestParam(required = false) String surname,
                                                                                  @RequestParam(required = false) String jmbg,
                                                                                  @RequestParam(required = false) Long departmentId,
                                                                                  @RequestParam(required = false) Long hospitalRoomId,
                                                                                  @RequestParam(required = false) String lbp,
                                                                                  @RequestParam(required = false) Date startDate,
                                                                                  @RequestParam(required = false) Date endDate,
                                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                                  @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(hospitalizationService.getHospitalizationsWithFilter(name, surname, jmbg, departmentId, hospitalRoomId, lbp, startDate, endDate, page, size),HttpStatus.OK);
    }

    @PutMapping("/add_ventilator/{id}")
    public ResponseEntity<MessageDto> addOnVentilator(@PathVariable("id") Long id){
        return new ResponseEntity<>(hospitalizationService.addOnVentilator(id), HttpStatus.OK);
    }

    @PutMapping("/remove_ventilator/{id}")
    public ResponseEntity<MessageDto> removeFromVentilator(@PathVariable("id") Long id){
        return new ResponseEntity<>(hospitalizationService.removeFromVentilator(id), HttpStatus.OK);
    }

    @GetMapping("/is_ventilator/{id}")
    public ResponseEntity<Boolean> isVentilator(@PathVariable("id") Long id){
        return new ResponseEntity<>(hospitalizationService.getVentilator(id), HttpStatus.OK);
    }
}
