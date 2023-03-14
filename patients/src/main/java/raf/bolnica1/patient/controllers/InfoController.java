package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.services.FindInfoService;

import java.util.List;

@RestController
@RequestMapping("/info")
@AllArgsConstructor
public class InfoController {

    private FindInfoService findInfoService;

    @GetMapping("/myFindGMD/{lbp}")
    public ResponseEntity<GeneralMedicalDataDto> getGeneralMedicalDataByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(findInfoService.findGeneralMedicalDataByLbp(lbp), HttpStatus.OK);
    }

    @GetMapping("/myFindOperations/{lbp}")
    public ResponseEntity<List<OperationDto>> getOperationsByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(findInfoService.findOperationsByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindMedicalHistories/{lbp}")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistoryByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(findInfoService.findMedicalHistoryByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindExaminationHistories/{lbp}")
    public ResponseEntity<List<ExaminationHistoryDto>> getExaminationHistoryByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(findInfoService.findExaminationHistoryByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindMedicalRecord/{lbp}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(findInfoService.findMedicalRecordByLbp(lbp),HttpStatus.OK);
    }
}
