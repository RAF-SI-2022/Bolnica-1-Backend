package raf.bolnica1.patient.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.patient.domain.*;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.AllergyDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;
import raf.bolnica1.patient.dto.general.OperationDto;
import raf.bolnica1.patient.dto.general.VaccinationDto;
import raf.bolnica1.patient.mapper.GeneralMedicalDataMapper;
import raf.bolnica1.patient.mapper.OperationMapper;
import raf.bolnica1.patient.repository.*;
import raf.bolnica1.patient.services.MedicalRecordService;

@Service
@Transactional
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private GeneralMedicalDataMapper generalMedicalDataMapper;
    private OperationMapper operationMapper;
    private PatientRepository patientRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private GeneralMedicalDataRepository generalMedicalDataRepository;
    private AllergyDataRepository allergyDataRepository;
    private VaccinationDataRepository vaccinationDataRepository;
    private AllergyRepository allergyRepository;
    private VaccinationRepository vaccinationRepository;
    private OperationRepository operationRepository;

    @Override
    public GeneralMedicalDataDto addGeneralMedicalData(String lbp, GeneralMedicalDataCreateDto generalMedicalDataCreateDto) {
        Patient patient = patientRepository.findByLbp(lbp).orElseThrow(() -> new RuntimeException(String.format("Patient with lbp %s not found.", lbp)));
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatient(patient).orElseThrow(() -> new RuntimeException());

        GeneralMedicalData generalMedicalData = generalMedicalDataMapper.toEntity(generalMedicalDataCreateDto);
        generalMedicalData = generalMedicalDataRepository.save(generalMedicalData);

        for(AllergyDto allergyDto : generalMedicalDataCreateDto.getAllergyDtos()){
            AllergyData allergyData = new AllergyData();
            allergyData.setAllergy(allergyRepository.findByName(allergyDto.getName()));
            allergyData.setGeneralMedicalData(generalMedicalData);
            allergyDataRepository.save(allergyData);
        }

        for(VaccinationDto vaccinationDto : generalMedicalDataCreateDto.getVaccinationDtos()){
            VaccinationData vaccinationData = new VaccinationData();
            vaccinationData.setVaccination(vaccinationRepository.findByName(vaccinationDto.getName()));
            vaccinationData.setGeneralMedicalData(generalMedicalData);
            vaccinationData.setVaccinationDate(vaccinationDto.getVaccinationDate());
            vaccinationDataRepository.save(vaccinationData);
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

}
