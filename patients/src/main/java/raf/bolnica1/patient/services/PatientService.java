package raf.bolnica1.patient.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class PatientService {


    //Registracija pacijenta
    public static ResponseEntity<Object> registerPatient(Object object){
        return (ResponseEntity<Object>) object;
    }

    //Azuriranje podataka pacijenta
    public static ResponseEntity<?> updatePatient(Object object){
        return (ResponseEntity) object;
    }


    //Brisanje pacijenta
    public static ResponseEntity<?> deletePatient(Object object){
        return (ResponseEntity) object;
    }


    //Pretraga pacijenta
    public static ResponseEntity<?> findPatient(Object object){
        return (ResponseEntity) object;
    }


    //Pretraga pacijenta preko LBP-a
    public static ResponseEntity<?> findPatientLBP(Object object){
        return (ResponseEntity) object;
    }


    //Dobijanje istorije bolesti pacijenta
    public static ResponseEntity<?> hisotryOfDeseasePatient(Object object){
        return (ResponseEntity) object;
    }


    //Svi izvestaji
    public static ResponseEntity<?> findReportPatient(Object object){
        return (ResponseEntity) object;
    }


    //Svi kartoni
    public static ResponseEntity<?> findMedicalChartPatient(Object object){
        return (ResponseEntity) object;
    }


    //Krvne grupe
    public static ResponseEntity<?> findDetailsPatient(Object object){
        return (ResponseEntity) object;
    }



}
