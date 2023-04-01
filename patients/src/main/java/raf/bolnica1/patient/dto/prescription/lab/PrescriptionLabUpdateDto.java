package raf.bolnica1.patient.dto.prescription.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionUpdateDto;

import java.util.List;

@Getter
@Setter
public class PrescriptionLabUpdateDto extends PrescriptionUpdateDto {
    private String comment;
    private List<PrescriptionAnalysisDto> prescriptionAnalysisDtos;
}
