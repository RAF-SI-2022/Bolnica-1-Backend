package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.lab.*;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ParameterAnalysisResultDto;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderWithAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.exceptions.workOrder.NoParameterAnalysisResultFound;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;
import raf.bolnica1.laboratory.services.lab.impl.LabWorkOrdersServiceImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.*;

@ExtendWith(MockitoExtension.class)
public class LabWorkOrdersServiceTest {

    @Mock
    private AuthenticationUtils authenticationUtils;

    @Mock
    private LabWorkOrderRepository labWorkOrderRepository;

    @Mock
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private LabWorkOrderWithAnalysisMapper labWorkOrderWithAnalysisMapper;

    @Mock
    private ParameterAnalysisResultMapper parameterAnalysisResultMapper;

    @Mock
    private LabWorkOrderMapper labWorkOrderMapper;

    @Mock
    private PrescriptionService prescriptionService;

    @InjectMocks
    private LabWorkOrdersServiceImpl labWorkOrdersService;

    @BeforeEach
    public void setAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", "testpassword");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testCreateWorkOrder() {
        Prescription prescription = createPrescription();
        Timestamp timestamp = createDefaultTimestamp();

        LabWorkOrder labWorkOrder = createLabWorkOrder();
        labWorkOrder.setPrescription(prescription);
        labWorkOrder.setLbp(prescription.getLbp());
        labWorkOrder.setCreationDateTime(timestamp);

        when(labWorkOrderRepository.save(any(LabWorkOrder.class))).thenReturn(labWorkOrder);

        LabWorkOrder result = labWorkOrdersService.createWorkOrder(prescription);

