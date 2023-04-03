package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.services.MedicalExaminationService;
import raf.bolnica1.patient.services.PatientService;

import java.util.List;

@RestController
@RequestMapping("/examination")
@AllArgsConstructor
public class MedicalExaminationController {

    private MedicalExaminationService medicalExaminationService;
    private PatientService patientService;

    @PostMapping("/{lbp}")
    public ResponseEntity<ExaminationHistoryDto> createExaminationHistory(@PathVariable String lbp, @RequestBody ExaminationHistoryCreateDto examinationHistoryCreateDto){
        return new ResponseEntity<>(medicalExaminationService.addExamination(lbp, examinationHistoryCreateDto), HttpStatus.OK);
    }

    @PostMapping("/diagnosis_history/{lbp}")
    public ResponseEntity<MedicalHistoryDto> createDiagnosisHistory(@PathVariable String lbp, @RequestBody MedicalHistoryCreateDto medicalHistoryCreateDto){
        return new ResponseEntity<>(medicalExaminationService.addMedicalHistory(lbp, medicalHistoryCreateDto), HttpStatus.OK);
    }

    //Kreiranje zakazanog pregleda
    @PostMapping(path = "/create")
    public ResponseEntity<Object> createScheduledExamination(@RequestBody ScheduleExamCreateDto scheduleExamCreateDto) {
        return new ResponseEntity<>(patientService.schedule(scheduleExamCreateDto), HttpStatus.CREATED);
    }

    //Pretraga zakazanih pregleda
    @GetMapping(path = "/find/{lbz}")
    public ResponseEntity<Page<ScheduleExamDto>> findScheduledExaminationForDoctor(@PathVariable String lbz, @RequestParam int page, @RequestParam int size) {
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
        return new ResponseEntity<>(patientService.findScheduledExaminationsForDoctor(lbz, page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/find_all_today")
    public ResponseEntity<Page<ScheduleExamDto>> findScheduledExaminationForDay(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(patientService.findScheduledExaminationsForMedSister(page, size), HttpStatus.OK);
    }

    // Azuriranje statusa pacijenta
    @PutMapping(path = "/patient/{id}")
    /// @PreAuthorize("hasRole('ROLE_MED_SESTRA', 'ROLE_VISA_MED_SESTRA')")
    public ResponseEntity<Object> updatePatientArrivalStatus(@PathVariable("id") Long id,@RequestParam("pa") PatientArrival status){
        return new ResponseEntity<>(patientService.updatePatientArrivalStatus(id, status), HttpStatus.OK);
    }


    // Azuriranje statusa pregelda
    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateExaminationStatus(@PathVariable("id") Long id, @RequestBody Object object){
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
        return new ResponseEntity<>(patientService.deleteScheduledExamination(id),HttpStatus.OK);
    }

    //Pretraga lekara po odeljenju
    @GetMapping(path = "/find_doctor_by_department/{pbo}")
    public ResponseEntity<List<EmployeeDto>> findDoctorSpecByDepartment(@PathVariable("pbo") String pbo, @RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(patientService.findDoctorSpecByDepartment(pbo,authorization),HttpStatus.OK);
    }
}
