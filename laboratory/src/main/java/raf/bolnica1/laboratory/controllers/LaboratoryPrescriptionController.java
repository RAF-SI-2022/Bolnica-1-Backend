package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;

@RestController
@RequestMapping("/prescription")
@AllArgsConstructor
public class LaboratoryPrescriptionController {

    private PrescriptionRecieveService prescriptionRecieveService;

    @GetMapping("/{id}/get/{lbp}")
    public ResponseEntity<Page<PrescriptionDto>> getPrescriptionsForPatientByLbz(@PathVariable("id") Long doctorId, @PathVariable("lbp") String lbp,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionRecieveService.findPrescriptionsForPatient(lbp, doctorId, page, size), HttpStatus.OK);
    }

}
