package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class MedicalRecordDto {

    private Date registrationDate;
    private PatientDto patient;
    private GeneralMedicalDataDto generalMedicalDataDto;
    private List<OperationDto> operationDtos;
    private List<MedicalHistoryDto> medicalHistoryDtos;
    private List<ExaminationHistoryDto> examinationHistoryDtos;
}
