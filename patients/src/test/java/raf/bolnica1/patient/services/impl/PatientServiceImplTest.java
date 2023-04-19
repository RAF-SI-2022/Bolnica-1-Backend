package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.CountryCode;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.ScheduleExamRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ScheduleExamRepository scheduleExamRepository;
    @Mock
    private ScheduleExamMapper scheduleExamMapper;
    @InjectMocks
    private PatientServiceImpl patientService;

/*
    @Nested
    class updatePatientArrivalStatus{

        ScheduleExam exam;

        @BeforeEach
        void setup(){
            exam = new ScheduleExam();
            exam.setId(1l);
            exam.setArrivalStatus(PatientArrival.ZAVRSENO);
        }

        @Test
        void examDoesNotExistTest(){

            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(null);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.CEKA);

            assertEquals("Pregled nije pronadjen.", res.getMessage());

            verify(scheduleExamRepository).getReferenceById(1l);

        }


        @Test
        void updateSuccessfulNotCanceledTest(){


            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(exam);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.CEKA);

            assertEquals("Status pregleda promenjen u CEKA", res.getMessage());

            verify(scheduleExamRepository).getReferenceById(1l);

        }

        @Test
        void updateSuccessfulCanceledTest(){

            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(exam);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.OTKAZANO);

            assertEquals("Status pregleda promenjen u OTKAZANO", res.getMessage());


            verify(scheduleExamRepository).getReferenceById(1l);
        }


    }
*/
    @Nested
    class schedule{

        ScheduleExamCreateDto scheduleExamCreateDto;

        @BeforeEach
        void setup(){
            scheduleExamCreateDto = new ScheduleExamCreateDto();
            scheduleExamCreateDto.setLbp("string");
        }

        @Test
        void scheduleSuccessfulTest(){
            ScheduleExam exam = new ScheduleExam();

            when(scheduleExamMapper.toEntity(scheduleExamCreateDto, null)).thenReturn(exam);

            MessageDto res = patientService.schedule(scheduleExamCreateDto);

            assertEquals("Uspesno kreiran zakazani pregled.", res.getMessage());

            verify(scheduleExamMapper).toEntity(scheduleExamCreateDto, null);
            verify(scheduleExamRepository).save(exam);

        }

    }

    @Nested
    class DeleteScheduledExaminationTests {

        @BeforeEach
        void setup() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testDeleteScheduledExamination() {
            Long id = 1L;
            ScheduleExam exam = new ScheduleExam();
            exam.setId(id);
            when(scheduleExamRepository.deleteScheduleExamById(id)).thenReturn(Optional.of(exam));
            MessageDto result = patientService.deleteScheduledExamination(id);
            verify(scheduleExamRepository).deleteScheduleExamById(id);
            assertEquals(String.format("Scheduled exam with id %d deleted", id), result.getMessage());
        }

        @Test
        void testDeleteScheduledExaminationNotFound() {
            Long id = 1L;
            when(scheduleExamRepository.deleteScheduleExamById(id)).thenReturn(Optional.empty());
            assertThrows(RuntimeException.class, () -> patientService.deleteScheduledExamination(id));
            verify(scheduleExamRepository).deleteScheduleExamById(id);
        }

    }

    @Test
    void testFindScheduledExaminationsForDoctor() {
        String lbz = "LBZ001";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        ScheduleExam scheduleExam = new ScheduleExam();
        ScheduleExam scheduleExam2 = new ScheduleExam();
        List<ScheduleExam> scheduleExams = Arrays.asList(
                scheduleExam2, scheduleExam
        );
        PageImpl<ScheduleExam> scheduleExamsPage = new PageImpl<>(scheduleExams, pageable, scheduleExams.size());

        when(scheduleExamRepository.findScheduleForDoctor(pageable, lbz, PatientArrival.CEKA))
                .thenReturn(scheduleExamsPage);

        List<ScheduleExamDto> expectedDtos = scheduleExams.stream()
                .map(scheduleExamMapper::toDto)
                .collect(Collectors.toList());

        PageImpl<ScheduleExamDto> expectedPage = new PageImpl<>(expectedDtos, pageable, expectedDtos.size());
        Page<ScheduleExamDto> resultPage = patientService.findScheduledExaminationsForDoctor(lbz, page, size);

        verify(scheduleExamRepository).findScheduleForDoctor(pageable, lbz, PatientArrival.CEKA);
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void testFindScheduledExaminationsForMedSister() {
        List<ScheduleExam> scheduleExamList = new ArrayList<>();
        ScheduleExam scheduleExam1 = new ScheduleExam();
        ScheduleExam scheduleExam2 = new ScheduleExam();
        scheduleExamList.add(scheduleExam1);
        scheduleExamList.add(scheduleExam2);
        Page<ScheduleExam> page = new PageImpl<>(scheduleExamList);

        when(scheduleExamRepository.findScheduleForMedSister(
                any(Pageable.class),
                any(Date.class),
                any(PatientArrival.class)))
                .thenReturn(page);

        List<ScheduleExamDto> scheduleExamDtoList = new ArrayList<>();
        ScheduleExamDto scheduleExamDto1 = new ScheduleExamDto();
        ScheduleExamDto scheduleExamDto2 = new ScheduleExamDto();
        scheduleExamDtoList.add(scheduleExamDto1);
        scheduleExamDtoList.add(scheduleExamDto2);

        when(scheduleExamMapper.toDto(scheduleExam1)).thenReturn(scheduleExamDto1);
        when(scheduleExamMapper.toDto(scheduleExam2)).thenReturn(scheduleExamDto2);

        Page<ScheduleExamDto> result = patientService.findScheduledExaminationsForMedSister(0, 10);
        assertEquals(scheduleExamDtoList, result.getContent());
    }

    //Testovi za findScheduledExaminationsForDoctorAll
    @Test
    public void testFindScheduledExaminationsForDoctorAll() {
        String lbz = "sampleLbz";
        List<ScheduleExam> scheduleExams = new ArrayList<>();
        List<ScheduleExamDto> scheduleExamDtoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ScheduleExam scheduleExam = new ScheduleExam();
            scheduleExams.add(scheduleExam);

            ScheduleExamDto scheduleExamDto = new ScheduleExamDto();
            scheduleExamDtoList.add(scheduleExamDto);

            when(scheduleExamMapper.toDto(scheduleExam)).thenReturn(scheduleExamDto);
        }

        doReturn(scheduleExams).when(scheduleExamRepository).findFromCurrDateAndDoctor(Mockito.any(Date.class), Mockito.anyString());

        List<ScheduleExamDto> result = patientService.findScheduledExaminationsForDoctorAll(lbz);

        assertEquals(scheduleExamDtoList, result);
        verify(scheduleExamRepository, times(1)).findFromCurrDateAndDoctor(Mockito.any(Date.class), eq(lbz));
        verify(scheduleExamMapper, times(3)).toDto(any(ScheduleExam.class));
    }
    //Testovi za updatePatientArrivalStatus
    @Test
    public void testUpdatePatientArrivalStatus() {
        ScheduleExam exam = new ScheduleExam();
        exam.setArrivalStatus(PatientArrival.ZAKAZANO);

        Mockito.when(scheduleExamRepository.findById(1L)).thenReturn(Optional.of(exam));

        MessageDto message = patientService.updatePatientArrivalStatus(1L, PatientArrival.ZAKAZANO);

        verify(scheduleExamRepository).save(exam);
        assertEquals("Status pregleda promenjen u ZAKAZANO", message.getMessage());
    }
    @Test
    public void testUpdatePatientArrivalStatusNotFound() {
       when(scheduleExamRepository.findById(1L)).thenReturn(Optional.empty());

        MessageDto message = patientService.updatePatientArrivalStatus(1L, PatientArrival.ZAKAZANO);

        assertEquals("Pregled nije pronadjen.", message.getMessage());

        Mockito.verify(scheduleExamRepository, Mockito.never()).save(Mockito.any(ScheduleExam.class));
    }

    //Testovi za findDoctorSpecByDepartment
    @Test
    public void testFindDoctorSpecByDepartment() {
        String pbo = "123";
        String token = "Bearer 1234567890";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth("1234567890");
        HttpEntity entity = new HttpEntity(null, httpHeaders);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        List<EmployeeDto> employees = Arrays.asList(employeeDto);
        ResponseEntity<List<EmployeeDto>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
        ParameterizedTypeReference<List<EmployeeDto>> responseType = new ParameterizedTypeReference<List<EmployeeDto>>() {};
        when(restTemplate.exchange(eq("/find_doctor_specialists_by_department/" + pbo), eq(HttpMethod.GET), eq(entity), eq(responseType))).thenReturn(responseEntity);

        List<EmployeeDto> result = patientService.findDoctorSpecByDepartment(pbo, token);

        assertEquals(employees, result);
        verify(restTemplate).exchange(eq("/find_doctor_specialists_by_department/" + pbo), eq(HttpMethod.GET), eq(entity), eq(responseType));
    }
    //Testovi za findScheduledExaminations
    @Test
    public void testFindScheduledExaminations() {
        List<ScheduleExam> scheduleExams = createScheduleExams();
        List<ScheduleExamDto> scheduleExamDtos = createScheduleExamDtos();

        when(scheduleExamRepository.findAll()).thenReturn(scheduleExams);
        when(scheduleExamMapper.toDto(any(ScheduleExam.class))).thenReturn(new ScheduleExamDto());
        when(scheduleExamMapper.toDto(scheduleExams.get(0))).thenReturn(scheduleExamDtos.get(0));
        when(scheduleExamMapper.toDto(scheduleExams.get(1))).thenReturn(scheduleExamDtos.get(1));
        when(scheduleExamMapper.toDto(scheduleExams.get(2))).thenReturn(scheduleExamDtos.get(2));

        List<ScheduleExamDto> result = patientService.findScheduledExaminations();

        assertFalse(result.isEmpty());
        assertEquals(result.size(), scheduleExams.size());

        verify(scheduleExamRepository, times(1)).findAll();
        verify(scheduleExamMapper, times(3)).toDto(any(ScheduleExam.class));
    }

    @Test
    public void testFindScheduledExaminationsThrowsException() {
        when(scheduleExamRepository.findAll()).thenThrow(new RuntimeException());
        List<ScheduleExamDto> result = new ArrayList<>();
        try {
            result = patientService.findScheduledExaminations();
            fail("Expected exception not thrown");
        } catch (RuntimeException e) {
            verify(scheduleExamRepository, times(1)).findAll();
            assertTrue(result.isEmpty());
        }

    }

    //Pomocne klase
    private Patient createPatietnt(){
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@email.com");
        patient.setCitizenship(CountryCode.SRB);
        patient.setJmbg("123456");
        patient.setBirthPlace("Beograd");
        patient.setGender(Gender.MUSKO);
        patient.setLbp("123456");
        patient.setName("Ime");
        patient.setSurname("Prezimovic");
        patient.setPhone("06385113547");
        patient.setDateOfBirth(Date.valueOf("2011-11-11"));
        patient.setPlaceOfLiving("Beograd");
        patient.setGuardianJmbg("10987654321");
        patient.setGuardianNameAndSurname("Marko Markovic");
        patient.setDeleted(false);

        return patient;
    }

    private List<ScheduleExam> createScheduleExams(){
        List<ScheduleExam> scheduleExams = new ArrayList<>();
        ScheduleExam scheduleExam1 = new ScheduleExam();
        scheduleExam1.setId(1L);
        scheduleExam1.setPatient(createPatietnt());
        scheduleExam1.setArrivalStatus(PatientArrival.ZAKAZANO);
        scheduleExam1.setNote("Note1");
        scheduleExam1.setLbz("123456");

        ScheduleExam scheduleExam2 = new ScheduleExam();
        scheduleExam2.setId(2L);
        scheduleExam2.setPatient(createPatietnt());
        scheduleExam2.setArrivalStatus(PatientArrival.ZAKAZANO);
        scheduleExam2.setNote("Note2");
        scheduleExam2.setLbz("123456");

        ScheduleExam scheduleExam3 = new ScheduleExam();
        scheduleExam3.setId(1L);
        scheduleExam3.setPatient(createPatietnt());
        scheduleExam3.setArrivalStatus(PatientArrival.ZAKAZANO);
        scheduleExam3.setNote("Note3");
        scheduleExam3.setLbz("123456");

        scheduleExams.add(scheduleExam1);
        scheduleExams.add(scheduleExam2);
        scheduleExams.add(scheduleExam3);

        return scheduleExams;

    }

    private List<ScheduleExamDto> createScheduleExamDtos(){
        List<ScheduleExamDto> scheduleExams = new ArrayList<>();
        ScheduleExamDto scheduleExam1 = new ScheduleExamDto();
        scheduleExam1.setId(1L);
        scheduleExam1.setLbz("123456");
        scheduleExam1.setPatientArrival(PatientArrival.ZAKAZANO);

        ScheduleExamDto scheduleExam2 = new ScheduleExamDto();
        scheduleExam2.setId(2L);
        scheduleExam2.setPatientArrival(PatientArrival.ZAKAZANO);
        scheduleExam2.setLbz("123456");

        ScheduleExamDto scheduleExam3 = new ScheduleExamDto();
        scheduleExam3.setId(1L);
        scheduleExam3.setPatientArrival(PatientArrival.ZAKAZANO);
        scheduleExam3.setLbz("123456");

        scheduleExams.add(scheduleExam1);
        scheduleExams.add(scheduleExam2);
        scheduleExams.add(scheduleExam3);

        return scheduleExams;

    }

}