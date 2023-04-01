package raf.bolnica1.infirmary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleAppointmentDto {
    private String lbp;
    private Timestamp appointmentDateAndTime;
    private String note;

}
