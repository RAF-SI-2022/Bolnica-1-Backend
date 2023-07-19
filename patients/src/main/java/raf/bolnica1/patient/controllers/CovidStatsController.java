package raf.bolnica1.patient.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import raf.bolnica1.patient.domain.constants.CovidStat;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.stats.CovidStats;
import raf.bolnica1.patient.dto.stats.CovidStatsDto;
import raf.bolnica1.patient.dto.stats.CovidStatsResultDto;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.CovidStatsRepository;
import raf.bolnica1.patient.services.StatsService;

import java.sql.Date;

@RestController
@RequestMapping("/covid_stats")
@AllArgsConstructor
public class CovidStatsController {

    private StatsService statsService;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private CovidStatsRepository covidStatsRepository;


    @GetMapping
    public ResponseEntity<CovidStatsResultDto> getCovidStats(@RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size,
                                                             @RequestParam(required = false) Date startDate,
                                                             @RequestParam(required = false) Date endDate,
                                                             @RequestParam(required = false) Gender gender,
                                                             @RequestParam(required = false, defaultValue = "0") int ageCategory){
        return new ResponseEntity<>(statsService.getCovidStats(page, size, startDate, endDate, gender, ageCategory), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> tryIt(){

        ///jmsTemplate.convertAndSend("covid_stats", messageHelper.createTextMessage(new CovidStatsDto(CovidStat.POSITIVE, "P0002", Date.valueOf("2023-08-02"))));
        jmsTemplate.convertAndSend("covid_stats", messageHelper.createTextMessage(new CovidStatsDto(CovidStat.NEGATIVE, "P0004", Date.valueOf("2023-08-02"))));
        return new ResponseEntity<>("To je to", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> tryItAdd() {
        CovidStats covidStats2 = new CovidStats();
        covidStats2.setAgeCategory(2);
        covidStats2.setDate(new Date(System.currentTimeMillis()));
        covidStats2.setGender(Gender.ZENSKO);
        covidStatsRepository.save(covidStats2);

        CovidStats covidStats1 = new CovidStats();
        covidStats1.setAgeCategory(1);
        covidStats1.setDate(new Date(System.currentTimeMillis()));
        covidStats1.setGender(Gender.ZENSKO);
        covidStatsRepository.save(covidStats1);

        CovidStats covidStats3 = new CovidStats();
        covidStats3.setAgeCategory(3);
        covidStats3.setDate(new Date(System.currentTimeMillis()));
        covidStats3.setGender(Gender.ZENSKO);
        covidStatsRepository.save(covidStats3);

        CovidStats covidStats22 = new CovidStats();
        covidStats22.setAgeCategory(2);
        covidStats22.setDate(new Date(System.currentTimeMillis()));
        covidStats22.setGender(Gender.MUSKO);
        covidStatsRepository.save(covidStats22);

        CovidStats covidStats11 = new CovidStats();
        covidStats11.setAgeCategory(1);
        covidStats11.setDate(new Date(System.currentTimeMillis()));
        covidStats11.setGender(Gender.MUSKO);
        covidStatsRepository.save(covidStats11);

        CovidStats covidStats33 = new CovidStats();
        covidStats33.setAgeCategory(3);
        covidStats33.setDate(new Date(System.currentTimeMillis()));
        covidStats33.setGender(Gender.MUSKO);
        covidStatsRepository.save(covidStats33);

        return new ResponseEntity<>("To je to 2", HttpStatus.OK);
    }


}
