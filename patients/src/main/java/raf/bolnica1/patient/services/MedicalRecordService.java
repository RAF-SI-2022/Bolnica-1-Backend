package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.create.VaccinationDataDto;
import raf.bolnica1.patient.dto.general.*;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordDto findMedicalRecord(String lbp);

    GeneralMedicalDataDto addGeneralMedicalData(String lbp, GeneralMedicalDataCreateDto generalMedicalDataCreateDto);

    OperationDto addOperation(String lbp, OperationCreateDto operationCreateDto);

    List<AllergyDto> gatherAllergies();

    List<VaccinationDto> gatherVaccines();

    List<DiagnosisCodeDto> gatherDiagnosis();

    MessageDto addVaccine(String lbp, VaccinationDataDto vaccinationDataDto);

    MessageDto addAllergy(String lbp, String allergy);
}
