package raf.bolnica1.patient.dto.prescription.diagnostic;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionSendDto;

@Getter
@Setter
public class PrescriptionDiagnosticSendDto extends PrescriptionSendDto {
    private String referralDiagnosis;
    private String referralReason;
}
