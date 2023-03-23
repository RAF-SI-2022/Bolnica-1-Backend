package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.general.*;

import java.util.List;

public interface FindInfoService {
    GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp);

    MedicalRecordDto findMedicalRecordByLbp(String lbp);

    List<OperationDto> findOperationsByLbp(String lbp);

    List<MedicalHistoryDto> findMedicalHistoryByLbp(String lbp);

    Page<List<MedicalHistoryDto>> findMedicalHistoryByLbpPaged(String lbp, int page, int size);

    Page<List<MedicalHistoryDto>> findMedicalHistoryByLbpAndDiagnosisCodePaged(String lbp,String code, int page, int size);

    List<ExaminationHistoryDto> findExaminationHistoryByLbp(String lbp);

}
