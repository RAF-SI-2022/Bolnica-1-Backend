package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.services.MedicalRecordService;
import raf.bolnica1.infirmary.services.impl.MedicalRecordServiceImpl;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordUnitTest {


    private RestTemplate infoRestTemplate;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void prepare(){
        infoRestTemplate=mock(RestTemplate.class);
        jmsTemplate=mock(JmsTemplate.class);
        destination="moja_destinacija";
        messageHelper=mock(MessageHelper.class);
        medicalRecordService=new MedicalRecordServiceImpl(infoRestTemplate,jmsTemplate,destination,messageHelper);
    }


    @Test
    public void getMedicalRecordByLbpTest(){

        String authorization="Bearer mojToken";
        String lbp="mojLbp";

        MedicalRecordDto medicalRecordDto=new MedicalRecordDto();

        given(infoRestTemplate.exchange(eq("/myFindMedicalRecord/mojLbp"), any(), any(), eq(MedicalRecordDto.class) ))
                .willReturn(new ResponseEntity<>(medicalRecordDto, HttpStatus.OK) );

        MedicalRecordDto result=medicalRecordService.getMedicalRecordByLbp(lbp,authorization);

        Assertions.assertTrue(result.equals(medicalRecordDto));
    }


    @Test
    public void createExaminationHistoryTest(){

        ExaminationHistoryCreateDto examinationHistoryCreateDto=new ExaminationHistoryCreateDto();

        given(messageHelper.createTextMessage(examinationHistoryCreateDto)).willReturn("mojMessage");

        MessageDto result=medicalRecordService.createExaminationHistory(examinationHistoryCreateDto);

        verify(messageHelper).createTextMessage(examinationHistoryCreateDto);
        verify(jmsTemplate).convertAndSend(destination,"mojMessage");

    }

}
