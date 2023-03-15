package raf.bolnica1.patient.dto.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientUpdateDto extends PatientGeneralDto{
    private boolean deleted;
}
