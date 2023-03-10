package raf.bolnica1.patient.domain;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.ExpertiseDegree;
import raf.bolnica1.patient.domain.constants.FamilyStatus;
import raf.bolnica1.patient.domain.constants.MaritalStatus;

import javax.persistence.*;

@Entity
@Table(name = "social_data")
@Getter
@Setter
public class SocialData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private int numOfChildren;
    @Enumerated(EnumType.STRING)
    private ExpertiseDegree expertiseDegree;
    private String profession;
    @Enumerated(EnumType.STRING)
    private FamilyStatus familyStatus;
}
