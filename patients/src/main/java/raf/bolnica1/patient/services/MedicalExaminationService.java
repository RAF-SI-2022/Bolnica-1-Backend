package raf.bolnica1.patient.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.patient.domain.CovidExaminationHistory;
import raf.bolnica1.patient.dto.create.CovidExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.CovidExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.dto.general.MessageDto;

public interface MedicalExaminationService {

    ExaminationHistoryDto addExamination(String lbp, ExaminationHistoryCreateDto examinationHistoryCreateDto);

    CovidExaminationHistoryDto addCovidExamination(String lbp, CovidExaminationHistoryCreateDto examinationHistoryCreateDto);

    Page<CovidExaminationHistoryDto> getCovidExaminationByLbp(String lbp,int page,int size);

    MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryCreateDto medicalHistoryCreateDto);

}
