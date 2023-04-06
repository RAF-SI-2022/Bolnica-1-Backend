package raf.bolnica1.patient.services.impl;

import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.patient.domain.constants.PrescriptionType;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.create.LabResultDto;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDeleteDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionSendDto;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionUpdateDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionLabSendDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionNewDto;
import raf.bolnica1.patient.mapper.PrescriptionMapper;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.PrescriptionRepository;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceImplTest {

    /*@InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private MessageHelper messageHelper;
    @Mock
    private PrescriptionMapper prescriptionMapper;
    @Mock
    @Qualifier("labRestTemplate")
    private RestTemplate labRestTemplate;
    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private LabResultsRepository labResultsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendPersctiption_LaboratorijaType_Success() {
        // Arrange
        PrescriptionSendDto prescriptionSendDto = new PrescriptionLabSendDto();
        when(prescriptionSendDto.getType()).thenReturn(PrescriptionType.LABORATORIJA);
        //DORADITI!!!
        //when(prescriptionMapper.getPrescriptionSendDto(any(PrescriptionLabSendDto.class))).thenReturn("prescriptionBody");

        // Act
        MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);

        // Assert
        verify(jmsTemplate).convertAndSend(eq("destinationSend"), eq("prescriptionBody"));
        assertEquals("Uspesno poslat uput", result.getMessage());
    }

    @Test
    public void testSendPersctiption_OtherType_Success() {
        // Arrange
        PrescriptionSendDto prescriptionSendDto = new PrescriptionSendDto();
        when(prescriptionSendDto.getType()).thenReturn(PrescriptionType.OTHER);

        // Act
        MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);

        // Assert
        verify(jmsTemplate, never()).convertAndSend(anyString(), any());
        assertEquals("Uspesno poslat uput", result.getMessage());
    }

    @Test
    public void testSendPersctiption_Success() {
        // Arrange
        PrescriptionSendDto prescriptionSendDto = new PrescriptionLabSendDto();
        prescriptionSendDto.setType(PrescriptionType.LABORATORIJA);
        String destinationSend = "destinationSend";
        when(prescriptionMapper.getPrescriptionSendDto(any(PrescriptionLabSendDto.class))).thenReturn(new PrescriptionSendDto());
        when(messageHelper.createTextMessage(any(PrescriptionSendDto.class))).thenReturn("textMessage");

        // Act
        MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);

        // Assert
        assertNotNull(result);
        assertEquals("Uspesno poslat uput", result.getMessage());
        verify(jmsTemplate, times(1)).convertAndSend(eq(destinationSend), eq("textMessage"));
    }

    @Test
    public void testSendPersctiption_TypeNotLab_ReturnsNull() {
        // Arrange
        PrescriptionSendDto prescriptionSendDto = new PrescriptionSendDto();
        prescriptionSendDto.setType(PrescriptionType.NEKA_VRSTA);
        String destinationSend = "destinationSend";

        // Act
        MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getMessage());
        verify(jmsTemplate, times(0)).convertAndSend(eq(destinationSend), any(String.class));
    }

    @Test
    public void testGetPrescriptionsForPatient_Success() {
        // Arrange
        String lbz = "lbz";
        String lbp = "lbp";
        String token = "token";
        int page = 0;
        int size = 10;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ArrayList<PrescriptionNewDto> prescriptionList = new ArrayList<>();
        prescriptionList.add(new PrescriptionNewDto());
        ParameterizedTypeReference<ArrayList<PrescriptionNewDto>> type = new ParameterizedTypeReference<ArrayList<PrescriptionNewDto>>() {};
        ResponseEntity<ArrayList<PrescriptionNewDto>> responseEntity = new ResponseEntity<>(prescriptionList, HttpStatus.OK);
        when(labRestTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(entity), eq(type))).thenReturn(responseEntity);

        // Act
        Page<PrescriptionNewDto> result = prescriptionService.getPrescriptionsForPatient(lbz, lbp, token, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(prescriptionList.size(), result.getContent().size());
        verify(labRestTemplate, times(1)).exchange(eq("/prescription/"+lbz+"/get_rest/"+lbp), eq(HttpMethod.GET), eq(entity), eq(type));
    }

    private String destinationSend = "destinationSend";
    private String destinationDelete = "destinationDelete";
    private String destinationUpdate = "destinationUpdate";

    @Test
    public void testUpdatePrescription() {
        // Prepare test data
        PrescriptionUpdateDto prescriptionUpdateDto = new PrescriptionUpdateDto();
        // Mock the JmsTemplate and MessageHelper
        when(jmsTemplate.convertAndSend(eq(destinationUpdate), any(Message.class))).thenReturn(null);
        when(messageHelper.createTextMessage(any(PrescriptionUpdateDto.class))).thenReturn(new TextMessageStub());
        // Call the method to be tested
        MessageDto result = prescriptionService.updatePrescription(prescriptionUpdateDto);
        // Verify the JmsTemplate and MessageHelper were called with correct arguments
        verify(jmsTemplate).convertAndSend(eq(destinationUpdate), any(Message.class));
        verify(messageHelper).createTextMessage(eq(prescriptionUpdateDto));
        // Assert the result
        assertNotNull(result);
        assertEquals("Uspesno poslata poruka za azuriranje uputa.", result.getMessage());
    }

    @Test
    public void testDeletePrescription() {
        // Prepare test data
        Long prescriptionId = 1L;
        // Mock the JmsTemplate and MessageHelper
        when(jmsTemplate.convertAndSend(eq(destinationDelete), any(Message.class))).thenReturn(null);
        when(messageHelper.createTextMessage(any(PrescriptionDeleteDto.class))).thenReturn(new TextMessageStub());
        when(patientRepository.getLbzFromAuthentication()).thenReturn("lbz");
        // Call the method to be tested
        MessageDto result = prescriptionService.deletePresscription(prescriptionId);
        // Verify the JmsTemplate, MessageHelper, and PatientRepository were called with correct arguments
        verify(jmsTemplate).convertAndSend(eq(destinationDelete), any(Message.class));
        verify(messageHelper).createTextMessage(eq(new PrescriptionDeleteDto(prescriptionId, "lbz")));
        verify(patientRepository).getLbzFromAuthentication();
        // Assert the result
        assertNotNull(result);
        assertEquals("Uspesno poslata poruka za brisanje uputa.", result.getMessage());
    }

    // Helper class for mocking the TextMessage
    private static class TextMessageStub implements TextMessage {
        @Override
        public String getText() throws JMSException {
            return null;
        }
        // Implement other methods as needed
    }

    @Test
    public void testGetAllDonePrescriptionsForPatient() {
        // Mocking data
        String lbp = "123456789";
        Date dateFrom = new Date();
        Date dateTo = new Date();
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        Page<Prescription> prescriptionPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(prescriptionRepository.findPrescriptionByPatientAndDate(pageable, lbp, dateFrom, dateTo)).thenReturn(prescriptionPage);

        // Test
        Page<PrescriptionDoneDto> result = prescriptionService.getAllDonePrescriptionsForPatient(lbp, dateFrom, dateTo, page, size);

        // Assertions
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(page, result.getPageable().getPageNumber());
        assertEquals(size, result.getPageable().getPageSize());
    }

    @Test
    public void testGetPrescription() {
        // Mocking data
        Long prescriptionId = 1L;
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<PrescriptionDoneLabDto> prescriptionResponse = new ResponseEntity<>(new PrescriptionDoneLabDto(), HttpStatus.OK);
        when(labRestTemplate.exchange(eq("/prescription/"+prescriptionId), eq(HttpMethod.GET), eq(entity), eq(PrescriptionDoneLabDto.class))).thenReturn(prescriptionResponse);

        // Test
        PrescriptionDoneDto result = prescriptionService.getPrescription(prescriptionId, token);

        // Assertions
        assertNotNull(result);
        // Add more assertions based on the expected behavior of the method
    }

    @Test
    public void testGetPrescription() {
        // Mocking
        Long prescriptionId = 1L;
        String token = "Bearer <token>";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        PrescriptionDoneLabDto prescriptionDoneLabDto = new PrescriptionDoneLabDto();
        ResponseEntity<PrescriptionDoneLabDto> responseEntity = new ResponseEntity<>(prescriptionDoneLabDto, HttpStatus.OK);
        Mockito.when(labRestTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.eq(entity), Mockito.eq(PrescriptionDoneLabDto.class))).thenReturn(responseEntity);

        // Test
        PrescriptionDoneDto result = prescriptionService.getPrescription(prescriptionId, token);

        // Assertions
        assertNotNull(result);
        // Add more assertions based on the expected behavior of the method
    }

    @Test
    public void testCreatePrescription() {
        // Mocking
        PrescriptionCreateDto prescriptionCreateDto = new PrescriptionCreateDto();
        Prescription prescription = new Prescription();
        LabResultDto labResultDto1 = new LabResultDto();
        LabResultDto labResultDto2 = new LabResultDto();
        List<LabResultDto> labResultDtoList = Arrays.asList(labResultDto1, labResultDto2);
        prescriptionCreateDto.setLabResultDtoList(labResultDtoList);
        LabResults labResults1 = new LabResults();
        LabResults labResults2 = new LabResults();
        Mockito.when(prescriptionMapper.toEntity(Mockito.any(PrescriptionCreateDto.class))).thenReturn(prescription);
        Mockito.when(prescriptionRepository.save(Mockito.any(Prescription.class))).thenReturn(prescription);
        Mockito.when(prescriptionMapper.getLabResult(Mockito.any(Prescription.class), Mockito.any(LabResultDto.class))).thenReturn(labResults1, labResults2);

        // Test
        prescriptionService.createPrescription(prescriptionCreateDto);

        // Assertions
        Mockito.verify(prescriptionMapper).toEntity(prescriptionCreateDto);
        Mockito.verify(prescriptionRepository).save(prescription);
        Mockito.verify(prescriptionMapper, Mockito.times(2)).getLabResult(prescription, labResultDto1); // verify that getLabResult() is called twice for each lab result
        Mockito.verify(prescriptionMapper, Mockito.times(2)).getLabResult(prescription, labResultDto2);
        Mockito.verify(labResultsRepository).save(labResults1);
        Mockito.verify(labResultsRepository).save(labResults2);
        // Add more assertions based on the expected behavior of the method
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
    }*/

}
