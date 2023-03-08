package raf.bolnica1.patient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import raf.bolnica1.patient.dto.PatientDto;
import raf.bolnica1.patient.services.PatientService;

import java.util.List;

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
    @RequestMapping()
    public ResponseEntity<?> updatePatient(Object object){
        return (ResponseEntity) object;
    }


    //Brisanje pacijenta
    @RequestMapping(value = "/delete/{path}")
    public ResponseEntity<?> deletePatient(Object object){
        return (ResponseEntity) object;
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
