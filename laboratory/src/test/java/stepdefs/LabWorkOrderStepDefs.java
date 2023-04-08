package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.ParameterAnalysisResult;
import raf.bolnica1.laboratory.exceptions.workOrder.CantVerifyLabWorkOrderException;
import raf.bolnica1.laboratory.mappers.LabWorkOrderMapper;
import raf.bolnica1.laboratory.mappers.LabWorkOrderWithAnalysisMapper;
import raf.bolnica1.laboratory.mappers.ParameterAnalysisResultMapper;
import raf.bolnica1.laboratory.repository.LabWorkOrderRepository;
import raf.bolnica1.laboratory.repository.ParameterAnalysisResultRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.LabWorkOrdersService;
import raf.bolnica1.laboratory.services.lab.PrescriptionService;

import java.util.Collections;
import java.util.Optional;

public class LabWorkOrderStepDefs {

    @Autowired
    private LabWorkOrdersService labWorkOrdersService;
    @MockBean
    private AuthenticationUtils authenticationUtils;
    @MockBean
    private LabWorkOrderRepository labWorkOrderRepository;
    @MockBean
    private ParameterAnalysisResultRepository parameterAnalysisResultRepository;
    @MockBean
    private LabWorkOrderWithAnalysisMapper labWorkOrderWithAnalysisMapper;
    @MockBean
    private ParameterAnalysisResultMapper parameterAnalysisResultMapper;
    @MockBean
    private LabWorkOrderMapper labWorkOrderMapper;
    @MockBean
    private PrescriptionService prescriptionService;
    @MockBean
    private PrescriptionRepository prescriptionRepository;

    private final LabWorkOrdersServiceSharedState sharedState = new LabWorkOrdersServiceSharedState();

    @Given("a lab work order with id {string}")
    public void aLabWorkOrderWithId(String id) {
        Long labWorkOrderId = Long.parseLong(id);
        sharedState.setLabWorkOrderId(labWorkOrderId);

        LabWorkOrder labWorkOrder = new LabWorkOrder();
        labWorkOrder.setId(labWorkOrderId);

        ParameterAnalysisResult par = new ParameterAnalysisResult();
        par.setResult(null);

        Mockito.when(labWorkOrderRepository.findById(labWorkOrderId)).thenReturn(Optional.of(labWorkOrder));
        Mockito.when(parameterAnalysisResultRepository.findParameterAnalysisResultsByLabWorkOrderId(labWorkOrderId)).thenReturn(Collections.singletonList(par));
    }

    @When("the work order is verified")
    public void theWorkOrderIsVerified() {
        Assertions.assertThrows(CantVerifyLabWorkOrderException.class, () -> labWorkOrdersService.verifyWorkOrder(sharedState.getLabWorkOrderId()));
    }

    @Then("a CantVerifyLabWorkOrderException is thrown")
    public void aCantVerifyLabWorkOrderExceptionIsThrown() {
        // The exception is already checked in the @When step, so no further action is needed here.
    }
}
