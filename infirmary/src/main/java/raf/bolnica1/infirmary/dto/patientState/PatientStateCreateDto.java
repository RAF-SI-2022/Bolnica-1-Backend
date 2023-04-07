package raf.bolnica1.infirmary.dto.patientState;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class PatientStateCreateDto {

    private Date dateExamState;
    private Time timeExamState;
    private float temperature;
    private int systolicPressure;
    private int diastolicPressure;
    private int pulse;
    private String therapy;
    private String description;
    private Long hospitalizationId;

}
