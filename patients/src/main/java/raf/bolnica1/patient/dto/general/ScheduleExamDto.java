package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ScheduleExamDto {
    private Long id;
    private Timestamp dateAndTime;
    private boolean arrived;
    private Long doctorId;
    private Long lbz;
    private String lbp;
}
