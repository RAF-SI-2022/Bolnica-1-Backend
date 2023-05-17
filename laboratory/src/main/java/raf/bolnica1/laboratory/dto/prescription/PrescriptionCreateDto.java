package raf.bolnica1.laboratory.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PrescriptionCreateDto implements Serializable {

    private Long pid;
    private PrescriptionType type;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;
    private String comment;
    private List<PrescriptionAnalysisDto> prescriptionAnalysisDtos;
}
