package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationCreateDto;
import raf.bolnica1.infirmary.dto.hospitalization.HospitalizationDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.services.AdmissionService;

import java.sql.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/admission")
public class AdmissionController {

    private final AdmissionService admissionService;

    @PostMapping("/createHospitalizaion")
    public ResponseEntity<HospitalizationDto> createHospitalization(@RequestBody HospitalizationCreateDto hospitalizationCreateDto,@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(admissionService.createHospitalization(hospitalizationCreateDto,authorization), HttpStatus.OK);
    }

    @PostMapping("/createSCheduledAppointment")
    public ResponseEntity<ScheduledAppointmentDto> createScheduledAppointment(@RequestBody ScheduledAppointmentCreateDto scheduledAppointmentCreateDto){
        return new ResponseEntity<>(admissionService.createScheduledAppointment(scheduledAppointmentCreateDto),HttpStatus.OK);
    }

    @GetMapping("/findScheduledAppointmentWithFilter")
    public ResponseEntity<Page<ScheduledAppointmentDto>> findScheduledAppointmentWithFilter(@RequestParam(required = false) String lbp,
                                                                                            @RequestParam(required = false) Long departmentId,
                                                                                            @RequestParam(required = false) Date startDate,
                                                                                            @RequestParam(required = false) Date endDate,
                                                                                            @RequestParam(required = false) AdmissionStatus admissionStatus,
                                                                                            @RequestParam(defaultValue = "0") Integer page,
                                                                                            @RequestParam(defaultValue = "2") Integer size){
        System.out.println(lbp);
        System.out.println(departmentId);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(admissionStatus);
        return new ResponseEntity<>(admissionService.getScheduledAppointmentsWithFilter(lbp, departmentId, startDate, endDate, admissionStatus, page, size),HttpStatus.OK);
    }

    @GetMapping("/findScheduledAppointmentByPrescriptionId")
    public ResponseEntity<ScheduledAppointmentDto> findScheduledAppointmentByPrescriptionId(@RequestParam Long prescriptionId){
        return new ResponseEntity<>(admissionService.getScheduledAppointmentByPrescriptionId(prescriptionId),HttpStatus.OK);
    }

    @GetMapping("/findPrescriptionsWithFilter")
    public ResponseEntity<Page<PrescriptionDto>> findPrescriptionsWithFilter(@RequestParam(required = false) String lbp,
                                                                             @RequestParam(required = false) Long departmentId,
                                                                             @RequestParam(required = false) PrescriptionStatus prescriptionStatus,
                                                                             @RequestParam(defaultValue = "0") Integer page,
                                                                             @RequestParam(defaultValue = "2") Integer size){
        return new ResponseEntity<>(admissionService.getPrescriptionsWithFilter(lbp, departmentId, prescriptionStatus, page, size),HttpStatus.OK);
    }


    @PutMapping("/setScheduledAppointmentStatus")
    public ResponseEntity<MessageDto> setScheduledAppointmentStatus(@RequestParam Long scheduledAppointmentId,
                                                                    @RequestParam AdmissionStatus admissionStatus){
        return new ResponseEntity<>(admissionService.setScheduledAppointmentStatus(scheduledAppointmentId, admissionStatus),HttpStatus.OK);
    }

}
