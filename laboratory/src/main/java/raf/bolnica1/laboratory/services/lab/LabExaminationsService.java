package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

import java.sql.Date;

public interface LabExaminationsService {

    MessageDto createScheduledExamination(String lbp, Date scheduledDate, String note,String token);

    Object changeExaminationStatus(Object object);

    Object listScheduledExaminationsByDay(Object object);

    Object listScheduledExaminations(Object object);
}
