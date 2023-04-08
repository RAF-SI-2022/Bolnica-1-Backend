package raf.bolnica1.infirmary.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;

import java.sql.Date;

public interface PatientStateService {

    PatientStateDto createPatientState(PatientStateCreateDto patientStateCreateDto);

    Page<PatientStateDto> getPatientStateByDate(Long hospitalizationId, Date startDate, Date endDate, Integer page, Integer size);

}
