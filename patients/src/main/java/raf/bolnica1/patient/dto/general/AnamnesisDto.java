package raf.bolnica1.patient.dto.general;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AnamnesisDto implements Serializable {

    private String mainProblems;
    private String currDisease;
    private String personalAnamnesis;
    private String familyAnamnesis;
    private String patientOpinion;

}
