package raf.bolnica1.laboratory.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.services.ParameterService;

@RestController
@AllArgsConstructor
@RequestMapping("/parameter")
public class LaboratoryParameterController {

    private final ParameterService parameterService;

    @PostMapping("/createParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<ParameterDto> createParameter(@RequestBody ParameterDto parameterDto){
        return new ResponseEntity<>( parameterService.createParameter(parameterDto) , HttpStatus.OK);
    }

    @PostMapping("/updateParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<ParameterDto> updateParameter(@RequestBody ParameterDto parameterDto){
        return new ResponseEntity<>( parameterService.updateParameter(parameterDto) , HttpStatus.OK);
    }

    @DeleteMapping("/deleteParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<MessageDto> deleteParameter(@RequestParam("id") Long id){
        return new ResponseEntity<>( parameterService.deleteParameter(id) , HttpStatus.OK);
    }

    @GetMapping("/getParameter")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<ParameterDto> getParameter(@RequestParam("id") Long id){
        return new ResponseEntity<>( parameterService.getParameter(id) , HttpStatus.OK);
    }

}
