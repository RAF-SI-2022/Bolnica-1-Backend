package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.dto.externalPatientService.PatientDto;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicalRecordDto {

    private Long id;
    private Date registrationDate;
    private PatientDto patient;
    private GeneralMedicalDataDto generalMedicalDataDto;
    private List<OperationDto> operationDtos;
    private List<MedicalHistoryDto> medicalHistoryDtos;
    private List<ExaminationHistoryDto> examinationHistoryDtos;
}
