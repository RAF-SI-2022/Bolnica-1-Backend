package raf.bolnica1.patient.services.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.ExaminationStatus;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.ScheduleExamRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @MockBean
    private ScheduleExamRepository scheduleExamRepository;
    @MockBean
    private ScheduleExamMapper scheduleExamMapper;

    @Autowired
    private PatientServiceImpl patientService;


    @Nested
    class updatePatientArrivalStatus{

        ScheduleExam exam;

        @BeforeEach
        void setup(){
            exam = new ScheduleExam();
            exam.setId(1l);
            exam.setArrivalStatus(PatientArrival.SCHEDULE_IN_PROGRESS);
            exam.setExaminationStatus(ExaminationStatus.SCHEDULED);
        }

        @Test
        void examDoesnNotExistTest(){

            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(null);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.WAITING);

            assertEquals("Examination does not exist", res.getMessage());

            verify(scheduleExamRepository).getReferenceById(1l);

        }


        @Test
        void updateSuccessfulNotCanceledTest(){


            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(exam);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.WAITING);

            assertEquals("Arrival status updated into WAITING", res.getMessage());

            verify(scheduleExamRepository).getReferenceById(1l);

        }

        @Test
        void updateSuccessfulCanceledTest(){

            when(scheduleExamRepository.getReferenceById(1l)).thenReturn(exam);

            MessageDto res = patientService.updatePatientArrivalStatus(1l, PatientArrival.CANCELED);

            assertEquals(ExaminationStatus.CANCELED, exam.getExaminationStatus());
            assertEquals("Arrival status updated into CANCELED", res.getMessage());


            verify(scheduleExamRepository).getReferenceById(1l);
        }


    }

    @Nested
    class schedule{

        ScheduleExamCreateDto scheduleExamCreateDto;

        @BeforeEach
        void setup(){
            scheduleExamCreateDto = new ScheduleExamCreateDto();
            scheduleExamCreateDto.setLbz(1l);
            scheduleExamCreateDto.setLbp("string");
        }

        @Test
        void scheduleSuccessfulTest(){
            ScheduleExam exam = new ScheduleExam();

            when(scheduleExamMapper.toEntity(scheduleExamCreateDto)).thenReturn(exam);

            MessageDto res = patientService.schedule(scheduleExamCreateDto);

            assertEquals("Scheduled examination created", res.getMessage());

            verify(scheduleExamMapper).toEntity(scheduleExamCreateDto);
            verify(scheduleExamRepository).save(exam);

        }

    }

}