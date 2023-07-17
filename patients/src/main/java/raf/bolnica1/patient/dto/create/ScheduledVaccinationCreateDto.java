package raf.bolnica1.patient.dto.create;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class ScheduledVaccinationCreateDto implements Serializable {

    private Timestamp dateAndTime;
    private String note;
    private String lbz;
    private String vaccineName;
    private String lbp;

}
