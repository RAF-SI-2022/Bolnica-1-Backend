package raf.bolnica1.employees.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.employees.dto.employee.EmployeeMessageDto;
import raf.bolnica1.employees.dto.shift.*;
import raf.bolnica1.employees.services.ShiftService;

import java.sql.Date;

@RestController
@RequestMapping("/shift")
@AllArgsConstructor
public class ShiftController {

    private ShiftService shiftService;

    @PostMapping
    public ResponseEntity<ShiftDto> addShift(@RequestBody ShiftCreateDto shiftCreateDto){
        return new ResponseEntity<>(shiftService.createShift(shiftCreateDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ShiftDto> getShift(@RequestParam int num){
        return new ResponseEntity<>(shiftService.getShift(num), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<AllShiftsDto> getAllShift(){
        return new ResponseEntity<>(shiftService.all(), HttpStatus.OK);
    }

    @PostMapping("/schedule")
    public ResponseEntity<ShiftScheduleDto> addShiftSchedule(@RequestBody ShiftScheduleCreateDto shiftScheduleCreateDto){
        return new ResponseEntity<>(shiftService.createShiftSchedule(shiftScheduleCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeMessageDto> removeShiftSchedule(@PathVariable("id") Long id){
        return new ResponseEntity<>(shiftService.removeShiftSchedule(id), HttpStatus.OK);
    }

    @GetMapping("/schedule/all")
    public ResponseEntity<Page<ShiftScheduleDto>> getAllShiftSchedule(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<>(shiftService.scheduleAll(startDate, endDate, page, size), HttpStatus.OK);
    }

    @GetMapping("/schedule/{lbz}")
    public ResponseEntity<Page<ShiftScheduleDto>> getShiftSchedule(
            @PathVariable("lbz") String lbz,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return new ResponseEntity<>(shiftService.scheduleEmployee(lbz, startDate, endDate, page, size), HttpStatus.OK);
    }

    @GetMapping("/working/{lbz}")
    public ResponseEntity<Boolean> isWorking(@PathVariable("lbz") String lbz, @RequestParam Date date,  @RequestParam String time){
        return new ResponseEntity<>(shiftService.isDoctorWorking(lbz, time, date), HttpStatus.OK);
    }
}
