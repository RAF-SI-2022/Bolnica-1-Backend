package raf.bolnica1.patient.services;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.*;

public interface PatientService {

    ScheduleExamDto shedule(ScheduleExamCreateDto scheduleExamCreateDto);

}
