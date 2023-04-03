package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;

import java.util.List;

public interface PatientService {

    MessageDto schedule(ScheduleExamCreateDto scheduleExamCreateDto);

    //Pretraga zakazanih pregleda
    List<ScheduleExamDto> findScheduledExamination(Object object);

    // Azuriranje statusa pregleda
    MessageDto updateExaminationStatus(Object object);

    MessageDto deleteScheduledExamination(Long id);

    // Pretraga lekara specijalista na odeljenju
    List<EmployeeDto> findDoctorSpecByDepartment(String pbo, String token);
    // Azuriranje statusa o prispecu pacijenta
    MessageDto updatePatientArrivalStatus(Long id, PatientArrival object);

    Page<ScheduleExamDto> findScheduledExaminationsForDoctor(String lbz, int page, int size);

    Page<ScheduleExamDto> findScheduledExaminationsForMedSister(int page, int size);
}
