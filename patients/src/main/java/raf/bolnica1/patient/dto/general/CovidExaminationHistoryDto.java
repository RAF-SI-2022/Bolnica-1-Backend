package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class CovidExaminationHistoryDto implements Serializable {

    private Long id;
    private Date examDate;
    private String lbz;
    private Long medicalRecordId;

    private String lbp;
    private String symptoms;
    private String duration;
    private Double bodyTemperature;
    private Double bloodPressure;
    private Double saturation;
    private String lungCondition;
    private String therapy;


}

