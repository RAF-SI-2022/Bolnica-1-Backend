package raf.bolnica1.patient.services.impl;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.exceptions.jwt.NotAuthenticatedException;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.ScheduleExamRepository;
import raf.bolnica1.patient.services.PatientService;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        ScheduleExam scheduleExam = scheduleExamMapper.toEntity(scheduleExamCreateDto, getLbzFromAuthentication());
        scheduleExam = scheduleExamRepository.save(scheduleExam);
        return new MessageDto("Uspesno kreiran zakazani pregled.");
    }

    @Override
    public List<ScheduleExamDto> findScheduledExaminations() {
        List<ScheduleExamDto> exams = scheduleExamRepository.findAll().stream().map(scheduleExamMapper::toDto).collect(Collectors.toList());
        return exams;
    }

    @Override
    public MessageDto updateExaminationStatus(Object object) {
        return null;
    }

    @Transactional
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
        Optional<ScheduleExam> exam = scheduleExamRepository.findById(id);
        if(!exam.isPresent()){
            return new MessageDto("Pregled nije pronadjen.");
        }
        exam.get().setArrivalStatus(status);
        scheduleExamRepository.save(exam.get());
        return new MessageDto(String.format("Status pregleda promenjen u %s", status));
    }

    @Override
    public Page<ScheduleExamDto> findScheduledExaminationsForDoctor(String lbz, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleExam> scheduleExams = scheduleExamRepository.findScheduleForDoctor(pageable, lbz, PatientArrival.CEKA);
        return scheduleExams.map(scheduleExamMapper::toDto);
    }

    @Override
    public Page<ScheduleExamDto> findScheduledExaminationsForMedSister(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleExam> scheduleExams = scheduleExamRepository.findScheduleForMedSister(pageable, new Date(System.currentTimeMillis()), PatientArrival.ZAKAZANO);
        return scheduleExams.map(scheduleExamMapper::toDto);
    }

    @Override
    public List<ScheduleExamDto> findScheduledExaminationsForDoctorAll(String lbz) {
        List<ScheduleExam> scheduleExams = scheduleExamRepository.findFromCurrDateAndDoctor(new Date(System.currentTimeMillis()), lbz);
        List<ScheduleExamDto> scheduleExamDtoList = new ArrayList<>();
        for(ScheduleExam scheduleExam : scheduleExams){
            ScheduleExamDto scheduleExamDto = scheduleExamMapper.toDto(scheduleExam);
            scheduleExamDtoList.add(scheduleExamDto);
        }
        return scheduleExamDtoList;
    }

    private String getLbzFromAuthentication(){
        String lbz = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            lbz = (String) authentication.getPrincipal();
        }
        // temp linija, treba malo refaktorisati
//        if(lbz == null) throw new NotAuthenticatedException("Something went wrong.");
        return lbz;
    }


}
