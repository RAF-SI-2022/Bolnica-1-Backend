package raf.bolnica1.laboratory.dto.prescription;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PrescriptionUpdateDto {
    private Long id;
    private Long departmentFromId;
    private Long departmentToId;
    private Timestamp creationDateTime;
    private String comment;
    private List<PrescriptionAnalysisDto> prescriptionAnalysisDtos;
}
