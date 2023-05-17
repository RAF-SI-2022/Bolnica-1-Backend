package raf.bolnica1.laboratory.dto.lab.scheduledLabExamination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledLabExaminationDto implements Serializable {

    private Long id;
    private Long departmentId;
    private String lbp;
    private Date scheduledDate;
    private ExaminationStatus examinationStatus=ExaminationStatus.ZAKAZANO;
    private String note;
    private String lbz;

}
