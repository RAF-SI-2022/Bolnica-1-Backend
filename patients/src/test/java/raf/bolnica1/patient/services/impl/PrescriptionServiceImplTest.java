package raf.bolnica1.patient.services.impl;


import io.cucumber.java.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.patient.domain.constants.PrescriptionType;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.prescription.Prescription;
import raf.bolnica1.patient.dto.create.LabResultDto;
import raf.bolnica1.patient.dto.create.PrescriptionCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.prescription.general.*;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionAnalysisDataDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionDoneLabDto;
import raf.bolnica1.patient.dto.prescription.lab.PrescriptionNewDto;
import raf.bolnica1.patient.mapper.PrescriptionMapper;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.PrescriptionRepository;
import raf.bolnica1.patient.domain.constants.PrescriptionStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PrescriptionServiceImplTest {

    @InjectMocks
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
     //   MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
    }

    private PrescriptionSendDto prescriptionSendDto;

    @BeforeEach
    public void setup() {
        prescriptionSendDto = new PrescriptionSendDto();
    }


    @Nested
    class sendPrescription {

        private final String destinationSendLab = "string";

        @Test
        public void testSendPresctiption_LaboratorijaType_Success() {
            // Arrange
            prescriptionSendDto.setType(PrescriptionType.LABORATORIJA);
            prescriptionService.setDestinationSendLab("des");
            prescriptionService.setDestinationSendInfirmary("desInfirmary");

            when(messageHelper.createTextMessage(prescriptionSendDto)).thenReturn("true");


            MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);


            assertEquals("Uspesno poslat uput", result.getMessage());

            verify(jmsTemplate).convertAndSend("des", (Object) "true");
            verify(messageHelper).createTextMessage(prescriptionSendDto);
            verify(jmsTemplate, never()).convertAndSend("desInfirmary", (Object) "true");


        }


        @Test
        public void testSendPresctiption_STACIONARType_Success() {
            // Arrange
            prescriptionSendDto.setType(PrescriptionType.STACIONAR);
            prescriptionService.setDestinationSendLab("des");
            prescriptionService.setDestinationSendInfirmary("desInfirmary");

            when(messageHelper.createTextMessage(prescriptionSendDto)).thenReturn("true");


            MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);


            assertEquals("Uspesno poslat uput", result.getMessage());

            verify(jmsTemplate).convertAndSend("desInfirmary", (Object) "true");
            verify(messageHelper).createTextMessage(prescriptionSendDto);
            verify(jmsTemplate, never()).convertAndSend("des", (Object) "true");

        }



        @Test
        public void testSendPresctiption_DIJAGNOSTIKAType_Success() {
            // Arrange
            prescriptionSendDto.setType(PrescriptionType.DIJAGNOSTIKA);
            prescriptionService.setDestinationSendLab("des");
            prescriptionService.setDestinationSendInfirmary("desInfirmary");

            MessageDto result = prescriptionService.sendPersctiption(prescriptionSendDto);


            assertEquals("Uspesno poslat uput", result.getMessage());

            verify(jmsTemplate, never()).convertAndSend("desInfirmary", (Object) "true");
            verify(messageHelper, never()).createTextMessage(prescriptionSendDto);
            verify(jmsTemplate, never()).convertAndSend("des", (Object) "true");

        }

    }


    @Test
    public void getPrescriptionsForPatientTest() {

        String lbz = "some_lbz";
        String lbp = "some_lbp";
        String token = "Bearer some_token";
        int page = 0;
        int size = 10;
        List<PrescriptionNewDto> prescriptionDtos = new ArrayList<>();


        PrescriptionNewDto prescription1 = new PrescriptionNewDto();
        prescription1.setId(1L);
        prescription1.setType("Type 1");
        prescription1.setDepartmentFromId(100L);
        prescription1.setDepartmentToId(200L);
        prescription1.setLbp("LBP 1");
        prescription1.setDoctorLbz("Doctor LBZ 1");
        prescription1.setComment("Comment 1");
        prescription1.setCreationDate(new Date(System.currentTimeMillis()));
        prescription1.setStatus(PrescriptionStatus.REALIZOVAN);
        prescription1.setPrescriptionAnalysisDataDtoList(
                null
        );

        PrescriptionNewDto prescription2 = new PrescriptionNewDto();
        prescription2.setId(2L);
        prescription2.setType("Type 2");
        prescription2.setDepartmentFromId(300L);
        prescription2.setDepartmentToId(400L);
        prescription2.setLbp("LBP 2");
        prescription2.setDoctorLbz("Doctor LBZ 2");
        prescription2.setComment("Comment 2");
        prescription2.setCreationDate(new Date(System.currentTimeMillis()));
        prescription2.setStatus(PrescriptionStatus.NEREALIZOVAN);
        prescription2.setPrescriptionAnalysisDataDtoList(
                null
        );

        prescriptionDtos.add(prescription1);
        prescriptionDtos.add(prescription2);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("some_token");
        when(labRestTemplate.exchange(eq("/prescription/" + lbz + "/get_rest/" + lbp), eq(HttpMethod.GET), eq(new HttpEntity<>(null, headers)), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(prescriptionDtos));
        // act
        Page<PrescriptionNewDto> result = prescriptionService.getPrescriptionsForPatient(lbz, lbp, token, page, size);
        // assert
        assertEquals(2, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());


    }


    @Test
    public void testUpdatePrescription() {
        // Prepare test data
        PrescriptionUpdateDto prescriptionUpdateDto = new PrescriptionUpdateDto();
        prescriptionService.setDestinationUpdateLab("dest");
        // Mock the JmsTemplate and MessageHelper
        when(messageHelper.createTextMessage(prescriptionUpdateDto)).thenReturn("true");

        MessageDto result = prescriptionService.updatePrescription(prescriptionUpdateDto);

        assertEquals("Uspesno poslata poruka za azuriranje uputa.", result.getMessage());


        verify(jmsTemplate, times(1)).convertAndSend("dest", "true");
        verify(messageHelper, times(1)).createTextMessage(eq(prescriptionUpdateDto));

    }



        @Test
        public void deletePresscriptionSuccessfulTest() {

            String lbz = "testUser";
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(authority);
            Authentication authentication = new UsernamePasswordAuthenticationToken(lbz, null, authorities);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            long prescriptionId = 1l;
            PrescriptionDeleteDto prescriptionDeleteDto = new PrescriptionDeleteDto(prescriptionId, lbz);
       //     String expectedMessage = messageHelper.createTextMessage(prescriptionDeleteDto);

           when(messageHelper.createTextMessage(prescriptionDeleteDto)).thenReturn("true");
    //        Mockito.doNothing().when(jmsTemplate).convertAndSend(Mockito.anyString(), Mockito.eq(expectedMessage));

            prescriptionService.setDestinationDeleteLab("dest");
            MessageDto res = prescriptionService.deletePresscription(prescriptionId);

            // Assert
            assertEquals("Uspesno poslata poruka za brisanje uputa.", res.getMessage());
        //    verify(jmsTemplate).convertAndSend(Mockito.anyString(), Mockito.eq("true"));

        }





    @Test
    public void getAllDonePrescriptionsForPatientTest(){



        String lbp = "123456789";
        Date dateFrom = new Date(2020, 01, 15);
        Date dateTo = new Date(2023, 01, 15);
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Prescription prescription1 = new Prescription();
        Prescription prescription2 = new Prescription();
        List<Prescription> prescriptions = Arrays.asList(prescription1, prescription2);
        when(prescriptionRepository.findPrescriptionByPatientAndDate(pageable, lbp, dateFrom, dateTo))
                .thenReturn(new PageImpl<>(prescriptions));
        PrescriptionDoneDto prescriptionDoneDto1 = new PrescriptionDoneDto();
        PrescriptionDoneDto prescriptionDoneDto2 = new PrescriptionDoneDto();
        List<PrescriptionDoneDto> prescriptionDoneDtos = Arrays.asList(prescriptionDoneDto1, prescriptionDoneDto2);
        when(prescriptionMapper.toDto(prescription1)).thenReturn(prescriptionDoneDto1);
        when(prescriptionMapper.toDto(prescription2)).thenReturn(prescriptionDoneDto2);

        // Act
        Page<PrescriptionDoneDto> result = prescriptionService.getAllDonePrescriptionsForPatient(lbp, (java.sql.Date) dateFrom, dateTo, page, size);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(prescriptionDoneDtos, result.getContent());
        verify(prescriptionRepository).findPrescriptionByPatientAndDate(pageable, lbp, dateFrom, dateTo);
        verify(prescriptionMapper).toDto(prescription1);
        verify(prescriptionMapper).toDto(prescription2);
    }



    @Test
    public void getPrescriptionTest(){

        Long prescriptionId = 123L;
        String token = "Bearer abc123";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        PrescriptionDoneLabDto prescriptionDoneLabDto = new PrescriptionDoneLabDto();
        prescriptionDoneLabDto.setId(prescriptionId);
        prescriptionDoneLabDto.setComment("comment");
        ResponseEntity<PrescriptionDoneLabDto> responseEntity = new ResponseEntity<>(prescriptionDoneLabDto, HttpStatus.OK);
        when(labRestTemplate.exchange(eq("/prescription/" + prescriptionId), eq(HttpMethod.GET), eq(entity), eq(PrescriptionDoneLabDto.class)))
                .thenReturn(responseEntity);

        // Act
        PrescriptionDoneDto result = prescriptionService.getPrescription(prescriptionId, token);

        // Assert
        assertEquals(prescriptionId, result.getId());

        verify(labRestTemplate).exchange(eq("/prescription/" + prescriptionId), eq(HttpMethod.GET), eq(entity), eq(PrescriptionDoneLabDto.class));


    }



    @Test
    public void createPrescriptionTest() {


        Prescription prescription = new Prescription();
        PrescriptionCreateDto prescriptionCreateDto = new PrescriptionCreateDto();
        prescriptionCreateDto.setType("LABORATORIJA");


        LabResultDto labResultDto = new LabResultDto();
        LabResults labResults1 = new LabResults();
        List<LabResultDto> list = new ArrayList<>();
        list.add(labResultDto);
        prescriptionCreateDto.setLabResultDtoList(list);


        when(prescriptionMapper.toEntity(prescriptionCreateDto)).thenReturn(prescription);
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);
        when(prescriptionMapper.getLabResult(prescription, labResultDto)).thenReturn(labResults1);


        prescriptionService.createPrescription(prescriptionCreateDto);



        verify(prescriptionMapper, times(1)).toEntity(prescriptionCreateDto);
        verify(prescriptionRepository, times(1)).save(prescription);
        verify(prescriptionMapper, times(1)).getLabResult(prescription, labResultDto);
        verify(labResultsRepository, times(1)).save(labResults1);




    }
}
