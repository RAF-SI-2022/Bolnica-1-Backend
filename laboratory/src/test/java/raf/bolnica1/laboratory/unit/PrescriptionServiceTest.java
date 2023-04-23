package raf.bolnica1.laboratory.unit;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;

@SpringBootTest
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private JmsTemplate jmsTemplate;

}
