package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;

@RestController
@RequestMapping("/work-orders")
@AllArgsConstructor
public class LaboratoryWorkOrdersController {

    private final LabWorkOrdersService labWorkOrdersService;

    //Ovako sam preveo uput posto nisam siguran da li postoji bolji naziv za to u engleskom jeziku
    @PostMapping("/create/{guidance_id}")
    public ResponseEntity<?> createWorkOrder(@PathVariable("guidance_id") Long id) {
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

    @PutMapping("/{workOrderId}/{parameterAnalysisId}/update")
    @PreAuthorize("hasAnyRole('ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<?> updateAnalysisParameters(@PathVariable Long workOrderId, @PathVariable Long parameterAnalysisId, @RequestParam String result) {
        return new ResponseEntity<>(labWorkOrdersService.updateAnalysisParameters(workOrderId, parameterAnalysisId, result), HttpStatus.OK);
    }

    @GetMapping("/{workOrderId}/results")
    @PreAuthorize("hasAnyRole('ROLE_DR_SPEC_ODELJENJA', 'ROLE_DR_SPEC', 'ROLE_DR_SPEC_POV', 'ROLE_LAB_TEHNICAR', 'ROLE_VISI_LAB_TEHNICAR', 'ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<?> findAnalysisParametersResults(@PathVariable Long workOrderId) {
        return new ResponseEntity<>(labWorkOrdersService.findParameterAnalysisResultsForWorkOrder(workOrderId), HttpStatus.FOUND);
    }

    @GetMapping("/verify/{id}")
    @PreAuthorize("hasRole('ROLE_SPEC_MED_BIOHEMIJE')")
    public ResponseEntity<?> verifyWorkOrder(@PathVariable Long id) {
        return new ResponseEntity<>(labWorkOrdersService.verifyWorkOrder(id), HttpStatus.OK);
    }

}
