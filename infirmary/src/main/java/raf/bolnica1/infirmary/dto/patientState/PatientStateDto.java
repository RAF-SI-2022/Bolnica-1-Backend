package raf.bolnica1.infirmary.dto.patientState;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;

import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


@Getter
@Setter
@NoArgsConstructor
public class PatientStateDto implements Serializable {

    private Long id;
    private Date dateExamState;
    private Time timeExamState;
    private float temperature;
    private int systolicPressure;
    private int diastolicPressure;
    private int pulse;
    private String therapy;
    private String description;
    private String lbz;
    private Long hospitalizationId;

}
