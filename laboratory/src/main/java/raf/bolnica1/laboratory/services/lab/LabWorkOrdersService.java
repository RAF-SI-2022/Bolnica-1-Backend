package raf.bolnica1.laboratory.services.lab;

import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.*;

public interface LabWorkOrdersService {

    LabWorkOrderDto createWorkOrder(Object dto);

    LabWorkOrderDto updateWorkOrder(Object dto);

    LabWorkOrderMessageDto verifyWorkOrder(Long id);

    UpdateParameterAnalysisResultMessageDto updateAnalysisParameters(Long workOrderId, Long parameterAnalysisId, String result);

    Object workOrdersHistory(Object object);

    Page<LabWorkOrder> findWorkOrdersByLab(String lbp, String fromDate, String toDate, OrderStatus status, Integer page, Integer size);

    LabWorkOrderWithAnalysisDto findParameterAnalysisResultsForWorkOrder(Long id);

}
