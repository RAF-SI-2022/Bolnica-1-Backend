package raf.bolnica1.patient.services;

import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.ExaminationHistoryDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;

public interface MedicalExaminationService {

    ExaminationHistoryDto addExamination(String lbp, ExaminationHistoryCreateDto examinationHistoryCreateDto);

    MedicalHistoryDto addMedicalHistory(String lbp, MedicalHistoryCreateDto medicalHistoryCreateDto);

    //Pretraga zakazanih pregleda
    public Object findScheduledExamination(Object object);
    // Azuriranje statusa o prispecu pacijenta
    public Object updatePatientArrivalStatus(Object object);

    // Azuriranje statusa pregleda
    public Object updateExaminationStatus(Object object);

    //Brisanje zakazanog pregleda
    public Object deleteScheduledExamination(Object object);

    // Pretraga lekara specijalista na odeljenju
    public Object findDoctorSpecByDepartment(Object object);
}
