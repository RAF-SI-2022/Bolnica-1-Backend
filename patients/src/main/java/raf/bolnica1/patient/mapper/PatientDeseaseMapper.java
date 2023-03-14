package raf.bolnica1.patient.mapper;


import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.dto.general.PatientDtoDesease;

import java.util.ArrayList;
import java.util.List;

public class PatientDeseaseMapper {
    public static PatientDtoDesease toDto(MedicalHistory medicalHistory){

        PatientDtoDesease dto = new PatientDtoDesease();

       dto.setId(medicalHistory.getId());
       dto.setStartDate(medicalHistory.getStartDate());
       dto.setEndDate(medicalHistory.getEndDate());
       dto.setTreatmentResult(medicalHistory.getTreatmentResult());
       dto.setCurrStateDesc(medicalHistory.getCurrStateDesc());
       dto.setValid(medicalHistory.isValid());
       dto.setValidTo(medicalHistory.getValidTo());
       dto.setValidFrom(medicalHistory.getValidFrom());

        return dto;
    }

    public static List<PatientDtoDesease> allToDto(List<MedicalHistory> list){

        List<PatientDtoDesease> listDto = new ArrayList<>();

        for(MedicalHistory medicalHistory: list){
            listDto.add(toDto(medicalHistory));
        }

        return listDto;
    }
}
