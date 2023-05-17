package raf.bolnica1.infirmary.dto.dischargeList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class CreateDischargeListDto implements Serializable {
    private String followingDiagnosis;
    private String anamnesis;
    private String analysis;
    private String courseOfDisease;
    private String summary;
    private String therapy;
    private String lbzPrescribing;
    private String lbzDepartment;
    private Timestamp creation;
    private Long hospitalizationId;
}
