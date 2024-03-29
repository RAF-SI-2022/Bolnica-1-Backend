package raf.bolnica1.laboratory.mappers;

import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.ScheduledLabExamination;
import raf.bolnica1.laboratory.dto.lab.scheduledLabExamination.ScheduledLabExaminationDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledLabExaminationMapper {

    public ScheduledLabExaminationDto toDto(ScheduledLabExamination entity){
        if(entity==null)return null;

        ScheduledLabExaminationDto dto=new ScheduledLabExaminationDto();

        dto.setId(entity.getId());
        dto.setNote(entity.getNote());
        dto.setLbz(entity.getLbz());
        dto.setLbp(entity.getLbp());
        dto.setScheduledDate(entity.getScheduledDate());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setExaminationStatus(entity.getExaminationStatus());

        return dto;
    }

    public List<ScheduledLabExaminationDto> toDto(List<ScheduledLabExamination> entity){

        if(entity==null)return null;

        List<ScheduledLabExaminationDto> dto=new ArrayList<>();

        for(ScheduledLabExamination scheduledLabExamination:entity)
            dto.add(toDto(scheduledLabExamination));

        return dto;
    }

    public ScheduledLabExamination toEntity(Long departmentId, String lbp, Date scheduledDate, String note, String lbz){

        ScheduledLabExamination entity=new ScheduledLabExamination();

        entity.setScheduledDate(scheduledDate);
        entity.setLbz(lbz);
        entity.setLbp(lbp);
        entity.setNote(note);
        entity.setDepartmentId(departmentId);

        return entity;
    }

}
