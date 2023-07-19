package raf.bolnica1.patient.services.impl;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.create.ScheduledVaccinationCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.exceptions.jwt.NotAuthenticatedException;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.mapper.ScheduledVaccinationMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.PatientService;
import raf.bolnica1.patient.util.PDFUtils;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;
    private ScheduleExamRepository scheduleExamRepository;
    private ScheduleExamMapper scheduleExamMapper;
    private RestTemplate employeeRestTemplate;
    private ScheduledVaccinationMapper scheduledVaccinationMapper;
    private VaccinationRepository vaccinationRepository;
    private ScheduledVaccinationRepository scheduledVaccinationRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private VaccinationDataRepository vaccinationDataRepository;


    public PatientServiceImpl(PatientRepository patientRepository, ScheduleExamRepository scheduleExamRepository, ScheduleExamMapper scheduleExamMapper, @Qualifier("employeeRestTemplate") RestTemplate employeeRestTemplate,
                              ScheduledVaccinationMapper scheduledVaccinationMapper,
                              VaccinationRepository vaccinationRepository,
                              ScheduledVaccinationRepository scheduledVaccinationRepository,
                              MedicalRecordRepository medicalRecordRepository,
                              VaccinationDataRepository vaccinationDataRepository) {
        this.patientRepository = patientRepository;
        this.scheduleExamRepository = scheduleExamRepository;
        this.scheduleExamMapper = scheduleExamMapper;
        this.employeeRestTemplate = employeeRestTemplate;
        this.scheduledVaccinationMapper=scheduledVaccinationMapper;
        this.vaccinationRepository=vaccinationRepository;
        this.scheduledVaccinationRepository=scheduledVaccinationRepository;
        this.medicalRecordRepository=medicalRecordRepository;
        this.vaccinationDataRepository=vaccinationDataRepository;
    }

    @Override
    public MessageDto sendVaccinationCertificateToMail(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        GeneralMedicalData generalMedicalData=medicalRecord.getGeneralMedicalData();
        if(generalMedicalData == null)
            return new MessageDto("No general medical data");
        List<Object[]> vaccinationsAndDates=vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData);

        for(int i=0;i<vaccinationsAndDates.size();i++){
            Vaccination v=((Vaccination)vaccinationsAndDates.get(i)[0]);
            if(v.isCovid()){
                VaccinationData vd=new VaccinationData();
                vd.setVaccination(v);
                vd.setVaccinationDate(((Date)vaccinationsAndDates.get(i)[1]));
                String pdf= PDFUtils.makeVaccinationCertificate(patient,vd);
                PDFUtils.sendToMail(pdf,patient.getEmail());
                PDFUtils.removePDF(pdf);
                return new MessageDto("Mail successfully sent");
            }
        }
        return new MessageDto("No covid vaccination found");
    }

    @Override
    public ScheduledVaccinationDto scheduleVaccination(ScheduledVaccinationCreateDto scheduledVaccinationCreateDto) {
        ScheduledVaccination scheduledVaccination= scheduledVaccinationMapper.toEntity(scheduledVaccinationCreateDto,
                vaccinationRepository,patientRepository);
        scheduledVaccination=scheduledVaccinationRepository.save(scheduledVaccination);
        return scheduledVaccinationMapper.toDto(scheduledVaccination);
    }

    @Override
    public ScheduledVaccinationDto updateScheduledVaccination(Long scheduledVaccinationId,
                                                              PatientArrival arrivalStatus) {
        ScheduledVaccination scheduledVaccination= scheduledVaccinationRepository.getById(scheduledVaccinationId);
        scheduledVaccination.setArrivalStatus(arrivalStatus);
        scheduledVaccination=scheduledVaccinationRepository.save(scheduledVaccination);
        return scheduledVaccinationMapper.toDto(scheduledVaccination);
    }

    @Override
    public Page<ScheduledVaccinationDto> getScheduledVaccinationsWithFilter(int page, int size,Date startDate,
                                                                            Date endDate,String lbp,String lbz,
                                                                            Boolean covid,PatientArrival arrivalStatus) {
        Pageable pageable=PageRequest.of(page,size);
        if(endDate!=null)endDate=new Date(endDate.getTime()+1000*60*60*24);
        Page<ScheduledVaccination> scheduledVaccinations=scheduledVaccinationRepository.getScheduledVaccinationsWithFilter(
                pageable,startDate,endDate,lbp,lbz,covid,arrivalStatus);
        return scheduledVaccinations.map(scheduledVaccinationMapper::toDto);
    }

    @Override
    //@CacheEvict(value = "scheduledExams", allEntries = true)
    public MessageDto schedule(ScheduleExamCreateDto scheduleExamCreateDto) {
        ScheduleExam scheduleExam = scheduleExamMapper.toEntity(scheduleExamCreateDto, getLbzFromAuthentication());
        scheduleExam = scheduleExamRepository.save(scheduleExam);
        return new MessageDto("Uspesno kreiran zakazani pregled.");
    }

    @Override
    //@Cacheable(value = "scheduledExams")
    public List<ScheduleExamDto> findScheduledExaminations() {
        List<ScheduleExamDto> exams = scheduleExamRepository.findAll().stream().map(scheduleExamMapper::toDto).collect(Collectors.toList());
        return exams;
    }

    @Transactional
    @Override
    //@CacheEvict(value = "scheduledExams", allEntries = true)
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
    @Transactional(timeout = 20)
    //@CacheEvict(value = "scheduledExams", allEntries = true)
    public MessageDto updatePatientArrivalStatus(Long id, PatientArrival status) {
        Optional<ScheduleExam> exam = scheduleExamRepository.findByIdLock(id);
        if(!exam.isPresent()){
            return new MessageDto("Pregled nije pronadjen.");
        }
        exam.get().setArrivalStatus(status);
        scheduleExamRepository.save(exam.get());
        return new MessageDto(String.format("Status pregleda promenjen u %s", status));
    }

    @Override
    @Transactional(timeout = 20)
    public Page<ScheduleExamDto> findScheduledExaminationsForDoctor(String lbz, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleExam> scheduleExams = scheduleExamRepository.findScheduleForDoctorLock(pageable, lbz, PatientArrival.CEKA);
        return scheduleExams.map(scheduleExamMapper::toDto);
    }

    @Override
    @Transactional(timeout = 20)
    public Page<ScheduleExamDto> findScheduledExaminationsForMedSister(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleExam> scheduleExams = scheduleExamRepository.findScheduleForMedSisterLock(pageable, new Date(System.currentTimeMillis()), PatientArrival.ZAKAZANO);
        return scheduleExams.map(scheduleExamMapper::toDto);
    }

    @Override
    @Transactional(timeout = 20)
    public List<ScheduleExamDto> findScheduledExaminationsForDoctorAll(String lbz) {
        List<ScheduleExam> scheduleExams = scheduleExamRepository.findFromCurrDateAndDoctorLock(new Date(System.currentTimeMillis()), lbz);
        List<ScheduleExamDto> scheduleExamDtoList = new ArrayList<>();
        for(ScheduleExam scheduleExam : scheduleExams){
            ScheduleExamDto scheduleExamDto = scheduleExamMapper.toDto(scheduleExam);
            scheduleExamDtoList.add(scheduleExamDto);
        }
        return scheduleExamDtoList;
    }

    @Override
    public ExamsForPatientDto getExamsForPatient(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(()->new RuntimeException(String.format("Patient with lbp %s not found!", lbp)));
        List<ScheduleExam> exams = scheduleExamRepository.findFromCurrDateForPatient(new Date(System.currentTimeMillis()), lbp);

        List<ExamForPatientDto> examForPatient = new ArrayList<>();
        for(ScheduleExam scheduleExam : exams){
            examForPatient.add(new ExamForPatientDto(lbp, scheduleExam.getDateAndTime(), scheduleExam.getDoctorLbz(), scheduleExam.getNote()));
        }
        return new ExamsForPatientDto(examForPatient);
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
