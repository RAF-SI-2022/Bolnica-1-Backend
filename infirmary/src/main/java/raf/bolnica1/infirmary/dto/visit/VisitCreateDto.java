package raf.bolnica1.infirmary.dto.visit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class VisitCreateDto implements Serializable {

    private String visitorName;
    private String visitorSurname;
    private String visitorJmbg;
    private Timestamp visitTime;
    private String note;
    private Long hospitalizationId;

}
