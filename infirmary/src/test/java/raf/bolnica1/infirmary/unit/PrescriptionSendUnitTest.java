package raf.bolnica1.infirmary.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.PrescriptionCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.PrescriptionReceiveDtoGenerator;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.dto.externalLabService.PrescriptionCreateDto;
import raf.bolnica1.infirmary.dto.prescription.PrescriptionReceiveDto;
import raf.bolnica1.infirmary.listener.helper.MessageHelper;
import raf.bolnica1.infirmary.mapper.PrescriptionMapper;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.services.PrescriptionSendService;
import raf.bolnica1.infirmary.services.impl.PrescriptionSendServiceImpl;
import raf.bolnica1.infirmary.validation.ClassJsonComparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrescriptionSendUnitTest {

    private PrescriptionReceiveDtoGenerator prescriptionReceiveDtoGenerator=PrescriptionReceiveDtoGenerator.getInstance();
    private PrescriptionCreateDtoGenerator prescriptionCreateDtoGenerator=PrescriptionCreateDtoGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();



    /// MOCK
    private PrescriptionSendService prescriptionSendService;
    private PrescriptionMapper prescriptionMapper;
    private PrescriptionRepository prescriptionRepository;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;


    @BeforeEach
    public void prepare(){
        prescriptionMapper=new PrescriptionMapper();
        prescriptionRepository=mock(PrescriptionRepository.class);
        jmsTemplate=mock(JmsTemplate.class);
        destination="moja_destinacija";
        messageHelper=mock(MessageHelper.class);
        prescriptionSendService=new PrescriptionSendServiceImpl(prescriptionMapper,prescriptionRepository,
                jmsTemplate,destination,messageHelper);
    }


    @Test
    public void receivePrescriptionTest(){

        PrescriptionReceiveDto prescriptionReceiveDto=prescriptionReceiveDtoGenerator.getPrescriptionReceiveDto();

        prescriptionSendService.receivePrescription(prescriptionReceiveDto);

        ArgumentCaptor<Prescription>prescriptionArgumentCaptor=ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(prescriptionArgumentCaptor.capture());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(prescriptionReceiveDto,
                prescriptionArgumentCaptor.getValue()));

    }


    @Test
    public void sendPrescriptionTest(){

        PrescriptionCreateDto prescriptionCreateDto=prescriptionCreateDtoGenerator.getPrescriptionCreateDto();

        given(messageHelper.createTextMessage(any())).willReturn("mock_string");

        prescriptionSendService.sendPrescription(prescriptionCreateDto);

        verify(jmsTemplate).convertAndSend(destination,"mock_string");
        verify(messageHelper).createTextMessage(prescriptionCreateDto);

    }

}
