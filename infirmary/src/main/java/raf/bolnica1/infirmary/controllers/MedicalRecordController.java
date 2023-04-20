package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.services.MedicalRecordService;

@RestController
@AllArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;


    @GetMapping("/getMedicalRecordByLbp")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordByLbp(@RequestParam String lbp,@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(medicalRecordService.getMedicalRecordByLbp(lbp,authorization), HttpStatus.OK);
    }

    @PostMapping("/createExaminationHistory")
    public ResponseEntity<MessageDto> createExaminationHistory(@RequestBody ExaminationHistoryCreateDto examinationHistoryCreateDto){
        return new ResponseEntity<>(medicalRecordService.createExaminationHistory(examinationHistoryCreateDto),HttpStatus.OK);
    }

}
