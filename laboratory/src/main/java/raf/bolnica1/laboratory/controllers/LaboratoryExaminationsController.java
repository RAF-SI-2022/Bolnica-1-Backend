package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examinations")
@AllArgsConstructor
public class LaboratoryExaminationsController {

    @PostMapping("/create")
    public ResponseEntity<?> createScheduledExamination() { return null; }

    @PutMapping("/update_status")
    public ResponseEntity<?> changeExaminationStatus() { return null; }

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
    @GetMapping("/count_scheduled_examinations/by_day")
    public ResponseEntity<?> listScheduledExaminationsByDay(@RequestParam("date") Long date) { return null; }

    //Razmisli o prosledjivanju datuma kao "query" parametar u vidu milisekundi
    @GetMapping("/list_scheduled_examinations")
    public ResponseEntity<?> listScheduledExaminations() { return null; }

}
