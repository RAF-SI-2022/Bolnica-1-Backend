package raf.bolnica1.patient.domain;


import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.CountryCode;
import raf.bolnica1.patient.domain.constants.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name =  "patient", indexes = {@Index(columnList = "lbp")})
@Getter
@Setter
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jmbg;
    @Column(unique = true)
    private String lbp;
    private String name;
    private String parentName;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dateOfBirth;
    private Timestamp dateAndTimeOfDeath;
    private String birthPlace;
    private String placeOfLiving;
    @Enumerated(EnumType.STRING)
    private CountryCode residenceCountry;
    @Enumerated(EnumType.STRING)
    private CountryCode citizenship;
    private String phone;
    private String email;
    private String guardianJmbg;
    private String guardianNameAndSurname;
    private boolean deleted = false;
    @OneToOne
    private SocialData socialData;


}
