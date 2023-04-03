package raf.bolnica1.laboratory.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PrescriptionDoneDto {
    private Long id;
    private String type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private String doctorLbz;
    private Date date;
    private String comment;
    private PrescriptionStatus prescriptionStatus;
    private List<PrescriptionAnalysisNameDto> parameters = new ArrayList<>();
}
