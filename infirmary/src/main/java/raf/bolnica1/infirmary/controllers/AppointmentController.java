package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;
import raf.bolnica1.infirmary.services.AppointmentService;

import java.sql.Date;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {

    AppointmentService appointmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAppointment(@RequestBody ScheduleAppointmentDto appointmentDto){
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDto), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAppointment(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("status") String status,
            @RequestParam("id") Integer id
    ){
        return new ResponseEntity<>(appointmentService.updateAppointment(authorization, status, id), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ScheduleAppointmentDto>> getScheduledAppointments(
                                                            @RequestParam("depId") Long depId,
                                                            @RequestParam("lbp") String lbp,
                                                            @RequestParam(value = "date", defaultValue = "1900-01-01") Date date,
                                                            @RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(appointmentService.getScheduledAppointments(depId, lbp, date, page, size), HttpStatus.OK);
    }

}
