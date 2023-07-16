package raf.bolnica1.patient.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.bolnica1.patient.domain.constants.CovidStat;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CovidStatsDto {

    private CovidStat covidStat;
    private String lbp;
    private Date date;
}
