package raf.bolnica1.patient.security.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDto;


import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabUpdateDto;
import raf.bolnica1.patient.services.PatientCrudService;
import raf.bolnica1.patient.services.PatientService;
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


    @GetMapping("/prescriptions/{lbp}")
    public ResponseEntity<Page<PrescriptionDto>> getPerscriptions(@RequestHeader("Authorization") String authorization, @PathVariable String lbp,
                                                                  @RequestParam Long doctorId,
                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionService.getPrescriptionsForPatient(doctorId, lbp, authorization, page, size), HttpStatus.OK);
    }

    @PostMapping("lab_prescription")
    public ResponseEntity<MessageDto> writeLabPerscription(@RequestBody PrescriptionLabSendDto prescriptionSendDto){
        return new ResponseEntity<>(prescriptionService.sendPersctiption(prescriptionSendDto), HttpStatus.OK);
    }

    @PutMapping("lab_prescription")
    public ResponseEntity<MessageDto> putLabPerscription(@RequestBody PrescriptionLabUpdateDto prescriptionLabUpdateDto){
        return new ResponseEntity<>(prescriptionService.updatePrescription(prescriptionLabUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("lab_prescription/{id}")
    public ResponseEntity<MessageDto> deleteLabPerscription(@PathVariable Long id) {
        return new ResponseEntity<>(prescriptionService.deletePresscription(id), HttpStatus.OK);
    }

    @GetMapping("/find_patient/{lbp}")
    public ResponseEntity<PatientDto> findPatientByLbp(@PathVariable("lbp")String lbp){
        return new ResponseEntity<>(patientCrudService.findPatient(lbp),HttpStatus.OK);
    }


    @GetMapping("/filter_patients")
    public ResponseEntity<Page<PatientDto>> filterPatients(@RequestParam("lbp")String lbp,
                                                             @RequestParam("jmbg")String jmbg,
                                                             @RequestParam("name")String name,
                                                             @RequestParam("surname")String surname,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(patientCrudService.filterPatients(lbp,jmbg,name,surname,page,size),HttpStatus.OK);
    }


    @GetMapping("/admin/test")
    ///@CheckPermission(permissions = {"ADMIN", "MED_SESTRA"})
    public ResponseEntity<String> getMess(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>("super radi!", HttpStatus.OK);
    }



}
