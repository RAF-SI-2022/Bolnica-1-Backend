package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.VisitDto;
import raf.bolnica1.infirmary.services.VisitService;

@RestController
@RequestMapping("/visit")
@AllArgsConstructor
public class VisitController {

    VisitService visitService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/registerVisit")
    public ResponseEntity<String> registerPatientVisit(@RequestHeader("Authorization") String authorization, @RequestBody VisitDto visitDto) {
        return new ResponseEntity<>(visitService.registerPatientVisit(authorization, visitDto), HttpStatus.OK);
    }

}
