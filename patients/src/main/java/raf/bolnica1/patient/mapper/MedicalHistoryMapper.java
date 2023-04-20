package raf.bolnica1.patient.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.DiagnosisCode;
import raf.bolnica1.patient.domain.MedicalHistory;
import raf.bolnica1.patient.domain.constants.TreatmentResult;
import raf.bolnica1.patient.dto.create.MedicalHistoryCreateDto;
import raf.bolnica1.patient.dto.general.MedicalHistoryDto;
import raf.bolnica1.patient.repository.DiagnosisCodeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class MedicalHistoryMapper {

    private DiagnosisCodeMapper diagnosisCodeMapper;
    private DiagnosisCodeRepository diagnosisCodeRepository;

    public MedicalHistory toEntity(MedicalHistoryDto medicalHistoryDto){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setStartDate(medicalHistoryDto.getStartDate());
        medicalHistory.setEndDate(medicalHistoryDto.getEndDate());
        medicalHistory.setCurrStateDesc(medicalHistoryDto.getCurrStateDesc());
        medicalHistory.setValidFrom(medicalHistoryDto.getValidFrom());
        medicalHistory.setValidTo(medicalHistoryDto.getValidTo());
        medicalHistory.setTreatmentResult(medicalHistory.getTreatmentResult());

        DiagnosisCode diagnosisCode = diagnosisCodeRepository.findByCode(medicalHistoryDto.getDiagnosisCodeDto().getCode());
        medicalHistory.setDiagnosisCode(diagnosisCode);
        return medicalHistory;
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

    public MedicalHistory getMedicalHistoryFromCreateDtoExist(MedicalHistoryCreateDto medicalHistoryCreateDto, MedicalHistory prev){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDiagnosisCode(prev.getDiagnosisCode());
        medicalHistory.setConfidential(prev.isConfidential());
        medicalHistory.setStartDate(prev.getStartDate());
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        if(!medicalHistoryCreateDto.getTreatmentResult().equals(TreatmentResult.U_TOKU))
            medicalHistory.setEndDate(date);
        medicalHistory.setTreatmentResult(medicalHistoryCreateDto.getTreatmentResult());
        medicalHistory.setCurrStateDesc(medicalHistoryCreateDto.getCurrStateDesc());
        medicalHistory.setValidFrom(date);
        medicalHistory.setValid(true);

        return medicalHistory;
    }

    public MedicalHistory getMedicalHistoryFromCreateDtoNoExist(MedicalHistoryCreateDto medicalHistoryCreateDto){
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDiagnosisCode(diagnosisCodeRepository.findByCode(medicalHistoryCreateDto.getDiagnosisCodeDto().getCode()));
        medicalHistory.setConfidential(medicalHistoryCreateDto.isConfidential());
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        medicalHistory.setStartDate(date);
        medicalHistory.setTreatmentResult(medicalHistoryCreateDto.getTreatmentResult());
        medicalHistory.setCurrStateDesc(medicalHistoryCreateDto.getCurrStateDesc());
        medicalHistory.setValidFrom(date);

        medicalHistory.setValid(true);

        return medicalHistory;
    }

    public List<MedicalHistoryDto> toDto(List<MedicalHistory> medicalHistories){
        if(medicalHistories==null)return null;

        List<MedicalHistoryDto> dto=new ArrayList<>();

        for(MedicalHistory medicalHistory:medicalHistories)
            dto.add(toDto(medicalHistory));

        return dto;
    }

}
