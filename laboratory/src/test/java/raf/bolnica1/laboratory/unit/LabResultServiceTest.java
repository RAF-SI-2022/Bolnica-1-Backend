package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.listener.helper.MessageHelper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.services.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.impl.LabResultServiceImpl;
import raf.bolnica1.laboratory.util.dataGenerators.classes.domain.prescription.PrescriptionGenerator;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.result.ResultUpdateDtoGenerator;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabResultServiceTest {

    private ResultUpdateDtoGenerator resultUpdateDtoGenerator=ResultUpdateDtoGenerator.getInstance();
    private PrescriptionGenerator prescriptionGenerator=PrescriptionGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();


    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    private LabWorkOrderRepository labWorkOrderRepository;
    private LabWorkOrdersService labWorkOrdersService;
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;
    private LabResultServiceImpl labResultService;


    @BeforeEach
    public void prepare(){
        parameterAnalysisResultRepository=mock(ParameterAnalysisResultRepository.class);
        labWorkOrderRepository=mock(LabWorkOrderRepository.class);
        labWorkOrdersService=mock(LabWorkOrdersService.class);
        jmsTemplate=mock(JmsTemplate.class);
        destination="mojaDestinacija";
        messageHelper=mock(MessageHelper.class);
        labResultService=new LabResultServiceImpl(parameterAnalysisResultRepository,labWorkOrderRepository,labWorkOrdersService,
                jmsTemplate,destination,messageHelper);
    }


    @Test
    public void updateResultsTest() {

        Long lwoId=3L;
        Long apId=4L;

        ResultUpdateDto resultUpdateDto=resultUpdateDtoGenerator.getResultUpdateDto(lwoId,apId);

        ParameterAnalysisResult par=new ParameterAnalysisResult();
        LabWorkOrder lwo=new LabWorkOrder();
        lwo.setId(lwoId);
        par.setLabWorkOrder(lwo);

        ///given(parameterAnalysisResultRepository.findParameterAnalysisResultByLabWorkOrderIdAndAnalysisParameterId(lwoId,apId))
           ///     .willReturn(par);
        given(parameterAnalysisResultRepository.findById(resultUpdateDto.getAnalysisParameterId())).willReturn(Optional.of(par));

        given(parameterAnalysisResultRepository.countParameterAnalysisResultWithNullResultByLabWorkOrderId(resultUpdateDto.getLabWorkOrderId()))
                .willReturn(1);

        given(parameterAnalysisResultRepository.save(any())).willReturn(par);

        labResultService.updateResults(resultUpdateDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(par,resultUpdateDto));

    }


    @Test
    public void commitResultTest(){

        Long lwoId=3L;

        Prescription p=prescriptionGenerator.getPrescription();
        LabWorkOrder lwo=new LabWorkOrder();
        lwo.setId(lwoId);
        lwo.setPrescription(p);
        lwo.setStatus(OrderStatus.OBRADJEN);

        given(labWorkOrderRepository.findLabWorkOrderById(lwoId)).willReturn(lwo);

        List<ParameterAnalysisResult> parList=new ArrayList<>();
        given(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(lwoId))
                .willReturn(parList);

        labResultService.commitResults(lwoId);

    }

}
