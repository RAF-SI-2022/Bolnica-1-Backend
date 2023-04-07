package raf.bolnica1.infirmary.services;

import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.MedicalRecordDto;
import raf.bolnica1.infirmary.dto.response.MessageDto;

public interface MedicalRecordService {

    MedicalRecordDto getMedicalRecordByLbp(String lbp,String authorization);

    MessageDto createExaminationHistory(ExaminationHistoryCreateDto examinationHistoryCreateDto);

}
