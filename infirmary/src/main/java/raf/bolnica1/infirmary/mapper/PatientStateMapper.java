package raf.bolnica1.infirmary.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.security.util.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PatientStateMapper {

    private final AuthenticationUtils authenticationUtils;


    public PatientStateDto toDto(PatientState entity){
        if(entity==null)return null;

        PatientStateDto dto=new PatientStateDto();

        dto.setId(entity.getId());
        dto.setLbz(entity.getLbz());
        dto.setDescription(entity.getDescription());
        dto.setPulse(entity.getPulse());
        dto.setTherapy(entity.getTherapy());
        dto.setDateExamState(entity.getDateExamState());
        dto.setTemperature(entity.getTemperature());
        dto.setDiastolicPressure(entity.getDiastolicPressure());
        dto.setSystolicPressure(entity.getSystolicPressure());
        dto.setHospitalizationId(entity.getHospitalization().getId());
        dto.setTimeExamState(entity.getTimeExamState());

        return dto;
    }

    public List<PatientStateDto> toDto(List<PatientState> entity){
        if(entity==null)return null;

        List<PatientStateDto> dto=new ArrayList<>();

        for(PatientState patientState:entity)
            dto.add(toDto(patientState));

        return dto;
    }


    /// kretiranje
    public PatientState toEntity(PatientStateCreateDto dto, HospitalizationRepository hospitalizationRepository){

        if(dto==null)return null;

        PatientState entity=new PatientState();

        entity.setLbz(authenticationUtils.getLbzFromAuthentication());
        entity.setDescription(dto.getDescription());
        entity.setPulse(dto.getPulse());
        entity.setTherapy(dto.getTherapy());
        entity.setDateExamState(dto.getDateExamState());
        entity.setTemperature(dto.getTemperature());
        entity.setDiastolicPressure(dto.getDiastolicPressure());
        entity.setSystolicPressure(dto.getSystolicPressure());
        entity.setHospitalization(hospitalizationRepository.findHospitalizationById(dto.getHospitalizationId()) );
        entity.setTimeExamState(dto.getTimeExamState());

        return entity;
    }

}
