package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.dto.MedicalHistoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalHistoryMapper {

    private DiagnosisCodeMapper diagnosisCodeMapper;

    public MedicalHistoryMapper(DiagnosisCodeMapper diagnosisCodeMapper){
        this.diagnosisCodeMapper=diagnosisCodeMapper;
    }

    public MedicalHistoryDto toDto(MedicalHistory entity){
        if(entity==null)return null;

        MedicalHistoryDto dto=new MedicalHistoryDto();

        dto.setTreatmentResult(entity.getTreatmentResult());
        dto.setValidTo(entity.getValidTo());
        dto.setEndDate(entity.getEndDate());
        dto.setValidFrom(entity.getValidFrom());
        dto.setCurrStateDesc(entity.getCurrStateDesc());
        dto.setStartDate(entity.getStartDate());
        dto.setValid(entity.isValid());
        dto.setDiagnosisCodeDto(diagnosisCodeMapper.toDto(entity.getDiagnosisCode()));

        return dto;
    }

    public List<MedicalHistoryDto> toDto(List<MedicalHistory> medicalHistories){
        if(medicalHistories==null)return null;

        List<MedicalHistoryDto> dto=new ArrayList<>();

        for(MedicalHistory medicalHistory:medicalHistories)
            dto.add(toDto(medicalHistory));

        return dto;
    }

}
