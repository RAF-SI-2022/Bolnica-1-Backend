package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.*;
import raf.bolnica1.patient.mapper.*;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalRecordService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private GeneralMedicalDataMapper generalMedicalDataMapper;
    private OperationMapper operationMapper;

    private DiagnosisCodeRepository diagnosisCodeRepository;
    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private AllergyDataRepository allergyDataRepository;
    private VaccinationDataRepository vaccinationDataRepository;
    private AllergyRepository allergyRepository;

    private AllergyMapper allergyMapper;
    private VaccinationRepository vaccinationRepository;

    private VaccinationMapper vaccinationMapper;

    private DiagnosisCodeMapper diagnosisCodeMapper;
    private OperationRepository operationRepository;

    @Override
    public GeneralMedicalDataDto addGeneralMedicalData(String lbp, GeneralMedicalDataCreateDto generalMedicalDataCreateDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException());

        GeneralMedicalData generalMedicalData = generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto);
        generalMedicalData = generalMedicalDataRepository.save(generalMedicalData);

        if(generalMedicalDataCreateDto.getAllergyDtos() != null) {
            for (AllergyDto allergyDto : generalMedicalDataCreateDto.getAllergyDtos()) {
                AllergyData allergyData = new AllergyData();
                allergyData.setAllergy(allergyRepository.findByName(allergyDto.getName()));
                allergyData.setGeneralMedicalData(generalMedicalData);
                allergyDataRepository.save(allergyData);
            }
        }

        if(generalMedicalDataCreateDto.getVaccinationDtos() != null) {
            for (VaccinationDto vaccinationDto : generalMedicalDataCreateDto.getVaccinationDtos()) {
                VaccinationData vaccinationData = new VaccinationData();
                vaccinationData.setVaccination(vaccinationRepository.findByName(vaccinationDto.getName()));
                vaccinationData.setGeneralMedicalData(generalMedicalData);
                vaccinationData.setVaccinationDate(vaccinationDto.getVaccinationDate());
                vaccinationDataRepository.save(vaccinationData);
            }
        }

        medicalRecord.setGeneralMedicalData(generalMedicalData);
        return generalMedicalDataMapper.toDto(generalMedicalData);
    }

    @Override
    public OperationDto addOperation(String lbp, OperationCreateDto operationCreateDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException());

        Operation operation = operationMapper.toEntity(operationCreateDto, medicalRecord);
        operation = operationRepository.save(operation);

        return operationMapper.toDto(operation);
    }

    @Override
    public List<AllergyDto> gatherAllergies() {
        List<Allergy> allergies =  allergyRepository.findAll();
        List<AllergyDto> allergyDtos = new ArrayList<>();
        for(Allergy a: allergies) {
            allergyDtos.add(allergyMapper.toDto(a));
        }
        return allergyDtos;
    }

    @Override
    public List<VaccinationDto> gatherVaccines() {
        List<Vaccination> vaccinations =  vaccinationRepository.findAll();
        List<VaccinationDto> vaccinationDtos = new ArrayList<>();
        for(Vaccination a: vaccinations) {
            vaccinationDtos.add(vaccinationMapper.toDto(a));
        }
        return vaccinationDtos;
    }

    @Override
    public List<DiagnosisCodeDto> gatherDiagnosis() {
        List<DiagnosisCode> diagnosisCodes =  diagnosisCodeRepository.findAll();
        List<DiagnosisCodeDto> diagnosisCodeDtos = new ArrayList<>();
        for(DiagnosisCode a: diagnosisCodes) {
            diagnosisCodeDtos.add(diagnosisCodeMapper.toDto(a));
        }
        return diagnosisCodeDtos;
    }

}
