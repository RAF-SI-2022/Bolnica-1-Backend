package raf.bolnica1.infirmary.dto.externalPatientService;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.infirmary.dto.externalPatientService.constants.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class PatientDto implements Serializable {
    private Long id;
    private String jmbg;
    private String lbp;
    private String name;
    private String parentName;
    private String surname;
    private Gender gender;
    private Date dateOfBirth;
    private Timestamp dateAndTimeOfDeath;
    private String birthPlace;
    private String placeOfLiving;
    private CountryCode citizenship;
    private String phone;
    private String email;
    private boolean deleted;
    private String guardianJmbg;
    private String guardianNameAndSurname;
    private MaritalStatus maritalStatus;
    private int numOfChildren;
    private ExpertiseDegree expertiseDegree;
    private String profession;
    private FamilyStatus familyStatus;

}