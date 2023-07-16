package raf.bolnica1.patient.dto.stats;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class CovidStatsResultDto {
    CovidSummed covidSummed;
    Page<CovidSummed> list;
}
