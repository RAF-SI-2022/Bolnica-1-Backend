package raf.bolnica1.laboratory.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionDeleteDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionUpdateDto;
import raf.bolnica1.laboratory.listener.helper.MessageHelper;
import raf.bolnica1.laboratory.services.lab.PrescriptionRecieveService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class PrescriptionListener {

    private MessageHelper messageHelper;
    private PrescriptionRecieveService prescriptionRecieveService;

    public PrescriptionListener(MessageHelper messageHelper, PrescriptionRecieveService prescriptionRecieveService) {
        this.messageHelper = messageHelper;
        this.prescriptionRecieveService = prescriptionRecieveService;
    }

    @JmsListener(destination = "${destination.send.lab}", concurrency = "5-10")
    public void addPrescription(Message message) throws JMSException {
        PrescriptionCreateDto prescriptionCreateDto = messageHelper.getMessage(message, PrescriptionCreateDto.class);
        prescriptionRecieveService.createPrescription(prescriptionCreateDto);
    }

    @JmsListener(destination = "${destination.update.lab}", concurrency = "5-10")
    public void updatePrescription(Message message) throws JMSException {
        PrescriptionUpdateDto prescription = messageHelper.getMessage(message, PrescriptionUpdateDto.class);
        prescriptionRecieveService.updatePrescription(prescription);
    }

    @JmsListener(destination = "${destination.delete.lab}", concurrency = "5-10")
    public void deletePrescription(Message message) throws JMSException {
        PrescriptionDeleteDto prescription = messageHelper.getMessage(message, PrescriptionDeleteDto.class);
        prescriptionRecieveService.deletePrescription(prescription.getPrescriptionId(), prescription.getLbz());
    }

}


