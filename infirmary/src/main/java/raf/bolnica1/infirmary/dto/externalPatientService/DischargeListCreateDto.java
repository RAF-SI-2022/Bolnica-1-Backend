package raf.bolnica1.infirmary.dto.externalPatientService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DischargeListCreateDto {
    private String followingDiagnosis;
    private String anamnesis;
    private String analysis;
    private String courseOfDisease;
    private String summary;
    private String therapy;
    private Timestamp creation;
    private boolean died;
    private Timestamp hospitalization;
    private String lbp;
}
