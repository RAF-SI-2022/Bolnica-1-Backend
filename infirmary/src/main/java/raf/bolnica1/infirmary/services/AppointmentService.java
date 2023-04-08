package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;

import java.sql.Date;

public interface AppointmentService {

    String createAppointment(ScheduleAppointmentDto appointmentDto);
    String updateAppointment(String authorization, String status, Integer id);
    Page<ScheduleAppointmentDto> getScheduledAppointments(Long depId, String lbp, Date date, int page, int size);
}
