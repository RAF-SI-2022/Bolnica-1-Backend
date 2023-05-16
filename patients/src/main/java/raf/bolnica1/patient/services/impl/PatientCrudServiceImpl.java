package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.GeneralMedicalData;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.SocialData;
import raf.bolnica1.patient.dto.create.PatientCreateDto;
import raf.bolnica1.patient.dto.create.PatientUpdateDto;
import raf.bolnica1.patient.dto.general.MessageDto;
import raf.bolnica1.patient.dto.general.PatientDto;
import raf.bolnica1.patient.mapper.MedicalRecordMapper;
import raf.bolnica1.patient.mapper.PatientMapper;
import raf.bolnica1.patient.repository.GeneralMedicalDataRepository;
import raf.bolnica1.patient.repository.MedicalRecordRepository;
import raf.bolnica1.patient.repository.PatientRepository;
import raf.bolnica1.patient.repository.SocialDataRepository;
import raf.bolnica1.patient.services.PatientCrudService;

@Service
@Transactional
@AllArgsConstructor
public class PatientCrudServiceImpl implements PatientCrudService {

    private PatientMapper patientMapper;
    private MedicalRecordMapper medicalRecordMapper;
    private PatientRepository patientRepository;
    private SocialDataRepository socialDataRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;

    @Override
    @Cacheable(value = "patient", key = "#patientCreateDto.lbp")
    public PatientDto registerPatient(PatientCreateDto patientCreateDto) {
        Patient patient = patientMapper.patientDtoToPatientGeneralData(patientCreateDto);
        SocialData socialData = socialDataRepository.save(patientMapper.patientDtoToPatientSocialData(patientCreateDto));
        patient.setSocialData(socialData);
        patient = patientRepository.save(patient);

        /// add medical record
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patient);
        medicalRecord.setRegistrationDate(patientCreateDto.getRegisterDate());

        GeneralMedicalData generalMedicalData = new GeneralMedicalData();
        generalMedicalData = generalMedicalDataRepository.save(generalMedicalData);

        medicalRecord.setGeneralMedicalData(generalMedicalData);
        medicalRecordRepository.save(medicalRecord);

        return patientMapper.patientToPatientDto(patient);
    }

    @Override
    @CachePut(value = "patient", key = "#dto.lbp")
    public PatientDto updatePatient(PatientUpdateDto dto) {
        Patient patient = patientRepository.findByLbp(dto.getLbp()).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", dto.getLbp())));
        patient.setDeleted(dto.isDeleted());
        patient = patientRepository.save(patientMapper.setPatientGeneralData(dto, patient));
        return patientMapper.patientToPatientDto(patient);
    }

    @Override
    @CacheEvict(value = "patient", key = "#lbp")
    public MessageDto deletePatient(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        patient.setDeleted(true);

        /// delete medical record
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));
        medicalRecord.setDeleted(true);
        return new MessageDto(String.format("Patient with lbp %s deleted.", lbp));
    }

    @Override
    public Page<PatientDto> filterPatients(String lbp, String jmbg, String name, String surname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return patientRepository
                .listPatientsWithFilters(pageable, name, surname, jmbg, lbp)
                .map(patientMapper::patientToPatientDto);
    }

    @Override
    @Cacheable(value = "patient", key = "#lbp")
    public PatientDto findPatient(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        return patientMapper.patientToPatientDto(patient);
    }




}
