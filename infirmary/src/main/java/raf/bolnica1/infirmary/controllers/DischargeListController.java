package raf.bolnica1.infirmary.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.services.DischargeListService;

@RestController
@AllArgsConstructor
@RequestMapping("/dischargeList")
public class DischargeListController {

    private final DischargeListService dischargeListService;

    @PostMapping("/createDischargeList")
    public ResponseEntity<DischargeListDto> createDischargeList(@RequestBody DischargeListDto dischargeListDto){
        return new ResponseEntity<>(dischargeListService.createDischargeList(dischargeListDto), HttpStatus.OK);
    }

    @GetMapping("/getDischargeListByHospitalizationId")
    public ResponseEntity<DischargeListDto> getDischargeListByHospitalizationId(@RequestParam Long hospitalizationId){
        return new ResponseEntity<>(dischargeListService.getDischargeListByHospitalizationId(hospitalizationId),HttpStatus.OK);
    }

}
