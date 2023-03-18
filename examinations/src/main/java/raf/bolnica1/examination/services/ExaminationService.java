package raf.bolnica1.examination.services;

import org.springframework.stereotype.Service;


public interface ExaminationService {

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
