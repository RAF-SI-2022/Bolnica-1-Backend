package raf.bolnica1.patient.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.ExaminationStatus;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.ScheduleExamRepository;
import raf.bolnica1.patient.services.PatientService;

import java.net.URI;
import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {

    private ScheduleExamRepository scheduleExamRepository;
    private ScheduleExamMapper scheduleExamMapper;
    private RestTemplate employeeRestTemplate;

    public PatientServiceImpl(ScheduleExamRepository scheduleExamRepository, ScheduleExamMapper scheduleExamMapper, @Qualifier("employeeRestTemplate") RestTemplate employeeRestTemplate) {
        this.scheduleExamRepository = scheduleExamRepository;
        this.scheduleExamMapper = scheduleExamMapper;
        this.employeeRestTemplate = employeeRestTemplate;
    }


    @Override
    public MessageDto schedule(ScheduleExamCreateDto scheduleExamCreateDto) {
        ScheduleExam scheduleExam = scheduleExamMapper.toEntity(scheduleExamCreateDto);
        scheduleExam = scheduleExamRepository.save(scheduleExam);
        return new MessageDto("Scheduled examination created");
    }

    @Override
    public List<ScheduleExamDto> findScheduledExamination(Object object) {
        return null;
    }

    @Override
    public MessageDto updateExaminationStatus(Object object) {
        return null;
    }

    @Override
    public MessageDto deleteScheduledExamination(Long id) {
        scheduleExamRepository.deleteScheduleExamById(id).orElseThrow(() -> new RuntimeException(String.format("Scheduled exam with id %d not found.", id)));
        return new MessageDto(String.format("Scheduled exam with id %d deleted",id));
    }

    @Override
    public List<EmployeeDto> findDoctorSpecByDepartment(String pbo, String token) {
        System.out.println(token);
        ParameterizedTypeReference<List<EmployeeDto>> responseType = new ParameterizedTypeReference<List<EmployeeDto>>() {};

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.substring(7));
        HttpEntity entity = new HttpEntity(null, httpHeaders);

        URI baseUri = URI.create("/find_doctor_specialists_by_department/" + pbo);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(baseUri);

        ResponseEntity<List<EmployeeDto>> employees = employeeRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType);

        return employees.getBody();
    }

    @Override
    public MessageDto updatePatientArrivalStatus(Long id, PatientArrival status) {
        ScheduleExam exam = scheduleExamRepository.getReferenceById(id);
        if(exam == null){
            return new MessageDto(String.format("Examination does not exist"));
        }
        exam.setArrivalStatus(status);
        if(status == PatientArrival.CANCELED){
            exam.setExaminationStatus(ExaminationStatus.CANCELED);
        }
        return new MessageDto(String.format("Arrival status updated into %s", status));
    }


}
