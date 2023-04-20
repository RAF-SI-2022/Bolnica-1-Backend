package raf.bolnica1.infirmary.dto.prescription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PrescriptionReceiveDto {

    private PrescriptionType type;
    private String doctorLbz;
    private Long departmentFromId;
    private Long departmentToId;
    private String lbp;
    private Timestamp creationDateTime;
    private PrescriptionStatus status;

    private String referralDiagnosis;
    private String referralReason;


}
