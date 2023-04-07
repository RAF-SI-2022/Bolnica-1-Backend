package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ExaminationHistoryDto {

    private Long id;
    private Date examDate;
    private String lbz;
    private boolean confidential;
    private String objectiveFinding;
    private String advice;
    private String therapy;

    private DiagnosisCodeDto diagnosisCodeDto;
    private AnamnesisDto anamnesisDto;

}
