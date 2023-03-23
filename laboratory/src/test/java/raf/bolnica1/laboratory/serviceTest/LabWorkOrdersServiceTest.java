package raf.bolnica1.laboratory.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.exceptions.workOrder.DateParseException;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.services.lab.impl.LabWorkOrdersServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LabWorkOrdersServiceTest {

    @Mock
    private LabWorkOrderRepository labWorkOrderRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private LabWorkOrdersServiceImpl labWorkOrdersService;

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
        prescription1.setDoctorId(101L);
        prescription1.setDepartmentFromId(201L);
        prescription1.setDepartmentToId(202L);
        prescription1.setLbp("10001L");
        prescription1.setCreationDateTime(Timestamp.valueOf("2023-03-18 10:00:00"));
        prescription1.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ///prescription1.setRequestedTests("Test 1, Test 2");
        prescription1.setComment("Urgent");
        prescription1.setReferralDiagnosis("Diagnosis");
        prescription1.setReferralReason("Reason");

        Prescription prescription2 = new Prescription();
        prescription2.setId(2L);
        prescription2.setType(PrescriptionType.DIJAGNOSTIKA);
        prescription2.setDoctorId(102L);
        prescription2.setDepartmentFromId(203L);
        prescription2.setDepartmentToId(204L);
        prescription2.setLbp("10002L");
        prescription2.setCreationDateTime(Timestamp.valueOf("2023-03-18 11:00:00"));
        prescription2.setStatus(PrescriptionStatus.NEREALIZOVAN);
        ///prescription2.setRequestedTests("Test 3, Test 4");
        prescription2.setComment("Regular");
        prescription2.setReferralDiagnosis("Another Diagnosis");
        prescription2.setReferralReason("Another Reason");

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
}
