package raf.bolnica1.infirmary.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CovidStatsDto {

    private CovidStat covidStat;
    private String lbp;
}
