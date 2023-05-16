package raf.bolnica1.laboratory.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PrescriptionCreateDto {

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

    @Override
    public String toString() {
        return "PrescriptionCreateDto{" +
                "pid=" + pid +
                ", type=" + type +
                ", doctorLbz='" + doctorLbz + '\'' +
                ", departmentFromId=" + departmentFromId +
                ", departmentToId=" + departmentToId +
                ", lbp='" + lbp + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", prescriptionAnalysisDtos=" + prescriptionAnalysisDtos +
                '}';
    }
}
