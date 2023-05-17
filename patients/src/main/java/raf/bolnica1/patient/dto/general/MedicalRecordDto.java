package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.create.ExaminationHistoryCreateDto;
import raf.bolnica1.patient.dto.create.GeneralMedicalDataCreateDto;
import raf.bolnica1.patient.dto.create.OperationCreateDto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class MedicalRecordDto implements Serializable {

    private Long id;
    private Date registrationDate;
    private PatientDto patient;
    private GeneralMedicalDataDto generalMedicalDataDto;
    private List<OperationDto> operationDtos;
    private List<MedicalHistoryDto> medicalHistoryDtos;
    private List<ExaminationHistoryDto> examinationHistoryDtos;
}
