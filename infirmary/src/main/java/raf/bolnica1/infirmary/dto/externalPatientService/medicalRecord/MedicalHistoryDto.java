package raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class MedicalHistoryDto implements Serializable {

    private Date startDate;
    private Date endDate;
    private TreatmentResult treatmentResult;
    private String currStateDesc;
    private Date validFrom;
    private Date validTo = Date.valueOf("9999-12-31");
    private boolean valid;
    private DiagnosisCodeDto diagnosisCodeDto;

}

