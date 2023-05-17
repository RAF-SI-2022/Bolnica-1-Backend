package raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.workOrder.LabWorkOrderDto;

import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultUpdateDto implements Serializable {
    private Long labWorkOrderId;
    private Long analysisParameterId;
    private String result;
    private Timestamp dateTime;
    private String biochemistLbz;
}
