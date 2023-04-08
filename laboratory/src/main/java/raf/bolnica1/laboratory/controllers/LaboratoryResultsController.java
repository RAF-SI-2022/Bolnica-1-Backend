package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.services.lab.LabResultService;

@RestController
@AllArgsConstructor
@RequestMapping("/results")
public class LaboratoryResultsController {

    private final LabResultService labResultService;

    @PostMapping("/updateResults")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<MessageDto> updateResults(@RequestBody ResultUpdateDto resultUpdateDto){
        return new ResponseEntity<>( labResultService.updateResults(resultUpdateDto) , HttpStatus.OK);
    }

    @PutMapping("/commitResults")
    @PreAuthorize("hasAnyRole('ROLE_VISI_LAB_TEHNICAR', 'ROLE_MED_BIOHEMICAR')")
    public ResponseEntity<MessageDto> commitResults(@RequestParam Long workOrderId){
        return new ResponseEntity<>( labResultService.commitResults(workOrderId) , HttpStatus.OK);
    }


}
