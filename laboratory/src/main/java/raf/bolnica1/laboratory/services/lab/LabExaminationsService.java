package raf.bolnica1.laboratory.services.lab;

import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

import java.sql.Date;
import java.util.List;

public interface LabExaminationsService {

    ScheduledLabExaminationDto createScheduledExamination(String lbp, Date scheduledDate, String note,String token);

    ScheduledLabExaminationDto changeExaminationStatus(Long id, ExaminationStatus newStatus);

    List<ScheduledLabExaminationDto> listScheduledExaminationsByDay(Date date,String token);

    List<ScheduledLabExaminationDto> listScheduledExaminations(String token);

    Page<ScheduledLabExaminationDto> listScheduledExaminationsByLbpAndDate(String lbp, Date startDate, Date endDate, String token,
                                                                           Integer page,Integer size);
}
