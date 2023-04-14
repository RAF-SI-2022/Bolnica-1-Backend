package raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;

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


    public ScheduledAppointmentCreateDto getScheduledAppointmentCreateDto(Long prescriptionId){

        ScheduledAppointmentCreateDto ret=new ScheduledAppointmentCreateDto();

        ret.setNote(randomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(randomTimestamp.getFromRandom());

        return ret;
    }

}
