package raf.bolnica1.infirmary.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.dto.ScheduleAppointmentDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledAppointmentMapper {

    public ScheduleAppointmentDto toDto(ScheduledAppointment entity){
        if(entity == null)
            return null;

        ScheduleAppointmentDto dto = new ScheduleAppointmentDto();
        dto.setNote(entity.getNote());
        dto.setLbp(entity.getPrescription().getLbp());
        dto.setAppointmentDateAndTime(entity.getPatientAdmission());

        return dto;
    }

    public List<ScheduleAppointmentDto> allToDto (List<ScheduledAppointment> entities){
        if(entities == null)
            return null;

        List<ScheduleAppointmentDto> dtos = new ArrayList<>();
        for(ScheduledAppointment entity: entities)
            dtos.add(toDto(entity));

        return dtos;
    }

}
