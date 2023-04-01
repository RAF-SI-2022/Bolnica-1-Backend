package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.services.AppointmentService;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private PrescriptionRepository prescriptionRepository;
}
