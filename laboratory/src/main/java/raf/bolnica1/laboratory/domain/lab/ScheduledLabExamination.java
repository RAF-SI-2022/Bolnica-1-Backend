package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "scheduled_lab_examination", indexes = {@Index(columnList = "departmentId, lbp")})
public class ScheduledLabExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private Long departmentId;
    @NotEmpty
    private Long lbp;
    @NotEmpty
    private Date scheduledDate;
    @Enumerated(EnumType.STRING)
    private ExaminationStatus examinationStatus = ExaminationStatus.ZAKAZANO;
    private String note;
    @NotEmpty
    private Long lbz;
}
