package raf.bolnica1.laboratory.dto.prescription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDeleteDto {
    private Long prescriptionId;
    private String authorization;
}
