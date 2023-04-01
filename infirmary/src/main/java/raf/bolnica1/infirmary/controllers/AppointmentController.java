package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;
import raf.bolnica1.infirmary.services.AppointmentService;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {

    AppointmentService appointmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAppointment(@RequestHeader("Authorization") String authorization, @RequestBody ScheduleAppointmentDto appointmentDto){
        return new ResponseEntity<>(appointmentService.createAppointment(authorization, appointmentDto), HttpStatus.OK);
    }

}
