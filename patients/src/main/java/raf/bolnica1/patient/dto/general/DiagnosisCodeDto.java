package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DiagnosisCodeDto implements Serializable {

    private String code;
    private String description;
    private String latinDescription;
}

