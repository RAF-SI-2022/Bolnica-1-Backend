package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.services.FindInfoService;

import java.sql.Date;
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

    @GetMapping("/myFindMedicalHistoriesPaged/{lbp}")
    public ResponseEntity<Page<MedicalHistoryDto>> getMedicalHistoryByLbpPaged(@PathVariable String lbp,
                                                                                     @RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(findInfoService.findMedicalHistoryByLbpPaged(lbp,page,size),HttpStatus.OK);
    }

    @GetMapping("/myFindMedicalHistoriesByDiagnosisCodePaged/{lbp}")
    public ResponseEntity<Page<MedicalHistoryDto>> getMedicalHistoryByLbpAndDiagnosisCodePaged(@PathVariable String lbp,
                                                                                                     @RequestParam("diagnosisCode") String code,
                                                                                                     @RequestParam(defaultValue = "0") Integer page,
                                                                                                     @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(findInfoService.findMedicalHistoryByLbpAndDiagnosisCodePaged(lbp,code,page,size),HttpStatus.OK);
    }

    @GetMapping("/myFindExaminationHistoriesByLbpAndDatePaged/{lbp}")
    public ResponseEntity<Page<ExaminationHistoryDto>> getMedicalExaminationByLbpAndDatePaged(@PathVariable String lbp,
                                                                                                            @RequestParam("date") Date date,
                                                                                                            @RequestParam(defaultValue = "0") Integer page,
                                                                                                            @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp,date,date,page,size),HttpStatus.OK);
    }

    @GetMapping("/myFindExaminationHistoriesByLbpAndDateRangePaged/{lbp}")
    public ResponseEntity<Page<ExaminationHistoryDto>> getMedicalExaminationByLbpAndDateRangePaged(@PathVariable String lbp,
                                                                                                         @RequestParam("start_date") Date startDate,
                                                                                                         @RequestParam("end_date") Date endDate,
                                                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                                                         @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(findInfoService.findExaminationHistoryByLbpAndDateRangePaged(lbp,startDate,endDate,page,size),HttpStatus.OK);
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
