package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.constants.validation.EnumNotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "prescription", indexes = {@Index(columnList = "doctorLbz, departmentFromId")})
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EnumNotBlank
    @Enumerated(EnumType.STRING)
    private PrescriptionType type;
    @NotNull
    private String doctorLbz;
    @NotNull
    private Long departmentFromId;
    @NotNull
    private Long departmentToId;
    @NotBlank
    private String lbp;
    @NotNull
    private Timestamp creationDateTime;
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status = PrescriptionStatus.NEREALIZOVAN;
    private String comment;
}
