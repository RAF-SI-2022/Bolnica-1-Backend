package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDoneDto;
import raf.bolnica1.patient.dto.prescription.infirmary.PrescriptionInfirmarySendDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabUpdateDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionNewDto;
import raf.bolnica1.patient.services.PrescriptionService;

import java.sql.Date;

@RestController
@RequestMapping("/prescription")
@AllArgsConstructor
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @GetMapping("/prescriptions/{lbp}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<Page<PrescriptionNewDto>> getPerscriptionsForPatientByDoctor(@RequestHeader("Authorization") String authorization, @PathVariable String lbp,
                                                                                            @RequestParam String lbz,
                                                                                            @RequestParam(defaultValue = "0") Integer page,
                                                                                            @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionService.getPrescriptionsForPatient(lbz, lbp, authorization, page, size), HttpStatus.OK);
    }

    @PostMapping("lab_prescription")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<MessageDto> writeLabPerscription(@RequestBody PrescriptionLabSendDto prescriptionSendDto){
        return new ResponseEntity<>(prescriptionService.sendPersctiption(prescriptionSendDto), HttpStatus.OK);
    }

    @PostMapping("infirmary_prescription")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV')")
    public ResponseEntity<MessageDto> writeInfirmaryPerscription(@RequestBody PrescriptionInfirmarySendDto prescriptionSendDto){
        return new ResponseEntity<>(prescriptionService.sendPersctiption(prescriptionSendDto), HttpStatus.OK);
    }

    @PutMapping("lab_prescription")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV' )")
    public ResponseEntity<MessageDto> putLabPerscription(@RequestBody PrescriptionLabUpdateDto prescriptionLabUpdateDto){
        return new ResponseEntity<>(prescriptionService.updatePrescription(prescriptionLabUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("lab_prescription/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV' )")
    public ResponseEntity<MessageDto> deleteLabPerscription(@PathVariable Long id) {
        return new ResponseEntity<>(prescriptionService.deletePresscription(id), HttpStatus.OK);
    }

    @GetMapping("/done_prescriptions/{lbp}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV' )")
    public ResponseEntity<Page<PrescriptionDoneDto>> getAllDonePrescriptionsByDatePeriod(@RequestParam Long dateFrom,
                                                                                         @RequestParam Long dateTo,
                                                                                         @PathVariable String lbp,
                                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                                         @RequestParam(defaultValue = "10") Integer size){
        return new ResponseEntity<>(prescriptionService.getAllDonePrescriptionsForPatient(lbp, new Date(dateFrom), new Date(dateTo), page, size), HttpStatus.OK);
    }

    @GetMapping("/prescription/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC' , 'ROLE_DR_SPEC_POV' )")
    public ResponseEntity<PrescriptionDoneDto> getPrescription(@PathVariable("id") Long prescriptionId, @RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(prescriptionService.getPrescription(prescriptionId, authorization), HttpStatus.OK);
    }

}
