package raf.bolnica1.infirmary.dto.dischargeList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;

import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class DischargeListDto {

    private Long id;
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
