package raf.bolnica1.infirmary.dto.externalPatientService;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.AnamnesisDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.DiagnosisCodeDto;

import java.sql.Date;

@Getter
@Setter
public class ExaminationHistoryCreateDto {

    private String lbp;
    private Date examDate;
    private String lbz;
    private boolean confidential;
    private String objectiveFinding;
    private String advice;
    private String therapy;

    private DiagnosisCodeDto diagnosisCodeDto;
    private AnamnesisDto anamnesisDto;

}
