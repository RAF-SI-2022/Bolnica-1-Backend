package raf.bolnica1.infirmary.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name="discharge_list")
public class DischargeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String followingDiagnosis;
    private String anamnesis;
    private String analysis;
    private String courseOfDisease;
    private String summary;
    private String therapy;
    private String lbzPrescribing;
    private String lbzDepartment;
    private Timestamp creation;
    @OneToOne
    private Hospitalization hospitalization;
    private boolean died;
}
