package raf.bolnica1.laboratory.services.lab;

public interface LabExaminationsService {

    Object createScheduledExamination(Object object);

    Object changeExaminationStatus(Object object);

    Object listScheduledExaminationsByDay(Object object);

    Object listScheduledExaminations(Object object);
}
