package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentDto;
import raf.bolnica1.infirmary.repository.PrescriptionRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledAppointmentMapper {

    private final AuthenticationUtils authenticationUtils;


    public static ScheduledAppointmentMapper getInstance(){
        return new ScheduledAppointmentMapper(AuthenticationUtils.getInstance());
    }


    public ScheduledAppointmentDto toDto(ScheduledAppointment entity){
        if(entity==null)return null;

        ScheduledAppointmentDto dto=new ScheduledAppointmentDto();

        dto.setId(entity.getId());
        dto.setLbp(entity.getPrescription().getLbp());
        dto.setNote(entity.getNote());
        dto.setLbzScheduler(entity.getLbzScheduler());
        dto.setAdmissionStatus(entity.getAdmissionStatus());
        dto.setPrescriptionId(entity.getPrescription().getId());
        dto.setPatientAdmission(entity.getPatientAdmission());

        return dto;
    }

    public List<ScheduledAppointmentDto> toDto(List<ScheduledAppointment> entity){
        if(entity==null)return null;

        List<ScheduledAppointmentDto> dto=new ArrayList<>();

        for(ScheduledAppointment s:entity)
            dto.add(toDto(s));

        return dto;
    }


    /// samo za kreiranje ScheduledAppointment
    public ScheduledAppointment toEntity(ScheduledAppointmentCreateDto scheduledAppointmentCreateDto, PrescriptionRepository prescriptionRepository){

        ScheduledAppointment scheduledAppointment=new ScheduledAppointment();

        scheduledAppointment.setLbzScheduler(authenticationUtils.getLbzFromAuthentication());
        scheduledAppointment.setAdmissionStatus(AdmissionStatus.ZAKAZAN);
        scheduledAppointment.setNote(scheduledAppointmentCreateDto.getNote());
        scheduledAppointment.setPatientAdmission(scheduledAppointmentCreateDto.getPatientAdmission());
        scheduledAppointment.setPrescription(prescriptionRepository.findPrescriptionById(scheduledAppointmentCreateDto.getPrescriptionId()));

        return scheduledAppointment;
    }

}
