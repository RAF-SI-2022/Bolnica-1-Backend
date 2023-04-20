package raf.bolnica1.patient.messaging.helper;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.dto.externalInfirmary.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.services.MedicalExaminationService;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@AllArgsConstructor
public class ExaminationHistoryListener {

    private MessageHelper messageHelper;
    private MedicalExaminationService medicalExaminationService;


    @JmsListener(destination = "${destination.send.examination}", concurrency = "5-10")
    public void addPrescription(Message message) throws JMSException {
        ExaminationHistoryCreateDto examinationHistoryCreateDto = messageHelper.getMessage(message, ExaminationHistoryCreateDto.class);
        medicalExaminationService.addExamination(examinationHistoryCreateDto.getLbp(), examinationHistoryCreateDto);
    }
}
