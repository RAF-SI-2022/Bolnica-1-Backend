package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.ScheduledAppointmentCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Prescription;
import raf.bolnica1.infirmary.domain.ScheduledAppointment;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;

@AllArgsConstructor
@Component
public class ScheduledAppointmentGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public static ScheduledAppointmentGenerator getInstance(){
        return new ScheduledAppointmentGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }

    public ScheduledAppointment getScheduledAppointment(Prescription prescription){

        ScheduledAppointment ret=new ScheduledAppointment();

        ret.setNote(randomString.getString(10));
        ret.setPrescription(prescription);
        ret.setPatientAdmission(randomTimestamp.getFromRandom());
        ret.setLbzScheduler("mojLbz");
        ret.setAdmissionStatus(AdmissionStatus.ZAKAZAN);
        ret.setId(null);

        return ret;
    }

}
