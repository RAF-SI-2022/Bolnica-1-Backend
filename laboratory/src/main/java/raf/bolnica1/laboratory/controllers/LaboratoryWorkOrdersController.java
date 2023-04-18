package raf.bolnica1.laboratory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;

import java.sql.Date;

@RestController
@RequestMapping("/work-orders")
@AllArgsConstructor
public class LaboratoryWorkOrdersController {

    private final LabWorkOrdersService labWorkOrdersService;

    @PutMapping("/register_patient_arrival")
    public ResponseEntity<MessageDto> registerPatient(@RequestParam String lbp){
        return new ResponseEntity<>(labWorkOrdersService.registerPatient(lbp), HttpStatus.OK);
    }


    @GetMapping("/work_orders_history")
    public ResponseEntity<Page<LabWorkOrder>> workOrdersHistory(
            @RequestParam String lbp,
            @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size)  {
        return new ResponseEntity<>(labWorkOrdersService.workOrdersHistory(lbp, fromDate, toDate, page, size), HttpStatus.OK);
    }

    @GetMapping("/find_work_orders")
    @PreAuthorize("hasAnyRole('ROLE_LAB_TEHNICAR', 'ROLE_VISI_LAB_TEHNICAR', 'ROLE_MED_BIOHEMICAR', 'ROLE_SPEC_BIOHEMICAR')")
    public ResponseEntity<Page<LabWorkOrder>> findWorkOrdersByLab(
            @RequestParam String lbp,
            @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size) {
        return new ResponseEntity<>(labWorkOrdersService.findWorkOrdersByLab(lbp, fromDate,  toDate, status, page, size), HttpStatus.OK);
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
