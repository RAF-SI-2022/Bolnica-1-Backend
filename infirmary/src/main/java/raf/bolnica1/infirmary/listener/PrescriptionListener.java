package raf.bolnica1.infirmary.listener;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.services.PrescriptionSendService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@AllArgsConstructor
public class PrescriptionListener {
    private MessageHelper messageHelper;
    private PrescriptionSendService prescriptionSendService;

    @JmsListener(destination = "${destination.send.infirmary}", concurrency = "5-10")
    public void addPrescription(Message message) throws JMSException {
        PrescriptionReceiveDto prescriptionCreateDto = messageHelper.getMessage(message, PrescriptionReceiveDto.class);
        prescriptionSendService.createPrescription(prescriptionCreateDto);
    }

}
