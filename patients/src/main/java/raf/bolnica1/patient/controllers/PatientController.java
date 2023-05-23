package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.*;


import raf.bolnica1.patient.services.PatientCrudService;
import raf.bolnica1.patient.services.PatientService;
import raf.bolnica1.patient.services.PrescriptionService;

import java.sql.Date;


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
    @PreAuthorize("hasAnyRole( 'ROLE_MED_SESTRA', 'ROLE_VISA_MED_SESTRA', 'ROLE_RECEPCIONER' )")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientCreateDto patient){
        return new ResponseEntity<>(this.patientCrudService.registerPatient(patient), HttpStatus.OK);
    }

    //Azuriranje podataka pacijenta
    //visa med sestra, med sestra
    //@CheckPermission(permissions = {"MED_SESTRA", "VISA_MED_SESTRA"})
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole( 'ROLE_MED_SESTRA', 'ROLE_VISA_MED_SESTRA', 'ROLE_RECEPCIONER' )")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientUpdateDto patientCreateDto){
        return new ResponseEntity<>(this.patientCrudService.updatePatient(patientCreateDto), HttpStatus.OK);
    }


    //Brisanje pacijenta
//    priv: visa med sestra
    /// @CheckPermission(permissions = {"VISA_MED_SESTRA"})
    @DeleteMapping("/delete/{lbp}")
    @PreAuthorize("hasAnyRole( 'ROLE_VISA_MED_SESTRA' )")
    public ResponseEntity<MessageDto> deletePatient(@PathVariable String lbp){
        return new ResponseEntity<>(patientCrudService.deletePatient(lbp), HttpStatus.OK);
    }

    @GetMapping("/find_patient/{lbp}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV', 'ROLE_MED_SESTRA', 'ROLE_VISA_MED_SESTRA' , 'ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<PatientDto> findPatientByLbp(@PathVariable("lbp")String lbp){
        return new ResponseEntity<>(patientCrudService.findPatient(lbp),HttpStatus.OK);
    }


    @GetMapping("/filter_patients")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV', 'ROLE_MED_SESTRA', 'ROLE_VISA_MED_SESTRA', 'ROLE_RECEPCIONER', 'ROLE_VISI_LAB_TEHNICAR', 'ROLE_LAB_TEHNICAR', 'ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<Page<PatientDto>> filterPatients(@RequestParam(required = false)String lbp,
                                                             @RequestParam(required = false)String jmbg,
                                                             @RequestParam(required = false)String name,
                                                             @RequestParam(required = false)String surname,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "2") Integer size
    ){
        return new ResponseEntity<>(patientCrudService.filterPatients(lbp,jmbg,name,surname,page,size),HttpStatus.OK);
    }


    @GetMapping("/examinations/{lbp}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<ExamsForPatientDto> patientExams(@PathVariable("lbp") String lbp){
        return new ResponseEntity<>(patientService.getExamsForPatient(lbp), HttpStatus.OK);
    }

}
