package raf.bolnica1.patient.messaging.helper.stats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import raf.bolnica1.patient.domain.Hospitalization;
import raf.bolnica1.patient.domain.MedicalRecord;
import raf.bolnica1.patient.domain.constants.CovidStat;
import raf.bolnica1.patient.domain.constants.Gender;
import raf.bolnica1.patient.domain.prescription.LabResults;
import raf.bolnica1.patient.domain.stats.CovidStats;
import raf.bolnica1.patient.dto.stats.CovidStatsDto;
import raf.bolnica1.patient.messaging.helper.MessageHelper;
import raf.bolnica1.patient.repository.CovidStatsRepository;
import raf.bolnica1.patient.repository.HospitalizationRepository;
import raf.bolnica1.patient.repository.LabResultsRepository;
import raf.bolnica1.patient.repository.PatientRepository;

import java.sql.Date;
import java.util.List;

@Component
public class StatsScheduler {

    private CovidStatsRepository covidStatsRepository;
    private LabResultsRepository labResultsRepository;
    private PatientRepository patientRepository;
    private HospitalizationRepository hospitalizationRepository;
    private JmsTemplate jmsTemplate;
    private String destinationStats;
    private MessageHelper messageHelper;

    public StatsScheduler(CovidStatsRepository covidStatsRepository,
                                LabResultsRepository labResultsRepository,
                                PatientRepository patientRepository,
                                HospitalizationRepository hospitalizationRepository,
                                JmsTemplate jmsTemplate,
                                @Value("${destination.send.stats}") String destinationStats,
                                MessageHelper messageHelper) {
        this.covidStatsRepository = covidStatsRepository;
        this.labResultsRepository = labResultsRepository;
        this.patientRepository = patientRepository;
        this.hospitalizationRepository = hospitalizationRepository;
        this.jmsTemplate = jmsTemplate;
        this.destinationStats = destinationStats;
        this.messageHelper = messageHelper;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void taskMethod() {

        createRow(Gender.ZENSKO);
        createRow(Gender.MUSKO);

        Date date = new Date(System.currentTimeMillis());
        List<LabResults> all = labResultsRepository.findAllPerscriptionsForCovidStats(date);
        for(LabResults labResults : all) {
            MedicalRecord medicalRecord = labResults.getLabPrescription().getMedicalRecord();
            Hospitalization hospitalization = hospitalizationRepository.findActiveHospitalization(medicalRecord.getId()).orElse(null);
            if (hospitalization == null) {
                jmsTemplate.convertAndSend(destinationStats, messageHelper.createTextMessage(new CovidStatsDto(CovidStat.HEALED, medicalRecord.getPatient().getLbp(), date)));
                labResults.getLabPrescription().setDone(true);
                labResultsRepository.save(labResults);
            }
        }
    }

    private void createRow(Gender gender){
        for(int i = 1; i<=3; i++) {
            CovidStats covidStats = new CovidStats();
            covidStats.setGender(gender);
            covidStats.setAgeCategory(i);
            covidStats.setDate(new Date(System.currentTimeMillis()));
            covidStatsRepository.save(covidStats);
        }
    }

}
