package raf.bolnica1.laboratory.services;

public interface LaboratoryExaminationsService {

    public Object createScheduledExamination(Object object);

    public Object changeExaminationStatus(Object object);

    public Object listScheduledExaminationsByDay(Object object);

    public Object listScheduledExaminations(Object object);
}
