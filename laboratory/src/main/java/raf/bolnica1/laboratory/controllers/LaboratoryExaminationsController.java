package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.services.LabExaminationsService;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/examinations")
@AllArgsConstructor
public class LaboratoryExaminationsController {

    private LabExaminationsService labExaminationsService;


    //////////////////

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<ScheduledLabExaminationDto> createScheduledExamination(@RequestParam("lbp")String lbp, @RequestParam("date")Long scheduledDate,@RequestParam("note")String note,HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.createScheduledExamination(lbp, new Date(scheduledDate), note,request.getHeader("Authorization")),HttpStatus.OK);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<?> changeExaminationStatus(@RequestParam("id")Long id, @RequestParam("newStatus") ExaminationStatus newStatus) {
        return new ResponseEntity<>(labExaminationsService.changeExaminationStatus(id, newStatus), HttpStatus.OK);
    }

    @GetMapping("/count-scheduled_examinations/by-day")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<Integer> countScheduledExaminationsByDay(@RequestParam("date") Long date,HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.listScheduledExaminationsByDay(new Date(date),request.getHeader("Authorization")).size(),HttpStatus.OK);
    }

    @GetMapping("/list-scheduled_examinations/by-day")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<List<ScheduledLabExaminationDto>> listScheduledExaminationsByDay(@RequestParam("date") Long date,HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.listScheduledExaminationsByDay(new Date(date),request.getHeader("Authorization")),HttpStatus.OK);
    }

    @GetMapping("/list-scheduled-examinations")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<?> listScheduledExaminations(HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.listScheduledExaminations(request.getHeader("Authorization")),HttpStatus.OK);
    }


    @GetMapping("/list-scheduled-examinations/by-lbp-date")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<?> listScheduledExaminationsByLbpAndDate(@RequestParam(required = false)String lbp,
                                                                    @RequestParam(required = false)Long startDate,
                                                                    @RequestParam(required = false)Long endDate,
                                                                    @RequestParam(defaultValue = "0")Integer page,
                                                                    @RequestParam(defaultValue = "10")Integer size,
                                                                    HttpServletRequest request) {
        Date sDate=null;
        if(startDate!=null)sDate=new Date(startDate);
        Date eDate=null;
        if(endDate!=null)eDate=new Date(endDate);

        return new ResponseEntity<>(
                labExaminationsService.listScheduledExaminationsByLbpAndDate(lbp,sDate,
                        eDate,request.getHeader("Authorization"),page,size),
                HttpStatus.OK);
    }

}
