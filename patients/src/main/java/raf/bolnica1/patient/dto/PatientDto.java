package raf.bolnica1.patient.dto;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.ExpertiseDegree;
import raf.bolnica1.patient.domain.FamilyStatus;
import raf.bolnica1.patient.domain.Gender;
import raf.bolnica1.patient.domain.MaritalStatus;

@Getter
@Setter
public class PatientDto {
    private Long id;
    private String jmbg;
    private String lbp;
    private String name;
    private String parentName;
    private String surname;
    private Gender gender;
    private String dateOfBirth;
    private String dateAndTimeOfDeath;
    private String birthPlace;
    private String placeOfLiving;
    private String citizenship;
    private String phone;
    private String email;
    private String guardianJmbg;
    private String guardianNameAndSurname;
    private MaritalStatus maritalStatus;
    private int numOfChildren;
    private ExpertiseDegree expertiseDegree;
    private String profession;
    private FamilyStatus familyStatus;



    public PatientDto() {
    }
}
