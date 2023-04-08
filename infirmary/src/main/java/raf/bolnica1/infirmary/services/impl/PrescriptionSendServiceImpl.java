package raf.bolnica1.infirmary.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.PrescriptionSendService;

@Service
public class PrescriptionSendServiceImpl implements PrescriptionSendService {

    /// MAPPERS
    private PrescriptionMapper prescriptionMapper;

    /// REPOSITORIES
    private PrescriptionRepository prescriptionRepository;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;

    public PrescriptionSendServiceImpl(PrescriptionMapper prescriptionMapper,
                                       PrescriptionRepository prescriptionRepository,
                                       JmsTemplate jmsTemplate,
                                       @Value("${destination.send.lab}") String destination, MessageHelper messageHelper) {
        this.prescriptionMapper = prescriptionMapper;
        this.prescriptionRepository = prescriptionRepository;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageHelper = messageHelper;
    }

    @Override
    public void createPrescription(PrescriptionReceiveDto prescriptionReceiveDto) {
        Prescription prescription= prescriptionMapper.toEntity(prescriptionReceiveDto);
        prescriptionRepository.save(prescription);
    }

    @Override
    public void sendPrescription(PrescriptionCreateDto prescriptionCreateDto) {
        jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(prescriptionCreateDto));
    }
}
