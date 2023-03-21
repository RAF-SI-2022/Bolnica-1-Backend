package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.ExaminationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "scheduled_lab_examination", indexes = {@Index(columnList = "departmentId, lbp")})
public class ScheduledLabExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private Long departmentId;
    @NotBlank
    private String lbp;
    @NotBlank
    private Date scheduledDate;
    @Enumerated(EnumType.STRING)
    private ExaminationStatus examinationStatus = ExaminationStatus.ZAKAZANO;
    private String note;
    @NotBlank
    private String lbz;
}
