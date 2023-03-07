package raf.bolnica1.patient.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {




    //Registracija pacijenta
    public ResponseEntity<Object> registerPatient(Object object){

        return (ResponseEntity<Object>) object;
    }


    //Azuriranje podataka pacijenta
    @RequestMapping(value = "/{path}")
    public ResponseEntity<?> updatePatient(Object object){
        return (ResponseEntity) object;
    }


    //Brisanje pacijenta
    @RequestMapping(value = "/delete/{path}")
    public ResponseEntity<?> deletePatient(Object object){
        return (ResponseEntity) object;
    }


    //Pretraga pacijenta
    @RequestMapping(value = "/find")
    public ResponseEntity<?> findPatient(Object object){
        return (ResponseEntity) object;
    }


    //Pretraga pacijenta preko LBP-a
    @RequestMapping(value = "/find/{path}")
    public ResponseEntity<?> findPatientLBP(Object object){
        return (ResponseEntity) object;
    }


    //Dobijanje istorije bolesti pacijenta
    @RequestMapping(value = "/findByDesease/{query}")
    public ResponseEntity<?> hisotryOfDeseasePatient(Object object){
        return (ResponseEntity) object;
    }


    //Svi izvestaji
    @RequestMapping(value = "/findReport")
    public ResponseEntity<?> findReportPatient(Object object){
        return (ResponseEntity) object;
    }


    //Svi kartoni
    @RequestMapping(value = "/findMedicalChart/{path}")
    public ResponseEntity<?> findMedicalChartPatient(Object object){
        return (ResponseEntity) object;
    }


    //Krvne grupe
    @RequestMapping(value = "/findDetails/{path}")
    public ResponseEntity<?> findDetailsPatient(Object object){
        return (ResponseEntity) object;
    }




}
