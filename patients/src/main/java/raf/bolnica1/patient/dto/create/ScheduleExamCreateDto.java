package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class ScheduleExamCreateDto {
    private Timestamp dateAndTime;
    private Long doctorId;
    private Long lbz;
    private String lbp;
    private String note;
}
