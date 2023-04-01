package raf.bolnica1.infirmary.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ScheduleAppointmentDto {
    private String lbp;
    private Timestamp appointmentDateAndTime;
    private String note;

}
