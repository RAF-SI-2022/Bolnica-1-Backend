package raf.bolnica1.patient.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.PrescriptionType;

import java.util.List;

@Getter
@Setter
public class PrescriptionCreateDto {
    private PrescriptionType type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private Long doctorId;
    private String comment;
    private String referralDiagnosis;
    private String referralReason;
    private List<PrescriptionAnalysisDto> prescriptionAnalysisDtos;
}
