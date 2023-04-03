package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.ScheduledLabExaminationMapper;
import raf.bolnica1.laboratory.repository.ScheduledLabExaminationRepository;
import raf.bolnica1.laboratory.security.util.AuthenticationUtils;
import raf.bolnica1.laboratory.services.lab.LabExaminationsService;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class LabExaminationsServiceImpl implements LabExaminationsService {

    private AuthenticationUtils authenticationUtils;
    private ScheduledLabExaminationMapper scheduledLabExaminationMapper;
    private ScheduledLabExaminationRepository scheduledLabExaminationRepository;

    @Qualifier("employeeRestTemplate")
    private RestTemplate employeeRestTemplate;

    @Override
    public MessageDto createScheduledExamination(String lbp, Date scheduledDate, String note, String token) {

        String lbz = authenticationUtils.getLbzFromAuthentication();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Long> departmentId = employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class);

        ScheduledLabExamination scheduledLabExamination = scheduledLabExaminationMapper.toEntity(departmentId.getBody(), lbp, scheduledDate, note, lbz);
        scheduledLabExaminationRepository.save(scheduledLabExamination);

        return new MessageDto(String.format("Uspesno kreiran zakazani laboratorijski pregled za pacijenta %s\n", lbp));
    }

    @Override
    public Object changeExaminationStatus(Object object) {
        return null;
    }

    @Override
    public List<ScheduledLabExaminationDto> listScheduledExaminationsByDay(Long date, String token) {
        String lbz = authenticationUtils.getLbzFromAuthentication();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Long> departmentId = employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class);

        Date sqlDate = new Date(date);
        return scheduledLabExaminationMapper.toDto(scheduledLabExaminationRepository.findScheduledLabExaminationsByDateAndDepartmentId(sqlDate, departmentId.getBody()));
    }

    @Override
    public List<ScheduledLabExaminationDto> listScheduledExaminations(String token) {
        String lbz = authenticationUtils.getLbzFromAuthentication();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Long> departmentId = employeeRestTemplate.exchange("/department/employee/" + lbz, HttpMethod.GET, httpEntity, Long.class);

        return scheduledLabExaminationMapper.toDto(scheduledLabExaminationRepository.findScheduledLabExaminationsByDepartmentId(departmentId.getBody()));
    }
}
