package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.employee.EmployeeDto;
import raf.bolnica1.laboratory.services.employee.EmployeeService;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/examinations")
@AllArgsConstructor
public class LaboratoryExaminationsController {

    private final EmployeeService employeeService;

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

    @GetMapping("/create")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> createScheduledExamination() {
        return null;
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> changeExaminationStatus() {
        return null;
    }

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
    @GetMapping("/count-scheduled_examinations/by-day")
    public ResponseEntity<?> listScheduledExaminationsByDay(@RequestParam("date") Long date) {
        return null;
    }

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
    @GetMapping("/list-scheduled-examinations")
    public ResponseEntity<?> listScheduledExaminations() {
        return null;
    }

}
