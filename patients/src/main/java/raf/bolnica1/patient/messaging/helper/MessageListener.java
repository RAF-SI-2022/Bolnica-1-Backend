package raf.bolnica1.patient.messaging.helper;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.services.PrescriptionService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@AllArgsConstructor
public class MessageListener {

    private MessageHelper messageHelper;
    private PrescriptionService prescriptionService;

    @JmsListener(destination = "${destination.send.completed}", concurrency = "5-10")
    public void addPrescription(Message message) throws JMSException {
        PrescriptionCreateDto prescriptionCreateDto = messageHelper.getMessage(message, PrescriptionCreateDto.class);
        prescriptionService.createPrescription(prescriptionCreateDto);
    }
}
