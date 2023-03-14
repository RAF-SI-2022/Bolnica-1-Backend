package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ExaminationHistoryDto {

    private Date examDate;
    private String lbz;
    private boolean confidential;
    private String objectiveFinding;
    private String advice;

    private DiagnosisCodeDto diagnosisCodeDto;
    private TherapyDto therapyDto;
    private AnamnesisDto anamnesisDto;

}
