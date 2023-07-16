package raf.bolnica1.patient.dto.stats;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CovidSummed {
    private long positive = 0;
    private long negative = 0;
    private long hospitalized = 0;
    private long ventilator = 0;
    private long dead = 0;
    private long curr = 0;
    private long vaccinated = 0;
    private long healed = 0;
    private Date date;
}
