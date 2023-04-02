package raf.bolnica1.patient.services;

import raf.bolnica1.patient.domain.constants.PatientArrival;
import raf.bolnica1.patient.dto.create.ScheduleExamCreateDto;
import raf.bolnica1.patient.dto.general.ScheduleExamDto;

public interface PatientService {

    ScheduleExamDto shedule(ScheduleExamCreateDto scheduleExamCreateDto);

    // Azuriranje statusa o prispecu pacijenta
    Object updatePatientArrivalStatus(Long id, PatientArrival object);

}
