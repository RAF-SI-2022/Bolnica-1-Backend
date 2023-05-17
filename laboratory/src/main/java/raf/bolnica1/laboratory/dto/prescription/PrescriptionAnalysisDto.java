package raf.bolnica1.laboratory.dto.prescription;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PrescriptionAnalysisDto implements Serializable {
    private Long analysisId;
    private List<Long> parametersIds;
}
