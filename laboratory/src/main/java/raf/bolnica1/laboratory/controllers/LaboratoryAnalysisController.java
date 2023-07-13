package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.services.LabAnalysisService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/analysis")
public class LaboratoryAnalysisController {

    private LabAnalysisService labAnalysisService;

    @PostMapping("/createLabAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<LabAnalysisDto> createLabAnalysis(@RequestBody LabAnalysisDto dto) {
        return new ResponseEntity<>(labAnalysisService.createLabAnalysis(dto), HttpStatus.OK);
    }

    @PostMapping("/updateLabAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<LabAnalysisDto> updateLabAnalysis(@RequestBody LabAnalysisDto dto) {
        return new ResponseEntity<>(labAnalysisService.updateLabAnalysis(dto), HttpStatus.OK);
    }

    @DeleteMapping("/deleteLabAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<MessageDto> deleteLabAnalysis(@RequestParam("id") Long id) {
        return new ResponseEntity<>(labAnalysisService.deleteLabAnalysis(id), HttpStatus.OK);
    }

    @GetMapping("/getLabAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<LabAnalysisDto> getLabAnalysis(@RequestParam("id") Long id) {
        return new ResponseEntity<>(labAnalysisService.getLabAnalysis(id), HttpStatus.OK);
    }

    @GetMapping("/getAllLabAnalysis")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR','ROLE_DR_SPEC_ODELJENJA','ROLE_DR_SPEC','ROLE_DR_SPEC_POV')")
    public ResponseEntity<List<LabAnalysisDto>> getAllLabAnalysis(@RequestParam boolean covid) {
        return new ResponseEntity<>(labAnalysisService.getAllLabAnalysis(covid), HttpStatus.OK);
    }



}
