package raf.bolnica1.laboratory.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private Long id;
    private String type;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private String doctorLbz;
    private String comment;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;
    private List<PrescriptionAnalysisDataDto> prescriptionAnalysisDataDtoList;
}
