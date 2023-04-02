package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;

import java.sql.Date;
import java.util.List;

public interface AppointmentService {

    String createAppointment(String authorization, ScheduleAppointmentDto appointmentDto);
    Page<ScheduleAppointmentDto> getScheduledAppointments(String authorization, String lbp, Date date, int page, int size);
}
