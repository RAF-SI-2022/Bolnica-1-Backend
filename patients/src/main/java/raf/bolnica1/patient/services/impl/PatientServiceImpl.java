package raf.bolnica1.patient.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.mapper.ScheduleExamMapper;
import raf.bolnica1.patient.repository.ScheduleExamRepository;
import raf.bolnica1.patient.services.PatientService;


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


}
