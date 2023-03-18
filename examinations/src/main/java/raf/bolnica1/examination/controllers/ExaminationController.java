package raf.bolnica1.examination.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.examination.services.ExaminationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/examinations")
public class ExaminationController {

    private ExaminationService service;

    @Autowired
    public ExaminationController(ExaminationService examinationService) {
        this.service = examinationService;
    }

    //Kreiranje zakazanog pregleda
    @PostMapping(path = "/create")
    public ResponseEntity<Object> createScheduledExamination(@RequestBody Object object) {

        return null;
    }

    //Pretraga zakazanih pregleda
    @PostMapping(path = "/find")
    public ResponseEntity<Object> findScheduledExamination(@Valid @RequestBody Object object) {
        // find all examinations based on LBZ
        // check whether examinationDate exists
        // if yes return only examinations on that date ( or none if they don't exist )
        // otherwise return all examinations of that doctor ( by LBZ ) starting with the today's date
        /*
            care for Date examinationDate
            if it can't be properly converted to Java Data, consider changing the type into string

            pristup :
            - Načelnik odeljenja
            - Doktor spec
            - Doktor spec. sa poverljivim pristupom
            - Viša medicinska sestra
            - Medicinska sestra
         */
        return null;
    }

    // Azuriranje statusa pacijenta
    @PutMapping(path = "/patient/{id}")
    public ResponseEntity<Object> updatePatientArrivalStatus(@PathVariable("id") Long id, @Valid @RequestBody Object object){
        // find examination based on id from path
        // update status based on status string from path
        // if status == "Otkazao"
        // update state of examination to "Otkazano"
        // use updateExaminationStatus function for this
        // needs to be implemented
        /*
            pristup :
            - Viša medicinska sestra
            - Medicinska sestra
         */

        return null;
    }

    // Azuriranje statusa pregelda
    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateExaminationStatus(@PathVariable("id") Long id, @Valid @RequestBody Object object){
        // find examination based on id from path
        // update status based on status string from path
        // if status == "U toku"
        // update state of patient to "Primljen"
        // if status == "Zavrseno"
        // update state of patient to "Zavrsio"
        // use updatePatientArrivalStatus function for this
        // needs to be implemented
        /*
            pristup :
            - Načelnik odeljenja
            - Doktor spec
            - Doktor spec. sa poverljivim pristupom
            - Viša medicinska sestra
            - Medicinska sestra
         */

        return null;
    }

    //Brisanje zakazanog pregleda
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteScheduledExamination(@PathVariable("id") Long id) {
        // Delete a scheduled examination
        // The examination is found using the examination id sent in the path
        /*
            pristup :
            - Viša medicinska sestra
            - Medicinska sestra
            - Recepcioner
         */
        return null;
    }

    //Pretraga lekara po odeljenju
    @GetMapping(path = "/find_doctor_by_department/{pbo}")
    public ResponseEntity<Object> findDoctorSpecByDepartment(@PathVariable("pbo") Long pbo, @Valid @RequestBody Object object){
        // Return a list of doctors specialists that are employed at the given department
        // Department is found by PBO ( unique department id )
        /*
            pristup :
            - Načelnik odeljenja
            - Doktor spec
            - Doktor spec. sa poverljivim pristupom
            - Viša medicinska sestra
            - Medicinska sestra
         */

        return null;
    }

}
