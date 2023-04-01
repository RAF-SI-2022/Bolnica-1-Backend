package raf.bolnica1.infirmary.services;

import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;

public interface AppointmentService {

    String createAppointment(String authorization, ScheduleAppointmentDto appointmentDto);
}
