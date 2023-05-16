package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.TreatmentResult;

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
