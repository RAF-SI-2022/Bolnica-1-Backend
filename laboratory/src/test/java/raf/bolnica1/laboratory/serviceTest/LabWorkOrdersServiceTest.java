package raf.bolnica1.laboratory.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderWithAnalysisDto;
import raf.bolnica1.laboratory.exceptions.workOrder.CantVerifyLabWorkOrderException;
import raf.bolnica1.laboratory.exceptions.workOrder.DateParseException;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;
import raf.bolnica1.laboratory.services.lab.impl.LabWorkOrdersServiceImpl;
import raf.bolnica1.laboratory.services.lab.impl.PrescriptionServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabWorkOrdersServiceTest {

    @Mock
    private AuthenticationUtils authenticationUtils;

    @Mock
    private LabWorkOrderRepository labWorkOrderRepository;

    @Mock
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;

    @Mock
    private LabWorkOrderMapper labWorkOrderMapper;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private LabWorkOrdersServiceImpl labWorkOrdersService;

    @Mock
    private PrescriptionServiceImpl prescriptionService;

    @Mock
    private ParameterAnalysisResultMapper parameterAnalysisResultMapper;

    @Mock
    private LabWorkOrderWithAnalysisMapper labWorkOrderWithAnalysisMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private GrantedAuthority grantedAuthority;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findLabWorkOrders_whenDatePassedInWrongFormat_shouldThrowDateParseException() {
        String lbp = "L0001";
        String fromDate = "2018-04-18";
        String toDate = "invalid date format";
        OrderStatus status = OrderStatus.NEOBRADJEN;
        int page = 0;
        int size = 2;

        assertThrows(DateParseException.class, () -> labWorkOrdersService.findWorkOrdersByLab(lbp, fromDate, toDate, status, page, size));
    }

    @Test
    void historyLabWorkOrders_whenDatePassedInWrongFormat_shouldThrowDateParseException() {
        String lbp = "L0001";
        String fromDate = "201804-18";
        String toDate = "invalid date format";
        OrderStatus status = OrderStatus.NEOBRADJEN;
        int page = 0;
        int size = 2;

        assertThrows(DateParseException.class, () -> labWorkOrdersService.findWorkOrdersByLab(lbp, fromDate, toDate, status, page, size));
    }

    @Test
    void findLabWorkOrders_whenAllArgumentsExist_shouldGiveResults() {

        List<LabWorkOrder> workOrders = generateLabWorkOrders();
        Page<LabWorkOrder> p = new PageImpl<>(workOrders);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String lbp = "L0001";
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat.parse("2020-01-01");
            toDate = labWorkOrdersService.lastSecondOfTheDay(dateFormat.parse("2020-01-03"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
        OrderStatus status = OrderStatus.NEOBRADJEN;
        int page = 0;
        int size = 2;

        given(labWorkOrderRepository.findWorkOrdersByLab(PageRequest.of(page, size), lbp, fromDate, toDate, status)).willReturn(p);

        assertEquals(labWorkOrdersService.findWorkOrdersByLab(lbp, "2020-01-01", "2020-01-03", status, page, size).getContent(), workOrders);
    }

    @Test
    void historyLabWorkOrders_whenAllArgumentsExist_shouldGiveResults() {

        List<LabWorkOrder> workOrders = generateLabWorkOrders();
        Page<LabWorkOrder> p = new PageImpl<>(workOrders);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String lbp = "L0001";
        Date fromDate = null;
        Date toDate = null;

        try {
            fromDate = dateFormat.parse("2020-01-01");
            toDate = labWorkOrdersService.lastSecondOfTheDay(dateFormat.parse("2020-01-03"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        int page = 0;
        int size = 2;

        given(labWorkOrderRepository.workOrdersHistory(PageRequest.of(page, size), lbp, fromDate, toDate)).willReturn(p);

        assertEquals(labWorkOrdersService.workOrdersHistory(lbp, "2020-01-01", "2020-01-03", page, size).getContent(), workOrders);
    }

    @Test
    void lastSecondOfTheDay_whenPassedDate_shouldReturnLastMilisecondOfTheDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date day = null;
        try {
            day = simpleDateFormat.parse("2020-01-01 12:34:56.789");
        } catch(Exception e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(simpleDateFormat.parse("2020-01-01 23:59:59.999"), labWorkOrdersService.lastSecondOfTheDay(day));
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }


    private ArrayList<LabWorkOrder> generateLabWorkOrders() {
        // prescriptions
        Prescription prescription1 = new Prescription();
        prescription1.setId(1L);
        prescription1.setType(PrescriptionType.LABORATORIJA);
        prescription1.setDoctorLbz("E0003");
        prescription1.setDepartmentFromId(201L);
        prescription1.setDepartmentToId(202L);
        prescription1.setLbp("10001L");
        prescription1.setCreationDateTime(Timestamp.valueOf("2023-03-18 10:00:00"));
        prescription1.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ///prescription1.setRequestedTests("Test 1, Test 2");
        prescription1.setComment("Urgent");

        Prescription prescription2 = new Prescription();
        prescription2.setId(2L);
        prescription2.setType(PrescriptionType.DIJAGNOSTIKA);
        prescription2.setDoctorLbz("E0003");
        prescription2.setDepartmentFromId(203L);
        prescription2.setDepartmentToId(204L);
        prescription2.setLbp("10002L");
        prescription2.setCreationDateTime(Timestamp.valueOf("2023-03-18 11:00:00"));
        prescription2.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ///prescription2.setRequestedTests("Test 3, Test 4");
        prescription2.setComment("Regular");

        prescriptionRepository.saveAll(Arrays.asList(prescription1, prescription2));

        // work orders
        LabWorkOrder labWorkOrder1 = new LabWorkOrder();
        labWorkOrder1.setId(1L);
        labWorkOrder1.setPrescription(prescription1);
        labWorkOrder1.setLbp("20001L");
        labWorkOrder1.setCreationDateTime(Timestamp.valueOf("2023-03-18 12:00:00"));
        labWorkOrder1.setStatus(OrderStatus.NEOBRADJEN);
        labWorkOrder1.setTechnicianLbz("ABC123");

        LabWorkOrder labWorkOrder2 = new LabWorkOrder();
        labWorkOrder2.setId(2L);
        labWorkOrder2.setPrescription(prescription2);
        labWorkOrder2.setLbp("20002L");
        labWorkOrder2.setCreationDateTime(Timestamp.valueOf("2023-03-18 13:00:00"));
        labWorkOrder2.setStatus(OrderStatus.NEOBRADJEN);
        labWorkOrder2.setTechnicianLbz("ABC1233");

        LabWorkOrder labWorkOrder3 = new LabWorkOrder();
        labWorkOrder3.setId(3L);
        labWorkOrder3.setPrescription(prescription2);
        labWorkOrder3.setLbp("20001L");
        labWorkOrder3.setCreationDateTime(Timestamp.valueOf("2023-03-19 13:00:00"));
        labWorkOrder3.setStatus(OrderStatus.NEOBRADJEN);
        labWorkOrder3.setTechnicianLbz("ABC1233");

        labWorkOrderRepository.saveAll(Arrays.asList(labWorkOrder1, labWorkOrder2, labWorkOrder3));
        return new ArrayList<LabWorkOrder>(Arrays.asList(labWorkOrder1, labWorkOrder2, labWorkOrder3));
    }

    @Test
    void testVerifyWorkOrder_whenLabWorkOrderExists() {
        // Arrange
        Long id = 1L;
        LabWorkOrder labWorkOrder = new LabWorkOrder();
        labWorkOrder.setId(id);
        labWorkOrder.setPrescription(new Prescription());
        LabWorkOrderMessageDto labWorkOrderMessageDto = new LabWorkOrderMessageDto("Verifikovan radni nalog", new LabWorkOrderDto());

        when(authenticationUtils.getLbzFromAuthentication()).thenReturn("mockLbz");
        when(labWorkOrderRepository.findById(id)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(id)).thenReturn(Collections.emptyList());
        when(labWorkOrderMapper.toDto(labWorkOrder)).thenReturn(new LabWorkOrderDto());

        // Act
        LabWorkOrderMessageDto result = labWorkOrdersService.verifyWorkOrder(id);

        // Assert
        assertEquals(labWorkOrderMessageDto.getMessage(), result.getMessage());
        verify(labWorkOrderRepository).save(labWorkOrder);
        verify(prescriptionService).updatePrescriptionStatus(eq(labWorkOrder.getPrescription().getId()), eq(PrescriptionStatus.REALIZOVAN));
    }

    @Test
    void testVerifyWorkOrder_whenLabWorkOrderNotFound() {
        // Arrange
        Long id = 1L;
        when(labWorkOrderRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(LabWorkOrderNotFoundException.class, () -> labWorkOrdersService.verifyWorkOrder(id));
    }

    @Test
    void testVerifyWorkOrder_whenResultsNotEntered() {
        // Arrange
        Long id = 1L;
        LabWorkOrder labWorkOrder = new LabWorkOrder();
        labWorkOrder.setId(id);

        ParameterAnalysisResult par = new ParameterAnalysisResult();
        par.setResult(null);

        when(labWorkOrderRepository.findById(id)).thenReturn(Optional.of(labWorkOrder));
        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(id)).thenReturn(Collections.singletonList(par));

        // Act and Assert
        assertThrows(CantVerifyLabWorkOrderException.class, () -> labWorkOrdersService.verifyWorkOrder(id));
    }

    @Test
    public void testUpdateAnalysisParameters() {
        // Set up test data
        Long workOrderId = 1L;
        Long analysisParameterId = 1L;
        String result = "test result";
        String lbz = "testLbz";

        LabWorkOrder mockLabWorkOrder = new LabWorkOrder();
        mockLabWorkOrder.setStatus(OrderStatus.NEOBRADJEN);

        ParameterAnalysisResult mockPar = new ParameterAnalysisResult();

        UpdateParameterAnalysisResultMessageDto mockDto = new UpdateParameterAnalysisResultMessageDto();

        // Mock repository and utility methods
        when(labWorkOrderRepository.findById(eq(workOrderId))).thenReturn(Optional.of(mockLabWorkOrder));
        when(parameterAnalysisResultRepository.findByLabWorkOrderIdAndAnalysisParameterId(eq(workOrderId), eq(analysisParameterId))).thenReturn(Optional.of(mockPar));
        when(authenticationUtils.getLbzFromAuthentication()).thenReturn(lbz);
        when(parameterAnalysisResultMapper.toDto(any(ParameterAnalysisResult.class))).thenReturn(mockDto);

        // Call the method to test
        UpdateParameterAnalysisResultMessageDto resultDto = labWorkOrdersService.updateAnalysisParameters(workOrderId, analysisParameterId, result);

        // Verify the interactions with the mocked objects and check the expected outcomes
        assertEquals(mockDto, resultDto);
        assertEquals(OrderStatus.U_OBRADI, mockLabWorkOrder.getStatus());
        assertEquals(lbz, mockPar.getBiochemistLbz());
        assertEquals(result, mockPar.getResult());
        verify(labWorkOrderRepository, times(1)).save(mockLabWorkOrder);
        verify(parameterAnalysisResultRepository, times(1)).save(mockPar);
    }

    @Test
    public void testFindParameterAnalysisResultsForWorkOrder() {
        // Set up test data
        Long workOrderId = 1L;

        LabWorkOrder mockLabWorkOrder = new LabWorkOrder();
        List<ParameterAnalysisResult> mockParameterAnalysisResults = new ArrayList<>();
        LabWorkOrderWithAnalysisDto mockDto = new LabWorkOrderWithAnalysisDto();

        // Mock repository and mapper methods
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer((Answer<List<GrantedAuthority>>) invocation -> Collections.singletonList(grantedAuthority));
        when(grantedAuthority.getAuthority()).thenReturn("ROLE_MED_BIOHEMICAR");
        when(labWorkOrderRepository.findById(eq(workOrderId))).thenReturn(Optional.of(mockLabWorkOrder));
        when(parameterAnalysisResultRepository.findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(eq(workOrderId), any())).thenReturn(mockParameterAnalysisResults);
        when(labWorkOrderWithAnalysisMapper.toDto(eq(mockLabWorkOrder), eq(mockParameterAnalysisResults))).thenReturn(mockDto);

        // Call the method to test
        LabWorkOrderWithAnalysisDto resultDto = labWorkOrdersService.findParameterAnalysisResultsForWorkOrder(workOrderId);

        // Verify the interactions with the mocked objects and check the expected outcomes
        assertEquals(mockDto, resultDto);
        verify(labWorkOrderRepository, times(1)).findById(eq(workOrderId));
        verify(parameterAnalysisResultRepository, times(1)).findParameterAnalysisResultsByWorkOrderIdAndAllowedStatuses(eq(workOrderId), any());
        verify(labWorkOrderWithAnalysisMapper, times(1)).toDto(eq(mockLabWorkOrder), eq(mockParameterAnalysisResults));
    }

}

