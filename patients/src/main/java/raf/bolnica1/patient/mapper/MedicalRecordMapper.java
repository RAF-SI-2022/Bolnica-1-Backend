package raf.bolnica1.patient.mapper;

import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;
import raf.bolnica1.patient.dto.general.*;

import java.util.List;

@Component
public class MedicalRecordMapper {

    public MedicalRecordDto toDto(MedicalRecord medicalRecord, PatientDto patientDto,
                                  GeneralMedicalDataDto generalMedicalDataDto, List<OperationDto> operationDtoList,
                                  List<MedicalHistoryDto> medicalHistoryDtos, List<ExaminationHistoryDto> examinationHistoryDtos

    ){
        if(medicalRecord==null)return null;

        MedicalRecordDto dto=new MedicalRecordDto();

        dto.setId(medicalRecord.getId());
        dto.setOperationDtos(operationDtoList);
        dto.setMedicalHistoryDtos(medicalHistoryDtos);
        dto.setExaminationHistoryDtos(examinationHistoryDtos);
        dto.setPatient(patientDto);
        dto.setRegistrationDate(medicalRecord.getRegistrationDate());
        dto.setGeneralMedicalDataDto(generalMedicalDataDto);

        return dto;
    }
}
