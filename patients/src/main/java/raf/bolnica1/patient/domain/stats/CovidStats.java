package raf.bolnica1.patient.domain.stats;

import lombok.Getter;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.Gender;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "covid_stats")
@Getter
@Setter
public class CovidStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private long positive = 0;
    private long negative = 0;
    private long hospitalized = 0;
    private long ventilator = 0;
    private long dead = 0;
    private long curr = 0;
    private long vaccinated = 0;
    private long healed = 0;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int ageCategory;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }
}
