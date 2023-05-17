package raf.bolnica1.laboratory.dto.lab.workOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabWorkOrderWithAnalysisDto implements Serializable {
    private Long id;
    private Long prescriptionId;
    private String lbp;
    private Timestamp creationDateTime;
    private OrderStatus status;
    private String technicianLbz;
    private String biochemistLbz;
    private List<ParameterAnalysisResultWithDetailsDto> parameterAnalysisResults;

}
