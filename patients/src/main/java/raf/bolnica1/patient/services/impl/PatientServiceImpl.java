package raf.bolnica1.patient.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.ExaminationStatus;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.ScheduleExamRepository;
import raf.bolnica1.patient.services.PatientService;

import java.util.Optional;


@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private ScheduleExamRepository scheduleExamRepository;
    private ScheduleExamMapper scheduleExamMapper;


    @Override
    public ScheduleExamDto shedule(ScheduleExamCreateDto scheduleExamCreateDto) {
        ScheduleExam scheduleExam = scheduleExamMapper.toEntity(scheduleExamCreateDto);
        scheduleExam = scheduleExamRepository.save(scheduleExam);
        return scheduleExamMapper.toDto(scheduleExam);
    }

    @Override
    public Object updatePatientArrivalStatus(Long id, PatientArrival status) {
        ScheduleExam exam = scheduleExamRepository.getReferenceById(id);
        System.out.println(exam.getId());
        if(exam == null){
            System.out.println("u ifu");
            return new MessageDto(String.format("Examination does not exist"));
        }

        System.out.println("posle ifa");
        exam.setArrivalStatus(status);
        if(status == PatientArrival.CANCELED){
            exam.setExaminationStatus(ExaminationStatus.CANCELED);
        }
        return new MessageDto(String.format("Arrival status updated into %s", status));
    }


}
