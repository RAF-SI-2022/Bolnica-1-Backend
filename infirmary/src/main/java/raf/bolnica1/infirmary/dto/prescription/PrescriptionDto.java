package raf.bolnica1.infirmary.dto.prescription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;
import raf.bolnica1.infirmary.domain.constants.PrescriptionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PrescriptionDto {

    private Long id;
    private String doctorLbz;
    private Long idDepartmentFrom;
    private Long idDepartmentTo;
    private String lbp;
    private Timestamp creation;
    private PrescriptionType prescriptionType;
    private PrescriptionStatus prescriptionStatus;
    private String referralDiagnosis;
    private String referralReason;

}
