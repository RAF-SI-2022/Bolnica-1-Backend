package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.patient.domain.MedicalRecord;

import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class CovidExaminationHistoryCreateDto implements Serializable {

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
