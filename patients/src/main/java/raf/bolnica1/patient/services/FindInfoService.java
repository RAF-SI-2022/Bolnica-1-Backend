package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.dto.general.*;

import java.sql.Date;
import java.util.List;

public interface FindInfoService {
    GeneralMedicalDataDto findGeneralMedicalDataByLbp(String lbp);

    MedicalRecordDto findMedicalRecordByLbp(String lbp);

    List<OperationDto> findOperationsByLbp(String lbp);

    List<MedicalHistoryDto> findMedicalHistoryByLbp(String lbp);

    Page<MedicalHistoryDto> findMedicalHistoryByLbpPaged(String lbp, int page, int size);

    Page<MedicalHistoryDto> findMedicalHistoryByLbpAndDiagnosisCodePaged(String lbp,String code, int page, int size);

    Page<ExaminationHistoryDto> findExaminationHistoryByLbpAndDateRangePaged(String lbp, Date startDate,Date endDate, int page, int size);

    List<ExaminationHistoryDto> findExaminationHistoryByLbp(String lbp);

}
