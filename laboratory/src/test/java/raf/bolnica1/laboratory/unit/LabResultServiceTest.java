package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.listener.helper.MessageHelper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.impl.LabResultServiceImpl;

import javax.jms.Destination;
import javax.jms.Message;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.*;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.createLabWorkOrder;

@ExtendWith(MockitoExtension.class)
public class LabResultServiceTest {

    @Mock
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @Mock
    private LabWorkOrderRepository labWorkOrderRepository;
    @InjectMocks
    private LabWorkOrdersService labWorkOrdersService;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private String destination;
    @Mock
    private MessageHelper messageHelper;
    @InjectMocks
    private LabResultServiceImpl labResultService;

    @Test
    public void testUpdateResults() {

    }

    @Test
    public void testCommitResults() {

    }

}
