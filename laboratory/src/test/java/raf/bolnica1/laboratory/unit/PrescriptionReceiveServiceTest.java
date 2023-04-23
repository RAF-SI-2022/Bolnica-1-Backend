package raf.bolnica1.laboratory.unit;

import io.cucumber.java.sl.In;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import raf.bolnica1.laboratory.mappers.PrescriptionMapper;
import raf.bolnica1.laboratory.mappers.PrescriptionRecieveMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;

@SpringBootTest
public class PrescriptionReceiveServiceTest {

    @Mock
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private AnalysisParameterRepository analysisParameterRepository;
    @Mock
    private PrescriptionMapper prescriptionMapper;
    @Mock
    private PrescriptionRecieveMapper prescriptionrecieveMapper;
    @InjectMocks
    private LabWorkOrdersService labWorkOrdersService;
    @Mock
    private LabWorkOrderRepository labWorkOrderRepository;


}
