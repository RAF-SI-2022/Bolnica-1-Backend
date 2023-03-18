package raf.bolnica1.laboratory.domain.lab;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "lab_work_order", indexes = {@Index(columnList = "lbp, creationDateTime")})
public class LabWorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @ManyToOne
    private Prescription prescription;
    @NotBlank
    private Long lbp;
    @NotBlank
    private Timestamp creationDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEOBRADJEN;
    @NotBlank
    private Long technicianLbz;
    private Long biochemistLbz = null;
}
