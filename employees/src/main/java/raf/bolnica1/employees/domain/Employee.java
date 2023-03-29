package raf.bolnica1.employees.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "employee", indexes = {@Index(name = "uniqueIndex1", columnList = "lbz"), @Index(columnList = "username, password")})
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String lbz;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String gender;
    private String jmbg;
    private String address;
    private String placeOfLiving;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String newPassword = "NO";
    private String resetPassword = "NO";
    private boolean deleted = false;
    @Enumerated(EnumType.STRING)
    private Title title;
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @ManyToOne
    private Department department;

}
