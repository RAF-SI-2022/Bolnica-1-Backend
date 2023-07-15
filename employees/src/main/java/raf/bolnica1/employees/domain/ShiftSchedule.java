package raf.bolnica1.employees.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "shift_schedule")
@Getter
@Setter
public class ShiftSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="employee", nullable=false)
    private Employee employee;
    @ManyToOne
    private Shift shift;
    private Date date;
}
