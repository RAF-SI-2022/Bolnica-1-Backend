package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.dto.prescription.PrescriptionDto;
import raf.bolnica1.patient.services.PatientCrudService;
import raf.bolnica1.patient.services.PatientService;

//import java.util.Date;

import raf.bolnica1.patient.services.PrescriptionService;


@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;
    private PatientCrudService patientCrudService;
    private PrescriptionService prescriptionService;

    //Registracija pacijenta
    //priv: visa med sesta, med sestra
    //@CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @PostMapping("/register")
    public ResponseEntity<PatientDto> registerPatient(
                                                      @RequestBody PatientCreateDto patient){
        return new ResponseEntity<>(this.patientCrudService.registerPatient(patient), HttpStatus.OK);
    }

    //Azuriranje podataka pacijenta
    //visa med sestra, med sestra
    //@CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @PutMapping
    public ResponseEntity<PatientDto> updatePatient(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody PatientUpdateDto patientCreateDto){
        return new ResponseEntity<>(this.patientCrudService.updatePatient(patientCreateDto), HttpStatus.OK);
    }


    //Brisanje pacijenta
//    priv: visa med sestra
    /// @CheckPermission(permissions = {"VISA_MED_SESTRA"})
    @DeleteMapping("/delete/{lbp}")
    public ResponseEntity<MessageDto> deletePatient(@RequestHeader("Authorization") String authorization,
                                           @PathVariable String lbp){
        return new ResponseEntity<>(patientCrudService.deletePatient(lbp), HttpStatus.OK);
    }

    @GetMapping("/prescriptions/{lbp}")
    public ResponseEntity<Page<PrescriptionDto>> getPerscriptions(@RequestHeader("Authorization") String authorization, @PathVariable String lbp,
                                                                  @RequestParam Long doctorId,
                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionService.getPrescriptionsForPatient(doctorId, lbp, authorization, page, size), HttpStatus.OK);
    }

    @PostMapping("prescription")
    public ResponseEntity<MessageDto> writePerscription(@RequestBody PrescriptionCreateDto perscriptionCreateDto){
        return new ResponseEntity<>(prescriptionService.sendPersctiption(perscriptionCreateDto), HttpStatus.OK);
    }

    @PutMapping("prescription")
    public ResponseEntity<MessageDto> putPerscription(@RequestBody PrescriptionCreateDto perscriptionCreateDto){
        return new ResponseEntity<>(prescriptionService.sendPersctiption(perscriptionCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("prescription/{id}")
    public ResponseEntity<MessageDto> deletePerscription(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        return new ResponseEntity<>(prescriptionService.deletePresscription(id, authorization), HttpStatus.OK);
    }

/**
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom, visa med sestra, med sestra
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC", "DR_SPEC_POV", "VISA_MED_SESTRA", "MED_SESTRA"})
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
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC", "DR_SPEC_POV"})
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/find/{lbp}")
    public ResponseEntity<Patient> findPatientLBP(@RequestHeader("Authorization") String authorization,
                                                  @PathVariable("lbp") String lbp){// @Valid @RequestBody Object object
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Patient patient = patientService.findDomainPatientLBP(lbp);

        //Provera da li pacijent postoji
        if( patient != null){
            return ResponseEntity.ok(patient);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

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


    //Svi kartoni
    //priv: nacelnik odeljenja, doktor spec, doktor spec sa poverljivim pristupom
    @CheckPermission(permissions = {"DR_SPEC_ODELJENJA", "DR_SPEC", "DR_SPEC_POV"})
    @GetMapping(
            path = "/findMedicalRecord/{ppn}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LightMedicalRecordDto> findLightMedicalRecordByLbp(@RequestHeader("Authorization") String authorization,
                                                                             @PathVariable("ppn") String lbp){

        return ResponseEntity.ok(patientService.findLightMedicalRecordByLbp(lbp));
    }



    @GetMapping("/admin/test")
    @CheckPermission(permissions = {"ADMIN", "MED_SESTRA"})
    public ResponseEntity<String> getMess(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>("super radi!", HttpStatus.OK);
    }

    @GetMapping("/myFindGMD/{lbp}")
    public ResponseEntity<GeneralMedicalDataDto> getGeneralMedicalDataByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findGeneralMedicalDataByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindOperations/{lbp}")
    public ResponseEntity<List<OperationDto>> getOperationsByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findOperationsByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindMedicalHistories/{lbp}")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistoryByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findMedicalHistoryByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindExaminationHistories/{lbp}")
    public ResponseEntity<List<ExaminationHistoryDto>> getExaminationHistoryByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findExaminationHistoryByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindPatient/{lbp}")
    public ResponseEntity<PatientDto> getPatientByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findPatientByLbp(lbp),HttpStatus.OK);
    }

    @GetMapping("/myFindMedicalRecord/{lbp}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordByLbp(@PathVariable String lbp){
        return new ResponseEntity<>(patientService.findMedicalRecordByLbp(lbp),HttpStatus.OK);
    }
*/

}
