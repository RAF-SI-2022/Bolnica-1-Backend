package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;

public interface MedicalExaminationService {

    ExaminationHistoryDto addExamination(String lbp, ExaminationHistoryCreateDto examinationHistoryCreateDto);

    MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryCreateDto medicalHistoryCreateDto);
}
