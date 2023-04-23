package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;
import raf.bolnica1.laboratory.services.lab.impl.PrescriptionServiceImpl;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PrescriptionUnitTest {

    private PrescriptionRepository prescriptionRepository;

    private PrescriptionService prescriptionService;

    @BeforeEach
    public void prepare(){
        prescriptionRepository=mock(PrescriptionRepository.class);
        prescriptionService=new PrescriptionServiceImpl(prescriptionRepository);
    }

    

}
