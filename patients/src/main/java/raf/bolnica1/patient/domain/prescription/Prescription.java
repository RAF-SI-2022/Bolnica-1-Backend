package raf.bolnica1.patient.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.MedicalRecord;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "prescription")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "Type")
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String doctorLbz;
    @NotNull
    private Long departmentFromId;
    @NotNull
    private Long departmentToId;
    @NotNull
    private Date date;
    @ManyToOne
    private MedicalRecord medicalRecord;

    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
