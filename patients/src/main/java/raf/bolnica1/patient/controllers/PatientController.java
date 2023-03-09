package raf.bolnica1.patient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.services.PatientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService service;

    @Autowired
    public PatientController(PatientService patientService) {
        this.service = patientService;
    }

    //Registracija pacijenta
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerPatient(Object object){
        return null;
    }

    //Azuriranje podataka pacijenta
    //ppn = personal patient number
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,path = "/{ppn}")
    public ResponseEntity<Object> updatePatient(@PathVariable("ppn") Long ppn, @Valid @RequestBody Object object){
        return null;
    }


    //Brisanje pacijenta
    @DeleteMapping(value = "/delete/{ppn}")
    public ResponseEntity<Object> deletePatient(@PathVariable("ppn") Long ppn){
        return null;
    }


    //Pretraga pacijenta
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/find")
    public ResponseEntity<Object> findPatient(@Valid @RequestBody Object object){
        return null;
    }


    //Pretraga pacijenta preko LBP-a
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/find/{ppn}")
    public ResponseEntity<Object> findPatientLBP(@PathVariable("ppn") Long ppn, @Valid @RequestBody Object object){
        return null;
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
