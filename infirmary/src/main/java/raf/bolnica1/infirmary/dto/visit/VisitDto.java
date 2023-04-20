package raf.bolnica1.infirmary.dto.visit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.domain.Hospitalization;

import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class VisitDto {

    private Long id;
    private String visitorName;
    private String visitorSurname;
    private String visitorJmbg;
    private String lbzRegister;
    private Timestamp visitTime;
    private String note;
    private Long hospitalizationId;

}
