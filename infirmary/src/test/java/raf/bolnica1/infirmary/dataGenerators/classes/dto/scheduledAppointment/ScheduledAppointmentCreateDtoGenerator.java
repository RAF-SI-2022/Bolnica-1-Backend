package raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;

import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class ScheduledAppointmentCreateDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public static ScheduledAppointmentCreateDtoGenerator getInstance(){
        return new ScheduledAppointmentCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }

    public ScheduledAppointmentCreateDto getScheduledAppointmentCreateDto(Long prescriptionId){

        ScheduledAppointmentCreateDto ret=new ScheduledAppointmentCreateDto();

        ret.setNote(randomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(new Timestamp(1000*(randomTimestamp.getFromRandom().getTime()/1000)));

        return ret;
    }

}
