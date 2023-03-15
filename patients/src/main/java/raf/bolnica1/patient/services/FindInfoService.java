package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.general.*;

import java.util.List;

public interface FindInfoService {
    GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp);

    MedicalRecordDto findMedicalRecordByLbp(String lbp);

    List<OperationDto> findOperationsByLbp(String lbp);

    List<MedicalHistoryDto> findMedicalHistoryByLbp(String lbp);

    List<ExaminationHistoryDto> findExaminationHistoryByLbp(String lbp);

}
