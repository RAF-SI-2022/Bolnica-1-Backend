package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import raf.bolnica1.patient.checking.CheckPermission;

import raf.bolnica1.patient.domain.Patient;


import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientGeneralDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.services.PatientCrudService;
import raf.bolnica1.patient.services.PatientService;

//import java.util.Date;
import java.sql.Date;
import java.util.List;

import org.springframework.http.MediaType;

import javax.validation.Valid;


@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;
    private PatientCrudService patientCrudService;

    //Registracija pacijenta
    //priv: visa med sesta, med sestra
    //@CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @PostMapping("/register")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientCreateDto patient){
        return new ResponseEntity<>(this.patientCrudService.registerPatient(patient), HttpStatus.OK);
    }

    //Azuriranje podataka pacijenta
    //visa med sestra, med sestra
    //@CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @PutMapping("/update")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientUpdateDto patientCreateDto){
        return new ResponseEntity<>(this.patientCrudService.updatePatient(patientCreateDto), HttpStatus.OK);
    }


    //Brisanje pacijenta
//    priv: visa med sestra
    /// @CheckPermission(permissions = {"VISA_MED_SESTRA"})
    @DeleteMapping("/delete/{lbp}")
    public ResponseEntity<MessageDto> deletePatient(@PathVariable String lbp){
        return new ResponseEntity<>(patientCrudService.deletePatient(lbp), HttpStatus.OK);
    }

    @GetMapping("/find_patient/{lbp}")
    public ResponseEntity<PatientDto> findPatientByLbp(@PathVariable("lbp")String lbp){
        return new ResponseEntity<>(patientCrudService.findPatient(lbp),HttpStatus.OK);
    }


    @GetMapping("/filter_patients")
    public ResponseEntity<Page<PatientDto>> findPatientByLbp(@RequestParam("lbp")String lbp,
                                                             @RequestParam("jmbg")String jmbg,
                                                             @RequestParam("name")String name,
                                                             @RequestParam("surname")String surname,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(patientCrudService.filterPatients(lbp,jmbg,name,surname,page,size),HttpStatus.OK);
    }

/**


    //Dobijanje istorije bolesti pacijenta
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC", "DR_SPEC_POV"})
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,value = "/findByDesease")
    public ResponseEntity<List<PatientDtoDesease>> hisotryOfDeseasePatient(@RequestHeader("Authorization") String authorization,
                                                                           @Param("lbp")String lbp,
                                                                           @Param("mkb10")Long mkb10){
        //Dohvatanje istorija bolesti preko lbpa-a pacijenta i preko mkb10 (dijagnoza)
        List<PatientDtoDesease> medicalHistory = patientService.hisotryOfDeseasePatient(lbp,mkb10);

        //Provera da li postoji bolest
        if( medicalHistory != null){
            return ResponseEntity.ok(medicalHistory);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }


    //Svi izvestaji
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC", "DR_SPEC_POV"})
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/findReport")
    public ResponseEntity<?> findReportPatient(@RequestHeader("Authorization") String authorization,
                                               @Param("lbp") String lbp,
                                               @Param("currDate") Date currDate,
                                               @Param("fromDate") Date fromDate,
                                               @Param("toDate") Date toDate){

        //Provera da li se vrsi pretraga preko konkretnog datuma ili preko raspona datuma od-do
        if(currDate != null && fromDate == null && toDate == null){
            //Pretraga preko konkretnog datuma i lbp-a pacijenta
            List<PatientDtoReport> examinationHistory = patientService.findReportPatientByCurrDate(lbp,currDate);

            //Provera da li postoji lista izvestaja ako postoji onda ih vracamo ako ne onda vracamo null
            if(examinationHistory != null){
                return ResponseEntity.ok(examinationHistory);
            }else{
                return null;
            }


        }else if(currDate == null && fromDate != null && toDate != null){
            //Pretraga preko raspona datuma od-do i lbp-a pacijenta
            List<PatientDtoReport> examinationHistory = patientService.findReportPatientByFromAndToDate(lbp,fromDate,toDate);

            //Provera da li postoji lista izvestaja ako postoji onda ih vracamo ako ne onda vracamo null
            if(examinationHistory != null){
                return ResponseEntity.ok(examinationHistory);
            }else{
                return null;
            }
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


*/


    @GetMapping("/admin/test")
    @CheckPermission(permissions = {"ADMIN", "MED_SESTRA"})
    public ResponseEntity<String> getMess(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>("super radi!", HttpStatus.OK);
    }



}
