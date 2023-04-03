package raf.bolnica1.patient.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import raf.bolnica1.patient.domain.constants.PrescriptionType;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.*;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;
import raf.bolnica1.patient.mapper.PrescriptionMapper;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.PrescriptionRepository;
import raf.bolnica1.patient.services.PrescriptionService;

import java.net.URI;
import java.sql.Date;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private JmsTemplate jmsTemplate;
    private String destinationSend;
    private String destinationDelete;
    private String destinationUpdate;
    private MessageHelper messageHelper;
    private PrescriptionMapper prescriptionMapper;
    private RestTemplate labRestTemplate;
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(JmsTemplate jmsTemplate, MessageHelper messageHelper,
                                   @Value("${destination.send}") String destinationSend,
                                   @Value("${destination.delete}") String destinationDelete,
                                   @Value("${destination.update}") String destinationUpdate,
                                   PrescriptionMapper prescriptionMapper,
                                   @Qualifier("labRestTemplate") RestTemplate labRestTemplate,
                                   PrescriptionRepository prescriptionRepository){
        this.jmsTemplate = jmsTemplate;
        this.destinationSend = destinationSend;
        this.destinationDelete = destinationDelete;
        this.destinationUpdate = destinationUpdate;
        this.messageHelper = messageHelper;
        this.prescriptionMapper = prescriptionMapper;
        this.labRestTemplate = labRestTemplate;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public MessageDto sendPersctiption(PrescriptionSendDto prescriptionSendDto) {
        if(prescriptionSendDto.getType().equals(PrescriptionType.LABORATORIJA))
            jmsTemplate.convertAndSend(destinationSend , messageHelper.createTextMessage(prescriptionMapper.getPrescriptionSendDto((PrescriptionLabSendDto) prescriptionSendDto)));
        return new MessageDto("Uspesno poslat uput");
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsForPatient(Long doctorId, String lbp, String token, int page, int size) {

        ParameterizedTypeReference<Page<PrescriptionDto>> responseType = new ParameterizedTypeReference<Page<PrescriptionDto>>() {};

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);

        URI baseUri = URI.create("/prescription/"+doctorId+"/get/"+lbp);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<Page<PrescriptionDto>> prescription = labRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType);

        return prescription.getBody();
    }

    @Override
    public MessageDto updatePrescription(PrescriptionUpdateDto prescriptionUpdateDto) {
        jmsTemplate.convertAndSend(destinationUpdate, messageHelper.createTextMessage(prescriptionUpdateDto));
        return new MessageDto("Uspesno poslata poruka za azuriranje uputa.");
    }

    @Override
    public MessageDto deletePresscription(Long prescriptionId) {
        jmsTemplate.convertAndSend(destinationDelete , messageHelper.createTextMessage(new PrescriptionDeleteDto(prescriptionId, getLbzFromAuthentication())));
        return new MessageDto("Uspesno poslata poruka za brisanje uputa.");
    }

    @Override
    public Page<PrescriptionDoneDto> getAllDonePrescriptionsForPatient(String lbp, Date dateFrom, Date dateTo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Prescription> prescriptions = prescriptionRepository.findPrescriptionByPatientAndDate(pageable, lbp, dateFrom, dateTo);

        return prescriptions.map(prescriptionMapper::toDto);
    }

    private String getLbzFromAuthentication(){
        String lbz = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            lbz = (String) authentication.getPrincipal();
        }
        // temp linija, treba malo refaktorisati
        if(lbz == null) throw new RuntimeException("Something went wrong.");
        return lbz;
    }

}
