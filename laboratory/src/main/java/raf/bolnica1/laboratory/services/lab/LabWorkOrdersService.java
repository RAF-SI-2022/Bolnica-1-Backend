package raf.bolnica1.laboratory.services.lab;


import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.*;
import raf.bolnica1.laboratory.dto.response.MessageDto;

import java.sql.Date;

public interface LabWorkOrdersService {

    MessageDto registerPatient(String lbp);


    LabWorkOrder createWorkOrder(Prescription prescription);

    LabWorkOrderDto updateWorkOrder(Object dto);

    LabWorkOrderMessageDto verifyWorkOrder(Long id);

    UpdateParameterAnalysisResultMessageDto updateAnalysisParameters(Long workOrderId, Long parameterAnalysisId, String result);

    Page<LabWorkOrder> workOrdersHistory(String lbp, Date fromDate, Date toDate, Integer page, Integer size);

    Page<LabWorkOrder> findWorkOrdersByLab(String lbp, Date fromDate, Date toDate, OrderStatus status, Integer page, Integer size);

    LabWorkOrderWithAnalysisDto findParameterAnalysisResultsForWorkOrder(Long id);

    void deleteWorkOrder(LabWorkOrder labWorkOrder);

    MessageDto updateLabWorkOrderStatus(Long id,OrderStatus orderStatus);

}
