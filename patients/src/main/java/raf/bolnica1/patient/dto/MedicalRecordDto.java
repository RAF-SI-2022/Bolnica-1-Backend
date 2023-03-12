package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.GeneralMedicalData;

import java.util.Date;

@Getter
@Setter
public class MedicalRecordDto {
    private Long id;
    private Date registrationDate;
    private boolean deleted;
    private GeneralMedicalData generalMedicalData;
}
