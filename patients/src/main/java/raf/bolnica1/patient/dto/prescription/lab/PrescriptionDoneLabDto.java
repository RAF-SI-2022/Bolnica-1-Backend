package raf.bolnica1.patient.dto.prescription.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.dto.prescription.general.PrescriptionDoneDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PrescriptionDoneLabDto extends PrescriptionDoneDto {
    private List<PrescriptionAnalysisNameDto> parameters = new ArrayList<>();

}