        assertNotNull(result);
        assertEquals(prescription, result.getPrescription());
        assertEquals(prescription.getLbp(), result.getLbp());
        assertNotNull(result.getCreationDateTime());
    }

    @Test
    public void testRegisterPatient() {
        String lbp = "LBP001";
        List<Prescription> prescriptions = new ArrayList<>();
        Prescription prescription1 = createPrescription();
        prescription1.setId(1L);
        Prescription prescription2 = createPrescription();
        prescription2.setId(2L);
        prescriptions.add(prescription1);
        prescriptions.add(prescription2);

        LabWorkOrder labWorkOrder1 = createLabWorkOrder();
        labWorkOrder1.setId(1L);
        labWorkOrder1.setPrescription(prescription1);
        LabWorkOrder labWorkOrder2 = createLabWorkOrder();
        labWorkOrder2.setId(2L);
        labWorkOrder2.setPrescription(prescription2);

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn("LBZ001");

        when(prescriptionRepository.findPrescriptionsByLbp(lbp)).thenReturn(prescriptions);
        when(labWorkOrderRepository.findByPrescription(prescription1.getId())).thenReturn(Optional.of(labWorkOrder1));
        when(labWorkOrderRepository.findByPrescription(prescription2.getId())).thenReturn(Optional.of(labWorkOrder2));

        MessageDto result = labWorkOrdersService.registerPatient(lbp);

        verify(labWorkOrderRepository, times(2)).save(any(LabWorkOrder.class));
        assertEquals("Uspesno izvrseno", result.getMessage());
        assertEquals(OrderStatus.U_OBRADI, labWorkOrder1.getStatus());
        assertEquals("LBZ001", labWorkOrder1.getTechnicianLbz());
        assertEquals(OrderStatus.U_OBRADI, labWorkOrder2.getStatus());
        assertEquals("LBZ001", labWorkOrder2.getTechnicianLbz());
    }

    @Test
    public void testVerifyWorkOrder() {
        Long id = 1L;
        LabWorkOrder labWorkOrder = createLabWorkOrder();
        labWorkOrder.setId(id);
        Prescription prescription = createPrescription();
        prescription.setId(1L);
        labWorkOrder.setPrescription(prescription);
        ParameterAnalysisResult parameterAnalysisResult = createParameterAnalysisResult();
        parameterAnalysisResult.setResult("REZULTAT");
        parameterAnalysisResult.setLabWorkOrder(labWorkOrder);

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn("LBZ001");
        when(labWorkOrderRepository.findById(id)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(id)).thenReturn(Collections.singletonList(parameterAnalysisResult));

        LabWorkOrderMessageDto result = labWorkOrdersService.verifyWorkOrder(id);

        verify(labWorkOrderRepository, times(1)).save(any(LabWorkOrder.class));
        verify(prescriptionService, times(1)).updatePrescriptionStatus(labWorkOrder.getPrescription().getId(), PrescriptionStatus.REALIZOVAN);
        assertEquals("Verifikovan radni nalog", result.getMessage());
        assertEquals(labWorkOrderMapper.toDto(labWorkOrder), result.getLabWorkOrderDto());
        assertEquals("LBZ001", labWorkOrder.getBiochemistLbz());
        assertEquals(OrderStatus.OBRADJEN, labWorkOrder.getStatus());
    }

    @Test
    void updateAnalysisParameters_shouldUpdateParameterAnalysisResult() {
        Long workOrderId = 1L;
        AnalysisParameter analysisParameter = createAnalysisParameter();
        String result = "result";

        LabWorkOrder labWorkOrder = createLabWorkOrder();
        labWorkOrder.setStatus(OrderStatus.NEOBRADJEN);

        ParameterAnalysisResult par = createParameterAnalysisResult();
        par.setLabWorkOrder(labWorkOrder);
        par.setAnalysisParameter(analysisParameter);

        UpdateParameterAnalysisResultMessageDto expectedDto = new UpdateParameterAnalysisResultMessageDto();
        expectedDto.setResult(result);

        when(labWorkOrderRepository.findById(workOrderId)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameter.getId())).thenReturn(Optional.of(par));
        when(authenticationUtils.getLbzFromAuthentication()).thenReturn("lbz");
        when(parameterAnalysisResultMapper.toDto(par)).thenReturn(expectedDto);

        UpdateParameterAnalysisResultMessageDto actualDto = labWorkOrdersService.updateAnalysisParameters(workOrderId, analysisParameter.getId(), result);

        assertEquals(result, par.getResult());
        assertEquals(OrderStatus.U_OBRADI, labWorkOrder.getStatus());
        assertEquals("lbz", par.getBiochemistLbz());
        assertNotNull(par.getDateTime());
        assertEquals(expectedDto, actualDto);

        verify(labWorkOrderRepository).findById(workOrderId);
        verify(parameterAnalysisResultRepository).findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameter.getId());
        verify(labWorkOrderRepository).save(labWorkOrder);
        verify(parameterAnalysisResultRepository).save(par);
        verify(parameterAnalysisResultMapper).toDto(par);
    }

    @Test
    void updateAnalysisParameters_shouldThrowException_whenLabWorkOrderNotFound() {
        Long workOrderId = 1L;
        Long analysisParameterId = 2L;
        String result = "result";

        when(labWorkOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        assertThrows(LabWorkOrderNotFoundException.class, () -> labWorkOrdersService.updateAnalysisParameters(workOrderId, analysisParameterId, result));
        verify(parameterAnalysisResultRepository, never()).findByLabWorkOrderIdAndAnalysisParameterId(anyLong(), anyLong());
        verify(labWorkOrderRepository, never()).save(any());
        verify(parameterAnalysisResultRepository, never()).save(any());
        verify(parameterAnalysisResultMapper, never()).toDto(any());
    }

    @Test
    void updateAnalysisParameters_shouldThrowException_whenParameterAnalysisResultNotFound() {
        Long workOrderId = 1L;
        Long analysisParameterId = 2L;
        String result = "result";

        LabWorkOrder labWorkOrder = createLabWorkOrder();
        labWorkOrder.setStatus(OrderStatus.NEOBRADJEN);

        when(labWorkOrderRepository.findById(workOrderId)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameterId)).thenReturn(Optional.empty());

        assertThrows(NoParameterAnalysisResultFound.class, () -> labWorkOrdersService.updateAnalysisParameters(workOrderId, analysisParameterId, result));
        assertEquals(OrderStatus.NEOBRADJEN, labWorkOrder.getStatus());

        verify(labWorkOrderRepository).findById(workOrderId);
        verify(parameterAnalysisResultRepository).findByLabWorkOrderIdAndAnalysisParameterId(workOrderId, analysisParameterId);
        verify(labWorkOrderRepository, never()).save(any());
        verify(parameterAnalysisResultRepository, never()).save(any());
        verify(parameterAnalysisResultMapper, never()).toDto(any());
    }

    @Test
    void workOrdersHistory_shouldReturnPageOfLabWorkOrders() {
        String lbp = "lbp";
        Date fromDate = Date.valueOf("2023-01-01");
        Date toDate = new Date(fromDate.getTime() + 24 * 60 * 60 * 1000);
        int page = 0;
        int size = 10;

        List<LabWorkOrder> labWorkOrderList = new ArrayList<>();
        LabWorkOrder labWorkOrder1 = createLabWorkOrder();
        LabWorkOrder labWorkOrder2 = createLabWorkOrder();
        labWorkOrderList.add(labWorkOrder1);
        labWorkOrderList.add(labWorkOrder2);

        Pageable pageable = PageRequest.of(page, size);
        PageImpl<LabWorkOrder> expectedPage = new PageImpl<>(labWorkOrderList, pageable, labWorkOrderList.size());

        when(labWorkOrderRepository.workOrdersHistory(eq(pageable), eq(lbp), any(Date.class), any(Date.class))).thenReturn(expectedPage);

        Page<LabWorkOrder> actualPage = labWorkOrdersService.workOrdersHistory(lbp, fromDate, toDate, page, size);

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testFindWorkOrdersByLab() {
        String lbp = "testLab";
        Date fromDate = Date.valueOf("2023-01-01");
        Date toDate = new Date(fromDate.getTime() + 24 * 60 * 60 * 1000);
        OrderStatus status = OrderStatus.U_OBRADI;
        int page = 0;
        int size = 10;

        List<LabWorkOrder> expectedOrders = new ArrayList<>();
        expectedOrders.add(createLabWorkOrder());
        expectedOrders.add(createLabWorkOrder());
        expectedOrders.add(createLabWorkOrder());

        Pageable pageable = PageRequest.of(page, size);
        when(labWorkOrderRepository.findWorkOrdersByLab(any(Pageable.class), any(String.class), any(Date.class), any(Date.class), any(OrderStatus.class))).thenReturn(new PageImpl<>(expectedOrders, pageable, expectedOrders.size()));

        Page<LabWorkOrder> actualOrders = labWorkOrdersService.findWorkOrdersByLab(lbp, fromDate, toDate, status, page, size);

        assertEquals(expectedOrders, actualOrders.getContent());
        assertEquals(expectedOrders.size(), actualOrders.getTotalElements());
        assertEquals(pageable, actualOrders.getPageable());
    }

    @Test
    public void testFindParameterAnalysisResultsForWorkOrder() {
        Long id = 1L;

        LabWorkOrder labWorkOrder = createLabWorkOrder();

        List<ParameterAnalysisResult> parameterAnalysisResults = new ArrayList<>();
        parameterAnalysisResults.add(createParameterAnalysisResult());
        parameterAnalysisResults.add(createParameterAnalysisResult());

        LabWorkOrderWithAnalysisDto expectedDto = new LabWorkOrderWithAnalysisDto();

        when(labWorkOrderRepository.findById(id)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(eq(id), anyList())).thenReturn(parameterAnalysisResults);
        when(labWorkOrderWithAnalysisMapper.toDto(any(LabWorkOrder.class), anyList())).thenReturn(expectedDto);

        LabWorkOrderWithAnalysisDto actualDto = labWorkOrdersService.findParameterAnalysisResultsForWorkOrder(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    public void testFindParameterAnalysisResultsForWorkOrderWithInvalidId() {
        Long invalidId = -1L;

        when(labWorkOrderRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(LabWorkOrderNotFoundException.class, () -> {
            labWorkOrdersService.findParameterAnalysisResultsForWorkOrder(invalidId);
        });
    }

    @Test
    public void testDeleteWorkOrder() {
        LabWorkOrder labWorkOrder = createLabWorkOrder();

        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrder.getId())).thenReturn(new ArrayList<>());

        labWorkOrdersService.deleteWorkOrder(labWorkOrder);

        verify(parameterAnalysisResultRepository).deleteAll(anyList());
        verify(labWorkOrderRepository).delete(labWorkOrder);
    }

    @Test
    void testUpdateLabWorkOrderStatus() {
        Long id = 1L;
        LabWorkOrder labWorkOrder = createLabWorkOrder();
        labWorkOrder.setStatus(OrderStatus.U_OBRADI);

        when(labWorkOrderRepository.findLabWorkOrderById(id)).thenReturn(labWorkOrder);
        when(labWorkOrderRepository.save(labWorkOrder)).thenReturn(labWorkOrder);

        MessageDto result = labWorkOrdersService.updateLabWorkOrderStatus(id, OrderStatus.OBRADJEN);

        assertEquals("LabWorkOrder with ID " + id + " changed OrderStatus to " + OrderStatus.OBRADJEN + ". ", result.getMessage());
        assertEquals(OrderStatus.OBRADJEN, labWorkOrder.getStatus());

        verify(labWorkOrderRepository).findLabWorkOrderById(id);
        verify(labWorkOrderRepository).save(labWorkOrder);
    }

    @Test
    public void testFindWorkOrder() {
        LabWorkOrder labWorkOrder = new LabWorkOrder();
        labWorkOrder.setId(1L);
        labWorkOrder.setLbp("test-lbp");
        labWorkOrder.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
        labWorkOrder.setStatus(OrderStatus.U_OBRADI);

        LabAnalysis labAnalysis = new LabAnalysis();
        labAnalysis.setId(1L);
        labAnalysis.setAnalysisName("test-analysis-name");

        Parameter parameter = new Parameter();
        parameter.setId(1L);
        parameter.setParameterName("test-parameter-name");

        LabWorkOrderDto labWorkOrderDto = new LabWorkOrderDto();
        labWorkOrderDto.setLbp("test-lbp");
        labWorkOrderDto.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
        labWorkOrderDto.setStatus(OrderStatus.U_OBRADI);
        labWorkOrderDto.setPrescription(createPrescriptionDto());
        labWorkOrderDto.getPrescription().setDoctorLbz("test-doctor-lbz");
        labWorkOrderDto.setParameterAnalysisResults(new ArrayList<>());
        ParameterAnalysisResultDto parameterAnalysisResultDto = new ParameterAnalysisResultDto();
        parameterAnalysisResultDto.setResult("test-result");
        parameterAnalysisResultDto.setAnalysesParameters(new ArrayList<>());
        AnalysisParameterDto analysisParameterDto = new AnalysisParameterDto();
        analysisParameterDto.setId(1L);
        analysisParameterDto.setLabAnalysis(createLabAnalysisDto());
        analysisParameterDto.getLabAnalysis().setId(1L);
        analysisParameterDto.getLabAnalysis().setAnalysisName("test-analysis-name");
        analysisParameterDto.setParameter(createParameterDto());
        analysisParameterDto.getParameter().setId(1L);
        analysisParameterDto.getParameter().setParameterName("test-parameter-name");
        parameterAnalysisResultDto.getAnalysesParameters().add(analysisParameterDto);
        labWorkOrderDto.getParameterAnalysisResults().add(parameterAnalysisResultDto);

        when(labWorkOrderRepository.findById(1L)).thenReturn(Optional.of(labWorkOrder));
        when(labWorkOrderMapper.toDto(labWorkOrder)).thenReturn(labWorkOrderDto);

        LabWorkOrderDto result = labWorkOrdersService.findWorkOrder(1L);

        assertEquals(labWorkOrderDto, result);
    }

}
