package raf.bolnica1.patient.services.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;

import raf.bolnica1.patient.domain.*;

import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.PatientService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
