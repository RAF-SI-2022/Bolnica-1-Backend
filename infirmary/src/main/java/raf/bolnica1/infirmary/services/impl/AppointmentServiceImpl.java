package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.security.utils.JwtUtils;
import raf.bolnica1.infirmary.services.AppointmentService;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private PrescriptionRepository prescriptionRepository;
    private RestTemplate departmentRestTemplate;
    private JwtUtils jwtUtils;

    @Override
    public String createAppointment(String authorization, ScheduleAppointmentDto appointmentDto) {
        ScheduledAppointment scheduledAppointment = new ScheduledAppointment();
        scheduledAppointment.setNote(appointmentDto.getNote());
        scheduledAppointment.setPatientAdmission(appointmentDto.getAppointmentDateAndTime());
        scheduledAppointment.setAdmissionStatus(AdmissionStatus.ZAKAZAN);

        String token = authorization.split(" ")[1];
        String lbz = jwtUtils.getUsernameFromToken(token);

        scheduledAppointment.setLbzScheduler(lbz);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<Long> response = departmentRestTemplate.exchange("employee/"+lbz, HttpMethod.GET, request, Long.class);
        if(response.getBody() == null)
            throw new RuntimeException("Invalid LPZ!");

        Prescription prescription = new Prescription();
        prescription.setLbp(appointmentDto.getLbp());
        prescription.setGetIdDepartmentTo(response.getBody());

        scheduledAppointment.setPrescription(prescriptionRepository.save(prescription));

        scheduledAppointmentRepository.save(scheduledAppointment);
        return "Appointment created successfully!";
    }
}
