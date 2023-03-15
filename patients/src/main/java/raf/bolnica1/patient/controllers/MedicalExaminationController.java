package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.services.MedicalExaminationService;

@RestController
@RequestMapping("/examination")
@AllArgsConstructor
public class MedicalExaminationController {

    private MedicalExaminationService medicalExaminationService;

    @PostMapping("/{lbp}")
    public ResponseEntity<ExaminationHistoryDto> createExaminationHistory(@PathVariable String lbp, @RequestBody ExaminationHistoryCreateDto examinationHistoryCreateDto){
        return new ResponseEntity<>(medicalExaminationService.addExamination(lbp, examinationHistoryCreateDto), HttpStatus.OK);
    }

    @PostMapping("/diagnosis_history/{lbp}")
    public ResponseEntity<MedicalHistoryDto> createDiagnosisHistory(@PathVariable String lbp, @RequestBody MedicalHistoryDto medicalHistoryDto){
        return new ResponseEntity<>(medicalExaminationService.addMedicalHistory(lbp, medicalHistoryDto), HttpStatus.OK);
    }


}
