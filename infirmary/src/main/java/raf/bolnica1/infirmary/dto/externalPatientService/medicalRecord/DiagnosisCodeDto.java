package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisCodeDto {

    private String code;
    private String description;
    private String latinDescription;
}

