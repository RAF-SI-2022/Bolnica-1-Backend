package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.dto.visit.VisitDto;
import raf.bolnica1.infirmary.services.VisitService;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/visit")
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/createVisit")
    public ResponseEntity<VisitDto> createVisit(@RequestBody VisitCreateDto visitCreateDto){
        return new ResponseEntity<>(visitService.createVisit(visitCreateDto), HttpStatus.OK);
    }

    @GetMapping("/getVisitsWithFilter")
    public ResponseEntity<Page<VisitDto>> getVisitsWithFilter(@RequestParam(required = false) Long departmentId,
                                                              @RequestParam(required = false) Long hospitalRoomId,
                                                              @RequestParam(required = false) Long hospitalizationId,
                                                              @RequestParam(required = false) Date startDate,
                                                              @RequestParam(required = false) Date endDate,
                                                              @RequestParam(defaultValue ="0") Integer page,
                                                              @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(visitService.getVisitsWithFilter(departmentId, hospitalRoomId, hospitalizationId, startDate, endDate, page, size),HttpStatus.OK);
    }

}
