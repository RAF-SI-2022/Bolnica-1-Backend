package raf.bolnica1.patient.dto.prescription.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionSendDto;

import java.util.List;

@Getter
@Setter
public class PrescriptionLabSendDto extends PrescriptionSendDto {
    private String comment;
    private List<PrescriptionAnalysisDto> prescriptionAnalysisDtos;
}
