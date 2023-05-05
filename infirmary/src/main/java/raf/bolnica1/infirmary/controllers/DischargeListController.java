package raf.bolnica1.infirmary.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.dischargeList.CreateDischargeListDto;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.services.DischargeListService;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/dischargeList")
public class DischargeListController {

    private final DischargeListService dischargeListService;

    @PostMapping("/createDischargeList")
    public ResponseEntity<DischargeListDto> createDischargeList(@RequestBody CreateDischargeListDto createDischargeListDto){
        return new ResponseEntity<>(dischargeListService.createDischargeList(createDischargeListDto), HttpStatus.OK);
    }

    @GetMapping("/getDischargeListByHospitalizationId")
    public ResponseEntity<Page<DischargeListDto>> getDischargeListWithFilter(@RequestParam(required = false) Long hospitalizationId,
                                                                             @RequestParam(required = false)Date startDate,
                                                                             @RequestParam(required = false)Date endDate,
                                                                             @RequestParam(required = false)String lbp,
                                                                             @RequestParam(defaultValue = "0")Integer page,
                                                                             @RequestParam(defaultValue = "10")Integer size){
        return new ResponseEntity<>(dischargeListService.getDischargeListWithFilter(hospitalizationId,
                startDate,endDate,lbp,page,size),HttpStatus.OK);
    }

}
