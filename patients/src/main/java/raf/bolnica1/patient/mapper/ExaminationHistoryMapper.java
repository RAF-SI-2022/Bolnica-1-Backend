package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.ExaminationHistory;
import raf.bolnica1.patient.dto.AnamnesisDto;
import raf.bolnica1.patient.dto.ExaminationHistoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExaminationHistoryMapper {

    private DiagnosisCodeMapper diagnosisCodeMapper;
    private TherapyMapper therapyMapper;
    private AnamnesisMapper anamnesisMapper;

    public ExaminationHistoryMapper(DiagnosisCodeMapper diagnosisCodeMapper,TherapyMapper therapyMapper,
                                    AnamnesisMapper anamnesisMapper){
        this.diagnosisCodeMapper=diagnosisCodeMapper;
        this.therapyMapper=therapyMapper;
        this.anamnesisMapper=anamnesisMapper;
    }

    public ExaminationHistoryDto toDto(ExaminationHistory entity){
        if(entity==null)return null;

        ExaminationHistoryDto dto=new ExaminationHistoryDto();

        dto.setAdvice(entity.getAdvice());
        dto.setConfidential(entity.isConfidential());
        dto.setLbz(entity.getLbz());
        dto.setObjectiveFinding(entity.getObjectiveFinding());
        dto.setExamDate(entity.getExamDate());
        dto.setAnamnesisDto(anamnesisMapper.toDto(entity.getAnamnesis()));
        dto.setTherapyDto(therapyMapper.toDto(entity.getTherapy()));
        dto.setDiagnosisCodeDto(diagnosisCodeMapper.toDto(entity.getDiagnosisCode()));

        return dto;
    }

    public List<ExaminationHistoryDto> toDto(List<ExaminationHistory> examinationHistories){
        if(examinationHistories==null)return null;

        List<ExaminationHistoryDto> dto=new ArrayList<>();

        for(ExaminationHistory examinationHistory:examinationHistories)
            dto.add(toDto(examinationHistory));

        return dto;
    }

}
