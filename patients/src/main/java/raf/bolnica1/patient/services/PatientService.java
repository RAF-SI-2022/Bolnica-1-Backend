package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.domain.ScheduledVaccination;
import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.create.ScheduledVaccinationCreateDto;
import raf.bolnica1.patient.dto.employee.EmployeeDto;
import raf.bolnica1.patient.dto.general.ExamsForPatientDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;
import raf.bolnica1.patient.dto.general.ScheduledVaccinationDto;

import java.sql.Date;
import java.util.List;

public interface PatientService {

    MessageDto sendVaccinationCertificateToMail(String lbp);

    ScheduledVaccinationDto scheduleVaccination(ScheduledVaccinationCreateDto scheduledVaccinationCreateDto);
    ScheduledVaccinationDto updateScheduledVaccination(Long scheduledVaccinationId,
                                                       PatientArrival arrivalStatus);
    Page<ScheduledVaccinationDto> getScheduledVaccinationsWithFilter(int page, int size, Date startDate,
                                                                     Date endDate, String lbp, String lbz,
                                                                     Boolean covid, PatientArrival arrivalStatus);


    MessageDto schedule(ScheduleExamCreateDto scheduleExamCreateDto);

    //Pretraga zakazanih pregleda
    List<ScheduleExamDto> findScheduledExaminations();
    MessageDto deleteScheduledExamination(Long id);

    // Pretraga lekara specijalista na odeljenju
    List<EmployeeDto> findDoctorSpecByDepartment(String pbo, String token);
    // Azuriranje statusa o prispecu pacijenta
    MessageDto updatePatientArrivalStatus(Long id, PatientArrival object);

    Page<ScheduleExamDto> findScheduledExaminationsForDoctor(String lbz, int page, int size);

    Page<ScheduleExamDto> findScheduledExaminationsForMedSister(int page, int size);

    List<ScheduleExamDto> findScheduledExaminationsForDoctorAll(String lbz);

    ExamsForPatientDto getExamsForPatient(String lbp);
}
