package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.dto.employee.EmployeeDto;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.services.employee.EmployeeService;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/examinations")
@AllArgsConstructor
public class LaboratoryExaminationsController {

    private final EmployeeService employeeService;

    private LabExaminationsService labExaminationsService;

    /**
     * Test za komunikaciju
     */
    @GetMapping("/get-employee")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<EmployeeDto> getEmployee(@RequestParam("lbz") String lbz, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return employeeService.getEmployee(lbz, authorizationHeader);
    }

    //////////////////

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<MessageDto> createScheduledExamination(@RequestParam("lbp")String lbp, @RequestParam("date")Long scheduledDate,@RequestParam("note")String note,HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.createScheduledExamination(lbp, new Date(scheduledDate), note,request.getHeader("Authorization")),HttpStatus.OK);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<?> changeExaminationStatus(@RequestParam("id")Long id, @RequestParam("newStatus") ExaminationStatus newStatus) {
        return new ResponseEntity<>(labExaminationsService.changeExaminationStatus(id, newStatus), HttpStatus.OK);
    }

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
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

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
    @GetMapping("/list-scheduled-examinations")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR','ROLE_VISI_LAB_TEHNICAR')")
    public ResponseEntity<?> listScheduledExaminations(HttpServletRequest request) {
        return new ResponseEntity<>(labExaminationsService.listScheduledExaminations(request.getHeader("Authorization")),HttpStatus.OK);
    }

}
