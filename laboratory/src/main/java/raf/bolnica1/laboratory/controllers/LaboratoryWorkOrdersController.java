package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-orders")
@AllArgsConstructor
public class LaboratoryWorkOrdersController {

    //Ovako sam preveo uput posto nisam siguran da li postoji bolji naziv za to u engleskom jeziku
    @PostMapping("/create/{guidance_id}")
    public ResponseEntity<?> createWorkOrder(@PathVariable("guidance_id") Long id) {
        return null;
    }

    @PutMapping("/work_order_verification/{work_order_id}")
    public ResponseEntity<?> workOrderVerification(@PathVariable("work_order_id") Long id) {
        return null;
    }

    @PutMapping("/update-analysis-parameters")
    public ResponseEntity<?> updateAnalysisParameters() {
        return null;
    }

    @GetMapping("/work-orders-history")
    public ResponseEntity<?> workOrdersHistory() {
        return null;
    }

    @GetMapping("/find-work-orders/by-lab")
    public ResponseEntity<?> findWorkOrdersByLab() {
        return null;
    }

    @GetMapping("/find-results-for-analysis-parameters")
    public ResponseEntity<?> findAnalysisParametersResults() {
        return null;
    }

}
