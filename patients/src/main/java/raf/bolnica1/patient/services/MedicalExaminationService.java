package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.dto.general.MessageDto;

public interface MedicalExaminationService {

    ExaminationHistoryDto addExamination(String lbp, ExaminationHistoryCreateDto examinationHistoryCreateDto);

    MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryCreateDto medicalHistoryCreateDto);


    //Pretraga zakazanih pregleda
    Object findScheduledExamination(Object object);
    // Azuriranje statusa o prispecu pacijenta
    Object updatePatientArrivalStatus(Object object);

    // Azuriranje statusa pregleda
    Object updateExaminationStatus(Object object);

    MessageDto deleteScheduledExamination(Long id);

    // Pretraga lekara specijalista na odeljenju
    Object findDoctorSpecByDepartment(Object object);
}
