package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledLabExaminationCreate {

    private String lbp;
    private Date scheduledDate;
    private String note;

}
