package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "lab_work_order", indexes = {@Index(columnList = "lbp, creationDateTime")})
public class LabWorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Prescription prescription;
    @NotBlank
    private String lbp;
    @NotNull
    private Timestamp creationDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEOBRADJEN;
    @NotBlank
    private String technicianLbz;
    private String biochemistLbz = null;
}
