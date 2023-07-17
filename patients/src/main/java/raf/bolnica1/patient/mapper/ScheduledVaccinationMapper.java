package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.ScheduledVaccination;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduledVaccinationCreateDto;
import raf.bolnica1.patient.dto.general.ScheduledVaccinationDto;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.VaccinationRepository;

@Component
@AllArgsConstructor
public class ScheduledVaccinationMapper {

    private VaccinationMapper vaccinationMapper;
    private PatientMapper patientMapper;

    public ScheduledVaccination toEntity(ScheduledVaccinationCreateDto dto, VaccinationRepository vaccinationRepository,
                                         PatientRepository patientRepository){
        if(dto==null)return null;

        ScheduledVaccination entity=new ScheduledVaccination();

        entity.setVaccination(vaccinationRepository.findByName(dto.getVaccineName()));
        entity.setNote(dto.getNote());
        entity.setLbz(dto.getLbz());
        entity.setPatient(patientRepository.findByLbp(dto.getLbp()).get());
        entity.setArrivalStatus(PatientArrival.ZAKAZANO);
        entity.setDateAndTime(dto.getDateAndTime());

        return entity;
    }

    public ScheduledVaccination toEntity(ScheduledVaccinationDto dto, VaccinationRepository vaccinationRepository,
                                         PatientRepository patientRepository){
        if(dto==null)return null;

        ScheduledVaccination entity=new ScheduledVaccination();

        entity.setVaccination(vaccinationRepository.findByName(dto.getVaccination().getName()));
        entity.setNote(dto.getNote());
        entity.setLbz(dto.getLbz());
        entity.setPatient(patientRepository.findByLbp(dto.getPatient().getLbp()).get());
        entity.setArrivalStatus(PatientArrival.ZAKAZANO);
        entity.setDateAndTime(dto.getDateAndTime());
        entity.setId(dto.getId());

        return entity;
    }

    public ScheduledVaccinationDto toDto(ScheduledVaccination entity){
        if(entity==null)return null;

        ScheduledVaccinationDto dto=new ScheduledVaccinationDto();

        dto.setId(entity.getId());
        dto.setVaccination(vaccinationMapper.toDto(entity.getVaccination()));
        dto.setNote(entity.getNote());
        dto.setLbz(entity.getLbz());
        dto.setPatient(patientMapper.patientToPatientDto(entity.getPatient()));
        dto.setDateAndTime(entity.getDateAndTime());
        dto.setArrivalStatus(entity.getArrivalStatus());

        return dto;

    }

}
