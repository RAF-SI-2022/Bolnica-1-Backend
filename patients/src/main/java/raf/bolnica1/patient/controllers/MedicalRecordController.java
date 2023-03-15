package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;
import raf.bolnica1.patient.dto.general.OperationDto;
import raf.bolnica1.patient.services.MedicalRecordService;

@RestController
@RequestMapping("/record")
@AllArgsConstructor
public class MedicalRecordController {

    private MedicalRecordService medicalRecordService;

    @PostMapping("/general_medical_data/{lbp}")
    public ResponseEntity<GeneralMedicalDataDto> createMedicalData(@PathVariable String lbp, @RequestBody GeneralMedicalDataCreateDto generalMedicalDataCreateDto){
        return new ResponseEntity<>(medicalRecordService.addGeneralMedicalData(lbp, generalMedicalDataCreateDto), HttpStatus.OK);
    }

    @PostMapping("/operation/{lbp}")
    public ResponseEntity<OperationDto> createOperation(@PathVariable String lbp, @RequestBody OperationCreateDto operationCreateDto){
        return new ResponseEntity<>(medicalRecordService.addOperation(lbp, operationCreateDto), HttpStatus.OK);
    }

}
