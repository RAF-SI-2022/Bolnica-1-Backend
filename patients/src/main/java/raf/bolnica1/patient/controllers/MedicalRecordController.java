package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.domain.DiagnosisCode;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.services.MedicalRecordService;

import java.util.List;

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
    public ResponseEntity<OperationDto> addOperation(@PathVariable String lbp, @RequestBody OperationCreateDto operationCreateDto){
        return new ResponseEntity<>(medicalRecordService.addOperation(lbp, operationCreateDto), HttpStatus.OK);
    }

    @PostMapping("/vaccine/{lbp}")
    public ResponseEntity<MessageDto> addVaccine(@RequestParam String lbp, @RequestBody VaccinationDataDto vaccinationDataDto){
        return new ResponseEntity<>(medicalRecordService.addVaccine(lbp, vaccinationDataDto), HttpStatus.OK);
    }

    @PostMapping("/allergy/{lbp}")
    public ResponseEntity<MessageDto> addAllergy(@RequestParam String lbp, @RequestParam String allergy){
        return new ResponseEntity<>(medicalRecordService.addAllergy(lbp, allergy), HttpStatus.OK);
    }

    @GetMapping("/gather_allergies")
    public ResponseEntity<List<AllergyDto>> gatherAllergies(){
        return new ResponseEntity<>(medicalRecordService.gatherAllergies(), HttpStatus.OK);
    }

    @GetMapping("/gather_vaccines")
    public ResponseEntity<List<VaccinationDto>> gatherVaccines(){
        return new ResponseEntity<>(medicalRecordService.gatherVaccines(), HttpStatus.OK);
    }

    @GetMapping("/gather_diagnosis")
    public ResponseEntity<List<DiagnosisCodeDto>> gatherDiagnosis(){
        return new ResponseEntity<>(medicalRecordService.gatherDiagnosis(), HttpStatus.OK);
    }



}
