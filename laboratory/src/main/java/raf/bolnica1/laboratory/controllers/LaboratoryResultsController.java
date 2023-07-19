package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.LabResultService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/results")
public class LaboratoryResultsController {

    private final LabResultService labResultService;
    private final ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    @PostMapping("/updateResults")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<MessageDto> updateResults(@RequestBody ResultUpdateDto resultUpdateDto){
        return new ResponseEntity<>( labResultService.updateResults(resultUpdateDto) , HttpStatus.OK);
    }

    @PutMapping("/commitResults")
    @PreAuthorize("hasAnyRole('ROLE_VISI_LAB_TEHNICAR', 'ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<MessageDto> commitResults(@RequestParam Long workOrderId){
        return new ResponseEntity<>( labResultService.commitResults(workOrderId) , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParameterAnalysisResult>> getResults(@RequestParam Long workOrderId){
        return new ResponseEntity<>(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(workOrderId) , HttpStatus.OK);
    }


}
