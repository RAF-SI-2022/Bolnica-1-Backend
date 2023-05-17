package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.FindInfoService;

import java.sql.Date;
import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class FindInfoServiceImpl implements FindInfoService {

    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private VaccinationDataRepository vaccinationDataRepository;
    private AllergyDataRepository allergyDataRepository;
    private OperationRepository operationRepository;
    private MedicalHistoryRepository medicalHistoryRepository;
    private ExaminationHistoryRepository examinationHistoryRepository;
    private PatientMapper patientMapper;
    private MedicalRecordMapper medicalRecordMapper;
    private GeneralMedicalDataMapper generalMedicalDataMapper;
    private OperationMapper operationMapper;
    private MedicalHistoryMapper medicalHistoryMapper;
    private ExaminationHistoryMapper examinationHistoryMapper;

    //Dohvatanje GeneralMedicalData po LBP(GMD,vaccines,allergies)
    @Cacheable(value = "gmd", key = "#lbp")
    public GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        GeneralMedicalData generalMedicalData=medicalRecord.getGeneralMedicalData();
        if(generalMedicalData == null)
            return null;

        List<Object[]> vaccinationsAndDates=vaccinationDataRepository.findVaccinationsByGeneralMedicalData(generalMedicalData);
        List<Allergy> allergies=allergyDataRepository.findAllergiesByGeneralMedicalData(generalMedicalData);

        GeneralMedicalDataDto dto=generalMedicalDataMapper.toDto(generalMedicalData,vaccinationsAndDates,allergies);

        return dto;
    }

    ///Dohvatanje liste operacije koje odgovaraju LBP
    @Cacheable(value = "ops", key = "#lbp")
    public List<OperationDto> findOperationsByLbp(String lbp) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        List<Operation> operations=operationRepository.findOperationsByMedicalRecord(medicalRecord);

        return operationMapper.toDto(operations);
    }

    ///Dohvatanje liste MedicalHistory po LBP
    @Cacheable(value = "medHistory", key = "#lbp")
    public List<MedicalHistoryDto> findMedicalHistoryByLbp(String lbp) {

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        List<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecord(medicalRecord);

        return medicalHistoryMapper.toDto(medicalHistories);
    }

    @Override
    //    @Cacheable(value = "medHisotryPaged", key = "{#lbp, #page, #size}")
    public Page<MedicalHistoryDto> findMedicalHistoryByLbpPaged(String lbp, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordPaged(pageable,medicalRecord);

        return medicalHistories.map(medicalHistoryMapper::toDto);
    }

    @Override
    //    @Cacheable(value = "medHisotryCodePaged", key = "{#lbp, #code, #page, #size}")
    public Page<MedicalHistoryDto> findMedicalHistoryByLbpAndDiagnosisCodePaged(String lbp, String code, int page, int size) {

        if(code==null)
            return findMedicalHistoryByLbpPaged(lbp,page,size);

        Pageable pageable= PageRequest.of(page,size);

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        Page<MedicalHistory> medicalHistories=medicalHistoryRepository.findMedicalHistoryByMedicalRecordAndDiagnosisCodePaged(pageable,medicalRecord,code);

        return medicalHistories.map(medicalHistoryMapper::toDto);
    }

    @Override
    public Page<ExaminationHistoryDto> findExaminationHistoryByLbpAndDateRangePaged(String lbp, Date startDate,Date endDate, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        Page<ExaminationHistory> examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecordAndDateRange(pageable,medicalRecord,startDate,endDate);

        return examinationHistories.map(examinationHistoryMapper::toDto);
    }

    ///Dohvatanje liste ExaminationHistory po LBP
    @Cacheable(value = "examHistory", key = "#lbp")
    public List<ExaminationHistoryDto> findExaminationHistoryByLbp(String lbp){

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        List<ExaminationHistory> examinationHistories=examinationHistoryRepository.findExaminationHistoryByMedicalRecord(medicalRecord);

        return examinationHistoryMapper.toDto(examinationHistories);

    }

    ///Dohvatanje Patient po LBP
    public PatientDto findPatientByLbp(Patient patient){

        return patientMapper.patientToPatientDto(patient);
    }

    ///Dohvatanje CELOG MedicalRecord po LBP
    @Cacheable(value = "medRecord", key = "#lbp")
    public MedicalRecordDto findMedicalRecordByLbp(String lbp){

        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() ->  new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord=medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException(String.format("Medical record for patient with lbp %s not found.", lbp)));

        return medicalRecordMapper.toDto(medicalRecord,findPatientByLbp(patient),
                findGeneralMedicalDataByLbp(lbp),findOperationsByLbp(lbp),findMedicalHistoryByLbp(lbp),
                findExaminationHistoryByLbp(lbp)
        );
    }
}

