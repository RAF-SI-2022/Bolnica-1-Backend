package raf.bolnica1.patient.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import raf.bolnica1.patient.checking.CheckPermission;
import raf.bolnica1.patient.dto.MedicalRecordDto;

import raf.bolnica1.patient.domain.ExaminationHistory;
import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.domain.Patient;

import raf.bolnica1.patient.dto.PatientDto;
import raf.bolnica1.patient.dto.PatientDtoDesease;
import raf.bolnica1.patient.dto.PatientDtoReport;
import raf.bolnica1.patient.services.PatientService;

//import java.util.Date;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;


@RestController
@RequestMapping("/patient")
public class PatientController {


    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //Registracija pacijenta
    //priv: visa med sesta, med sestra
    @CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @RequestMapping(value="/register",
                    method = RequestMethod.POST,
                    consumes = "application/json",
                    produces = "application/json")
    public ResponseEntity<PatientDto> registerPatient(@RequestHeader("Authorization") String authorization,
                                                      @RequestBody PatientDto patient){
        patient = patientService.registerPatient(patient);
        if(patient != null)
            return ResponseEntity.ok(patient);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Azuriranje podataka pacijenta
    //visa med sestra, med sestra
    @CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @RequestMapping(method = RequestMethod.PUT,
                    consumes = "application/json",
                    produces = "application/json")
    public ResponseEntity<PatientDto> updatePatient(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody PatientDto patient){
        patient = patientService.updatePatient(patient);
        if(patient != null)
            return ResponseEntity.ok(patient);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    //Brisanje pacijenta
//    priv: visa med sestra
    @CheckPermission(permissions = {"VISA_MED_SESTRA"})
    @RequestMapping(value = "/delete/{lbp}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatient(@RequestHeader("Authorization") String authorization,
                                           @PathVariable String lbp){
        if(patientService.deletePatient(lbp))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }


    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom, visa med sestra, med sestra
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV", "VISA_MED_SESTRA", "MED_SESTRA"})
    @RequestMapping(value = "/filter",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public ResponseEntity<List<PatientDto>> filterPatients(@RequestHeader("Authorization") String authorization,
                                                            @Param("lbp")String lbp,
                                                            @Param("jmbg")String jmbg,
                                                            @Param("name")String name,
                                                            @Param("surname")String surname){
        List<PatientDto> patients = patientService.filterPatients(lbp, jmbg, name, surname);
        return ResponseEntity.ok(patients);
    }

    //Pretraga pacijenta
    //priv:
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/find")
    public ResponseEntity<Object> findPatient(@Valid @RequestBody Object object){
        return null;
    }


    //Pretraga pacijenta preko LBP-a
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV"})
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/find/{lbp}")
    public ResponseEntity<Patient> findPatientLBP(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable("lbp") String lbp){// @Valid @RequestBody Object object
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Patient patient = patientService.findPatientLBP(lbp);

        //Provera da li pacijent postoji
        if( patient != null){
            return ResponseEntity.ok(patient);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //Dobijanje istorije bolesti pacijenta
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV"})
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
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV"})
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


    //Svi kartoni
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV"})
    @GetMapping(
            path = "/findMedicalRecord/{ppn}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalRecordDto>> findMedicalRecordByLbp(@RequestHeader("Authorization") String authorization,
                                                                         @PathVariable("ppn") String lbp){

        //provera jwt tokena zbog privilegija

        return ResponseEntity.ok(patientService.findMedicalRecordByLbp(lbp));
    }


    //Krvne grupe
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC, DR_SPEC_POV"})
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/findDetails/{ppn}")
    public ResponseEntity<Object> findDetailsPatient(@PathVariable("ppn") Long ppn, @Valid @RequestBody Object object){
        return null;
    }

    @GetMapping("/admin/test")
    @CheckPermission(permissions = {"ADMIN", "MED_SESTRA"})
    public ResponseEntity<String> getMess(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>("super radi!", HttpStatus.OK);
    }

}
