package raf.bolnica1.employees.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "shift")
@Getter
@Setter
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int shiftNum;
    private Time startTime;
    private Time endTime;
    private boolean active;
}
