package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.lab.PatientDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDoneDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDto;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;

import java.util.ArrayList;

@RestController
@RequestMapping("/prescription")
@AllArgsConstructor
public class LaboratoryPrescriptionController {

    private PrescriptionRecieveService prescriptionRecieveService;

    @GetMapping("/{id}/get/{lbp}")
    public ResponseEntity<Page<PrescriptionDto>> getPrescriptionsForPatientByLbz(@PathVariable("id") String doctorLbz, @PathVariable("lbp") String lbp,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionRecieveService.findPrescriptionsForPatient(lbp, doctorLbz, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}/get_patient/{lbp}")
    public ResponseEntity<Page<PrescriptionDto>> getPrescriptionsForPatient(@PathVariable("lbp") String lbp,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionRecieveService.findPrescriptionsForPatientNotRealized(lbp, page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}/get_rest/{lbp}")
    public ResponseEntity<ArrayList<PrescriptionDto>> getPrescriptionsForPatientByLbzRest(@PathVariable("id") String doctorLbz, @PathVariable("lbp") String lbp){
        return new ResponseEntity<>(prescriptionRecieveService.findPrescriptionsForPatientRest(lbp, doctorLbz), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDoneDto> getPrescription(@PathVariable("id") Long id){
        return new ResponseEntity<>(prescriptionRecieveService.findPrescription(id), HttpStatus.OK);
    }

    @GetMapping("/patients_lab")
    public ResponseEntity<Page<PatientDto>> getPatients(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionRecieveService.findPatients(page, size), HttpStatus.OK);
    }

}
