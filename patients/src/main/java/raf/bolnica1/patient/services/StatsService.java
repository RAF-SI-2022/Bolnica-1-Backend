package raf.bolnica1.patient.services;

import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.dto.stats.CovidStatsResultDto;

import java.sql.Date;

public interface StatsService {

    CovidStatsResultDto getCovidStats(int page, int size, Date startDate, Date endDate, Gender gender, int ageCategory);
}
