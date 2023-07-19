package raf.bolnica1.patient.messaging.helper;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Patient;
import raf.bolnica1.patient.domain.constants.CovidStat;
import raf.bolnica1.patient.domain.stats.CovidStats;
import raf.bolnica1.patient.dto.stats.CovidStatsDto;
import raf.bolnica1.patient.repository.CovidStatsRepository;
import raf.bolnica1.patient.repository.PatientRepository;

import javax.jms.JMSException;
import javax.jms.Message;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Component
@AllArgsConstructor
public class StatsListener {

    private CovidStatsRepository covidStatsRepository;
    private PatientRepository patientRepository;
    private MessageHelper messageHelper;

    @JmsListener(destination = "${destination.send.stats}", concurrency = "5-10")
    public void getStats(Message message) throws JMSException {
        System.out.println("DOSOOOOO");
        CovidStatsDto covidStatsDto = messageHelper.getMessage(message, CovidStatsDto.class);
        Date date = covidStatsDto.getDate();
        CovidStats covidStats = null;
        Patient patient = patientRepository.findByLbp(covidStatsDto.getLbp()).orElse(null);

        if(patient != null) {
            int ageCategory = calculateYearsPassed(patient.getDateOfBirth(), date);
            System.out.println(patient.getLbp() + " " + ageCategory);
            while (true) {
                covidStats = covidStatsRepository.findByDateAndGenderAndAgeCategory(date, patient.getGender(), ageCategory).orElse(null);
                if (covidStats != null) break;
            }
            System.out.println(covidStats.getDate());
            System.out.println(covidStatsDto.getCovidStat());
            if (covidStatsDto.getCovidStat().equals(CovidStat.POSITIVE))
                covidStats.setPositive(covidStats.getPositive() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.NEGATIVE))
                covidStats.setNegative(covidStats.getNegative() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.HEALED))
                covidStats.setHealed(covidStats.getHealed() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.DEAD))
                covidStats.setDead(covidStats.getDead() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.HOSPITALIZED))
                covidStats.setHospitalized(covidStats.getHospitalized() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.VENTILATOR_ADD))
                covidStats.setVentilator(covidStats.getVentilator() + 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.VENTILATOR_REMOVE))
                covidStats.setVentilator(covidStats.getVentilator() - 1);
            else if (covidStatsDto.getCovidStat().equals(CovidStat.VACCINATED))
                covidStats.setVaccinated(covidStats.getVaccinated() + 1);

            covidStatsRepository.save(covidStats);
        }
    }

    private int calculateYearsPassed(Date date1, Date date2) {
        LocalDate localDate1 = date1.toLocalDate();
        LocalDate localDate2 = date2.toLocalDate();

        // Calculate the difference between the two LocalDate objects
        Period period = Period.between(localDate2, localDate1);

        // Get the years from the period
        int yearsPassed = Math.abs(period.getYears());

        System.out.println("Y " + yearsPassed);
        if(yearsPassed <= 17) return 1;
        if(yearsPassed > 17 && yearsPassed <= 60) return 2;
        else return 3;

    }
}
