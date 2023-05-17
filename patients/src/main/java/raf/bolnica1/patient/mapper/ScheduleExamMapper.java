package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.ScheduleExam;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.repository.PatientRepository;

@Component
@AllArgsConstructor
public class ScheduleExamMapper {

    private PatientRepository patientRepository;

    public ScheduleExam toEntity(ScheduleExamCreateDto scheduleExamCreateDto, String lbz){
        Patient patient = patientRepository.findByLbp(scheduleExamCreateDto.getLbp()).orElseThrow(()->new RuntimeException(String.format("Patient with lbp %s not found", scheduleExamCreateDto.getLbp())));

        ScheduleExam scheduleExam = new ScheduleExam();
        scheduleExam.setArrivalStatus(PatientArrival.ZAKAZANO);
        scheduleExam.setDateAndTime(scheduleExamCreateDto.getDateAndTime());
        scheduleExam.setDoctorLbz(scheduleExamCreateDto.getDoctorLbz());
        scheduleExam.setLbz(lbz);
        scheduleExam.setPatient(patient);
        scheduleExam.setNote(scheduleExamCreateDto.getNote());

        return scheduleExam;
    }

    public ScheduleExamDto toDto(ScheduleExam scheduleExam){
        ScheduleExamDto scheduleExamDto = new ScheduleExamDto();
        scheduleExamDto.setId(scheduleExam.getId());
        scheduleExamDto.setDoctorLbz(scheduleExam.getDoctorLbz());
        scheduleExamDto.setLbp(scheduleExam.getPatient().getLbp());
        scheduleExamDto.setPatientArrival(scheduleExam.getArrivalStatus());
        scheduleExamDto.setDateAndTime(scheduleExam.getDateAndTime());
        scheduleExamDto.setLbz(scheduleExam.getLbz());

        return scheduleExamDto;
    }
}
