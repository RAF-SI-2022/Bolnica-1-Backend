package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.GeneralMedicalDataDto;
import raf.bolnica1.patient.dto.general.OperationDto;

public interface MedicalRecordService {

    GeneralMedicalDataDto addGeneralMedicalData(String lbp, GeneralMedicalDataCreateDto generalMedicalDataCreateDto);

    OperationDto addOperation(String lbp, OperationCreateDto operationCreateDto);
}
