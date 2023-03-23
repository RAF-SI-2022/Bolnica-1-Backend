package raf.bolnica1.patient.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
import raf.bolnica1.patient.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.PrescriptionDeleteDto;
import raf.bolnica1.patient.dto.prescription.PrescriptionDto;
import raf.bolnica1.patient.mapper.PrescriptionMapper;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.services.PrescriptionService;

import java.net.URI;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private JmsTemplate jmsTemplate;
    private String destinationSend;
    private String destinationDelete;
    private MessageHelper messageHelper;
    private PrescriptionMapper prescriptionMapper;
    private RestTemplate labRestTemplate;

    public PrescriptionServiceImpl(JmsTemplate jmsTemplate, MessageHelper messageHelper,
                                   @Value("${destination.laboratory.send}") String destinationSend,
                                   @Value("${destination.laboratory.delete}") String destinationDelete,
                                   PrescriptionMapper prescriptionMapper,
                                   @Qualifier("labRestTemplate") RestTemplate labRestTemplate){
        this.jmsTemplate = jmsTemplate;
        this.destinationSend = destinationSend;
        this.destinationDelete = destinationDelete;
        this.messageHelper = messageHelper;
        this.prescriptionMapper = prescriptionMapper;
        this.labRestTemplate = labRestTemplate;
    }

    @Override
    public MessageDto sendPersctiption(PrescriptionCreateDto prescriptionCreateDto) {
        jmsTemplate.convertAndSend(destinationSend , messageHelper.createTextMessage(prescriptionMapper.getPrescriptionSendDto(prescriptionCreateDto)));
        return new MessageDto("Uspesno poslat uput");
    }

    @Override
    public Page<PrescriptionDto> getPrescriptionsForPatient(Long doctorId, String lbp, String token, int page, int size) {

        ParameterizedTypeReference<Page<PrescriptionDto>> responseType = new ParameterizedTypeReference<Page<PrescriptionDto>>() {};

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(null, httpHeaders);

        URI baseUri = URI.create("/prescription/"+doctorId+"/get/"+lbp);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<Page<PrescriptionDto>> prescription = labRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType);

        return prescription.getBody();
    }

    @Override
    public PrescriptionCreateDto updatePrescription() {
        return null;
    }

    @Override
    public MessageDto deletePresscription(Long prescriptionId, String token) {
        jmsTemplate.convertAndSend(destinationDelete , messageHelper.createTextMessage(new PrescriptionDeleteDto(prescriptionId, token)));
        return new MessageDto("Uspesno poslata poruka za brisanje uputa");
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
