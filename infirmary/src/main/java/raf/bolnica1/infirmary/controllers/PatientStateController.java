package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.services.PatientStateService;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/patientState")
public class PatientStateController {

    private final PatientStateService patientStateService;

    @PostMapping("/createPatientState")
    public ResponseEntity<PatientStateDto> createPatientState(@RequestBody PatientStateCreateDto patientStateCreateDto){
        return new ResponseEntity<>(patientStateService.createPatientState(patientStateCreateDto), HttpStatus.OK);
    }

    @GetMapping("/getPatientStateByDate")
    public ResponseEntity<Page<PatientStateDto>> getPatientStateByDate(@RequestParam(required = false) Long hospitalizationId,
                                                                       @RequestParam(required = false) Date startDate,
                                                                       @RequestParam(required = false) Date endDate,
                                                                       @RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(patientStateService.getPatientStateByDate(hospitalizationId, startDate, endDate, page, size),HttpStatus.OK);
    }

}
