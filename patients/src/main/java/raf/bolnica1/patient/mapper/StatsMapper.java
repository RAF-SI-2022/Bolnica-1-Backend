package raf.bolnica1.patient.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.stats.CovidStats;
import raf.bolnica1.patient.dto.stats.CovidStatsResultDto;
import raf.bolnica1.patient.dto.stats.CovidSummed;

import java.sql.Date;
import java.util.*;

@Component
public class StatsMapper {
    
    public CovidStatsResultDto mapCovidResult(Page<CovidStats> covidStats, Pageable pageable){
        CovidSummed covidSummed = new CovidSummed();
        Map<Date, CovidSummed> map = new TreeMap<>();

        for(CovidStats cs : covidStats){
            covidSummed.setDead(covidSummed.getDead()+cs.getDead());
            covidSummed.setPositive(covidSummed.getPositive()+cs.getPositive());
            covidSummed.setNegative(covidSummed.getNegative()+cs.getNegative());
            covidSummed.setVaccinated(covidSummed.getVaccinated()+cs.getVaccinated());
            covidSummed.setHealed(covidSummed.getHealed()+cs.getHealed());
            covidSummed.setVentilator(covidSummed.getVentilator()+cs.getVentilator());
            covidSummed.setHospitalized(covidSummed.getHospitalized()+cs.getHospitalized());
            if(!map.containsKey(cs.getDate()))
                map.put(cs.getDate(), new CovidSummed());

            map.get(cs.getDate()).setDate(cs.getDate());
            map.get(cs.getDate()).setDead(map.get(cs.getDate()).getDead()+cs.getDead());
            map.get(cs.getDate()).setPositive(map.get(cs.getDate()).getPositive()+cs.getPositive());
            map.get(cs.getDate()).setNegative(map.get(cs.getDate()).getNegative()+cs.getNegative());
            map.get(cs.getDate()).setVaccinated(map.get(cs.getDate()).getVaccinated()+cs.getVaccinated());
            map.get(cs.getDate()).setHealed(map.get(cs.getDate()).getHealed()+cs.getHealed());
            map.get(cs.getDate()).setVentilator(map.get(cs.getDate()).getVentilator()+cs.getVentilator());
            map.get(cs.getDate()).setHospitalized(map.get(cs.getDate()).getHospitalized()+cs.getHospitalized());
        }

        CovidStatsResultDto covidStatsResultDto = new CovidStatsResultDto();
        List<CovidSummed> covidSummedList = new ArrayList<>();
        for(Map.Entry<Date, CovidSummed> entry : map.entrySet())
            covidSummedList.add(entry.getValue());

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), covidSummedList.size());

        Page<CovidSummed> page = new PageImpl<>(covidSummedList.subList(start, end), pageable, covidSummedList.size());
        covidStatsResultDto.setCovidSummed(covidSummed);
        covidStatsResultDto.setList(page);

        return covidStatsResultDto;
    }
}
