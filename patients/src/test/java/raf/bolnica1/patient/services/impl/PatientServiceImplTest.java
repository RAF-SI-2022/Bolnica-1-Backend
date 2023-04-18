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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
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
    private ScheduleExamRepository scheduleExamRepository;
    @Mock
    private ScheduleExamMapper scheduleExamMapper;
    @InjectMocks
    private PatientServiceImpl patientService;


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



}