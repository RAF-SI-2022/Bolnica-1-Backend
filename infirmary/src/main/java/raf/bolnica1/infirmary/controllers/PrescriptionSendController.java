package raf.bolnica1.infirmary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.services.PrescriptionSendService;

@RestController
@AllArgsConstructor
@RequestMapping("/prescriptionSend")
public class PrescriptionSendController {

    private final PrescriptionSendService prescriptionSendService;


    @PutMapping("/sendPrescriptionToLab")
    public ResponseEntity<MessageDto> sendPrescriptionToLab(@RequestBody PrescriptionCreateDto prescriptionCreateDto){
        prescriptionSendService.sendPrescription(prescriptionCreateDto);
        return new ResponseEntity<>(new MessageDto("Uspesno poslat uput za Lab. "), HttpStatus.OK);
    }

}
