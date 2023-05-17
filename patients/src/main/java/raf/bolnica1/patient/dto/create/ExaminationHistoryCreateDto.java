package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.general.AnamnesisDto;
import raf.bolnica1.patient.dto.general.DiagnosisCodeDto;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class ExaminationHistoryCreateDto implements Serializable {

    private Date examDate;
    private String lbz;
    private boolean confidential;
    private String objectiveFinding;
    private String advice;
    private String therapy;

    private DiagnosisCodeDto diagnosisCodeDto;
    private AnamnesisDto anamnesisDto;

}
