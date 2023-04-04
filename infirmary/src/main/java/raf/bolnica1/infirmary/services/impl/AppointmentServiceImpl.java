package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import raf.bolnica1.infirmary.mapper.ScheduledAppointmentMapper;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.repository.ScheduledAppointmentRepository;
import raf.bolnica1.infirmary.security.utils.JwtUtils;
import raf.bolnica1.infirmary.services.AppointmentService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private ScheduledAppointmentRepository scheduledAppointmentRepository;
    private PrescriptionRepository prescriptionRepository;
    private RestTemplate departmentRestTemplate;
    private JwtUtils jwtUtils;
    private ScheduledAppointmentMapper scheduledAppointmentMapper;

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

        Optional<Prescription> prescription = prescriptionRepository.findByLbp(appointmentDto.getLbp());
        if(!prescription.isPresent())
            throw new RuntimeException("No prescription for patient with lbp: " + appointmentDto.getLbp());

        prescription.get().setGetIdDepartmentTo(response.getBody());

        scheduledAppointment.setPrescription(prescriptionRepository.save(prescription.get()));
        scheduledAppointmentRepository.save(scheduledAppointment);

        return "Appointment created successfully!";
    }

    public String updateAppointment(String authorization, String status, Integer id) {

        String token = authorization.split(" ")[1];
        String lbz = jwtUtils.getUsernameFromToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<Long> response = departmentRestTemplate.exchange("employee/"+lbz, HttpMethod.GET, request, Long.class);
        if(response.getBody() == null)
            throw new RuntimeException("Invalid LPZ!");

        ScheduledAppointment scheduledAppointment = scheduledAppointmentRepository.findAppointment(id);
        scheduledAppointment.setAdmissionStatus(AdmissionStatus.valueOf(status));
        scheduledAppointmentRepository.save(scheduledAppointment);

        return "Appointment updated successfully!";
    }

    @Override
    public Page<ScheduleAppointmentDto> getScheduledAppointments(String authorization, String lbp, Date date, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        String token = authorization.split(" ")[1];
        String lbz = jwtUtils.getUsernameFromToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<Long> response = departmentRestTemplate.exchange("employee/"+lbz, HttpMethod.GET, request, Long.class);
        if(response.getBody() == null)
            throw new RuntimeException("Invalid LPZ!");

        Page<ScheduledAppointment> listPage =  scheduledAppointmentRepository.findAppointment(pageable, response.getBody(), lbp, date);

        return listPage.map(scheduledAppointmentMapper::toDto);

    }
}
