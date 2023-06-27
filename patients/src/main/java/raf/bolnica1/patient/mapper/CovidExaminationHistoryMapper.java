package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.CovidExaminationHistory;
import raf.bolnica1.patient.dto.create.CovidExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.general.CovidExaminationHistoryDto;
import raf.bolnica1.patient.repository.MedicalRecordRepository;

@Component
public class CovidExaminationHistoryMapper {

    public CovidExaminationHistory toEntity(CovidExaminationHistoryCreateDto dto,
                                            MedicalRecordRepository medicalRecordRepository){
        if(dto==null)return null;

        CovidExaminationHistory ret=new CovidExaminationHistory();

        ret.setDuration(dto.getDuration());
        ret.setLbp(dto.getLbp());
        ret.setLbz(dto.getLbz());
        ret.setExamDate(dto.getExamDate());
        ret.setBloodPressure(dto.getBloodPressure());
        ret.setTherapy(dto.getTherapy());
        ret.setSaturation(dto.getSaturation());
        ret.setSymptoms(dto.getSymptoms());
        ret.setLungCondition(dto.getLungCondition());
        ret.setBodyTemperature(dto.getBodyTemperature());
        //ret.setMedicalRecord(medicalRecordRepository.getById(dto.getMedicalRecordId()));

        return ret;
    }

    public CovidExaminationHistoryDto toDto(CovidExaminationHistory entity){
        if(entity==null)return null;
        
        CovidExaminationHistoryDto ret=new CovidExaminationHistoryDto();

        ret.setId(entity.getId());
        ret.setDuration(entity.getDuration());
        ret.setLbp(entity.getLbp());
        ret.setLbz(entity.getLbz());
        ret.setExamDate(entity.getExamDate());
        ret.setBloodPressure(entity.getBloodPressure());
        ret.setTherapy(entity.getTherapy());
        ret.setSaturation(entity.getSaturation());
        ret.setSymptoms(entity.getSymptoms());
        ret.setLungCondition(entity.getLungCondition());
        ret.setBodyTemperature(entity.getBodyTemperature());
        ret.setMedicalRecordId(entity.getMedicalRecord().getId());

        return ret;
    }

}
