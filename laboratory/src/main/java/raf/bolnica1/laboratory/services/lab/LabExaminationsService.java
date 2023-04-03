package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

import java.sql.Date;
import java.util.List;

public interface LabExaminationsService {

    MessageDto createScheduledExamination(String lbp, Date scheduledDate, String note,String token);

    Object changeExaminationStatus(Object object);

    List<ScheduledLabExaminationDto> listScheduledExaminationsByDay(Date date,String token);

    List<ScheduledLabExaminationDto> listScheduledExaminations(String token);
}
