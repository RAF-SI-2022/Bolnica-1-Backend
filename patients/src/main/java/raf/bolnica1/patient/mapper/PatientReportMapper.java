package raf.bolnica1.patient.mapper;

import raf.bolnica1.patient.domain.ExaminationHistory;
import raf.bolnica1.patient.dto.PatientDtoReport;

import java.util.ArrayList;
import java.util.List;

public class PatientReportMapper {

    public static PatientDtoReport toDto(ExaminationHistory examinationHistory){

        PatientDtoReport dto = new PatientDtoReport();

       dto.setId(examinationHistory.getId());
       dto.setAdvice(examinationHistory.getAdvice());
       dto.setAnamnesis(examinationHistory.getAnamnesis());
       dto.setExamDate(examinationHistory.getExamDate());
       dto.setLbz(examinationHistory.getLbz());
       dto.setConfidential(examinationHistory.isConfidential());
       dto.setObjectiveFinding(examinationHistory.getObjectiveFinding());
       dto.setDiagnosisCode(examinationHistory.getDiagnosisCode());
       dto.setTherapy(examinationHistory.getTherapy());


        return dto;
    }

    public static List<PatientDtoReport> allToDto(List<ExaminationHistory> list){

        List<PatientDtoReport> listDto = new ArrayList<>();

        for(ExaminationHistory examinationHistory: list){
            listDto.add(toDto(examinationHistory));
        }

        return listDto;
    }

}
