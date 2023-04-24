package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.services.AnalysisParameterService;

@RestController
@AllArgsConstructor
@RequestMapping("/analysisParameter")
public class LaboratoryAnalysisParameterController {

    private final AnalysisParameterService analysisParameterService;


    @PostMapping("/createAnalysisParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<AnalysisParameterDto> createAnalysisParameter(@RequestBody AnalysisParameterDto dto) {
        return new ResponseEntity<>(analysisParameterService.createAnalysisParameter(dto), HttpStatus.OK);
    }

    @PostMapping("/updateAnalysisParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<AnalysisParameterDto> updateAnalysisParameter(@RequestBody AnalysisParameterDto dto) {
        return new ResponseEntity<>(analysisParameterService.updateAnalysisParameter(dto), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAnalysisParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<MessageDto> deleteAnalysisParameter(@RequestParam("id") Long id) {
        return new ResponseEntity<>(analysisParameterService.deleteAnalysisParameter(id), HttpStatus.OK);
    }

    @GetMapping("/getAnalysisParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR','ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC', 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<AnalysisParameterDto> getAnalysisParameter(@RequestParam("id") Long id) {
        return new ResponseEntity<>(analysisParameterService.getAnalysisParameter(id), HttpStatus.OK);
    }


    @GetMapping("/getParametersByAnalysisId")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR', 'ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC', 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<Page<ParameterDto>> getParameterByAnalysisId(@RequestParam("id") Long id,
                                                                       @RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "2") Integer size) {
        return new ResponseEntity<>(analysisParameterService.getParametersByAnalysisId(id,page,size), HttpStatus.OK);
    }

}
