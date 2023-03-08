package raf.bolnica1.employees.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "hospital")
@Getter
@Setter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String pbb;
    private String fullName;
    private String shortName;
    private String place;
    private String address;
    private Date dateOfEstablishment;
    private String activity;
    private boolean deleted = false;

}
