package raf.bolnica1.laboratory.dto.lab.workOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ParameterAnalysisResultDto;
import raf.bolnica1.laboratory.dto.lab.prescription.PrescriptionDto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabWorkOrderDto implements Serializable {
    private PrescriptionDto prescription;
    private String lbp;
    private Timestamp creationDateTime;
    private OrderStatus status;
    private String technicianLbz;
    private String biochemistLbz;
    private List<ParameterAnalysisResultDto> parameterAnalysisResults;
}
