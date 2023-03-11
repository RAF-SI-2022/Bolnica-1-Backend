package raf.bolnica1.patient.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.dto.PatientDto;
import raf.bolnica1.patient.services.PatientService;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.services.PatientService;

import javax.validation.Valid;


@RestController
@RequestMapping("/patient")
public class PatientController {


    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //Registracija pacijenta
    @RequestMapping(value="/register",
                    method = RequestMethod.POST,
                    consumes = "application/json",
                    produces = "application/json")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patient){
        patient = patientService.registerPatient(patient);
        if(patient != null)
            return ResponseEntity.ok(patient);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Azuriranje podataka pacijenta

    @RequestMapping(method = RequestMethod.PUT,
                    consumes = "application/json",
                    produces = "application/json")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patient){
        patient = patientService.updatePatient(patient);
        if(patient != null)
            return ResponseEntity.ok(patient);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    //Brisanje pacijenta

    @RequestMapping(value = "/delete/{lbp}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatient(@PathVariable String lbp){
        if(patientService.deletePatient(lbp))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }



    @RequestMapping(value = "/filter",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public ResponseEntity<List<PatientDto>> filterPatients(@Param("lbp")String lbp,
                                                        @Param("jmbg")String jmbg,
                                                        @Param("name")String name,
                                                        @Param("surname")String surname){
        List<PatientDto> patients = patientService.filterPatients(lbp, jmbg, name, surname);
        return ResponseEntity.ok(patients);
    }

    //Pretraga pacijenta
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/find")
    public ResponseEntity<Object> findPatient(@Valid @RequestBody Object object){
        return null;
    }


    //Pretraga pacijenta preko LBP-a
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/find/{lbp}")
    public ResponseEntity<Patient> findPatientLBP(@PathVariable("lbp") String lbp){// @Valid @RequestBody Object object
        //Dohvatanje konkretnog pacijenta preko lbp-a
        Patient patient = patientService.findPatientLBP(lbp);

        //Provera da li pacijent postoji
        if( patient != null){
            return ResponseEntity.ok(patient);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //Dobijanje istorije bolesti pacijenta
    @RequestMapping(value = "/findByDesease")
    public ResponseEntity<?> hisotryOfDeseasePatient(@RequestParam("ppn") Long ppn, @RequestParam("mkb10") Long mkb10){
        return null;
    }


    //Svi izvestaji
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/findReport")
    public ResponseEntity<Object> findReportPatient(@Valid @RequestBody Object object){
        return null;
    }


    //Svi kartoni
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/findMedicalChart/{ppn}")
    public ResponseEntity<Object> findMedicalChartPatient(@PathVariable("ppn") Long ppn, @Valid @RequestBody Object object){
        return null;
    }


    //Krvne grupe
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/findDetails/{ppn}")
    public ResponseEntity<Object> findDetailsPatient(@PathVariable("ppn") Long ppn, @Valid @RequestBody Object object){
        return null;
    }




}
