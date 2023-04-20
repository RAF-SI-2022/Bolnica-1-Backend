package raf.bolnica1.infirmary.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.services.MedicalRecordService;


@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {


    /// REST TEMPLATES
    private RestTemplate infoRestTemplate;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;

    public MedicalRecordServiceImpl(@Qualifier("infoRestTemplate") RestTemplate infoRestTemplate,
                                    JmsTemplate jmsTemplate,
                                    @Value("${destination.send.examination}") String destination,
                                    MessageHelper messageHelper) {
        this.infoRestTemplate = infoRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageHelper = messageHelper;
    }

    @Override
    public MedicalRecordDto getMedicalRecordByLbp(String lbp,String authorization) {

        String token = authorization.split(" ")[1];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        String uri=new String("/myFindMedicalRecord/"+lbp);
        ResponseEntity<MedicalRecordDto> medicalRecord = infoRestTemplate.exchange(uri, HttpMethod.GET, httpEntity, MedicalRecordDto.class);
        if(medicalRecord.getBody()==null)
            return null;

        return medicalRecord.getBody();
    }

    @Override
    public MessageDto createExaminationHistory(ExaminationHistoryCreateDto examinationHistoryCreateDto) {
        jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(examinationHistoryCreateDto));
        return new MessageDto("Uspesno poslat izvestaj.");
    }
}
