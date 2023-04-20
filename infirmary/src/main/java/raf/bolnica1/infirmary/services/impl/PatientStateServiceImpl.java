package raf.bolnica1.infirmary.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;
import raf.bolnica1.infirmary.mapper.PatientStateMapper;
import raf.bolnica1.infirmary.repository.HospitalizationRepository;
import raf.bolnica1.infirmary.repository.PatientStateRepository;
import raf.bolnica1.infirmary.services.PatientStateService;

import java.sql.Date;

@Service
@AllArgsConstructor
public class PatientStateServiceImpl implements PatientStateService {

    /// MAPPERS
    private final PatientStateMapper patientStateMapper;

    /// REPOSITORIES
    private final PatientStateRepository patientStateRepository;
    private final HospitalizationRepository hospitalizationRepository;


    @Override
    public PatientStateDto createPatientState(PatientStateCreateDto patientStateCreateDto) {
        PatientState patientState=patientStateMapper.toEntity(patientStateCreateDto,hospitalizationRepository);
        patientState=patientStateRepository.save(patientState);
        return patientStateMapper.toDto(patientState);
    }

    @Override
    public Page<PatientStateDto> getPatientStateByDate(Long hospitalizationId, Date startDate, Date endDate,Integer page,Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<PatientState>patientStates=patientStateRepository.findPatientStatesByDate(pageable,hospitalizationId,startDate,endDate);
        return patientStates.map(patientStateMapper::toDto);
    }
}
