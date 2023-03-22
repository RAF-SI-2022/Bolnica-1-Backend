package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.domain.lab.LabWorkOrder;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.UpdateParameterAnalysisResultMessageDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.*;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface LabWorkOrdersService {

    LabWorkOrder createWorkOrder(Long prescriptionId);

    LabWorkOrderDto updateWorkOrder(Object dto);

    LabWorkOrderMessageDto verifyWorkOrder(Long id);

    UpdateParameterAnalysisResultMessageDto updateAnalysisParameters(Long workOrderId, Long parameterAnalysisId, String result);

    Object workOrdersHistory(Object object);

    Object findWorkOrdersByLab(Object object);

    LabWorkOrderWithAnalysisDto findParameterAnalysisResultsForWorkOrder(Long id);

}
