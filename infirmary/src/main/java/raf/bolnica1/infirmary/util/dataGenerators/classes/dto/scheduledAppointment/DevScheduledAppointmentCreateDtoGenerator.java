package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.scheduledAppointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.scheduledAppointment.ScheduledAppointmentCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevScheduledAppointmentCreateDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomTimestamp devRandomTimestamp;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomLBP devRandomLBP;


    public static DevScheduledAppointmentCreateDtoGenerator getInstance(){
        return new DevScheduledAppointmentCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomTimestamp.getInstance(), DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomLBP.getInstance());
    }

    public ScheduledAppointmentCreateDto getScheduledAppointmentCreateDto(Long prescriptionId){

        ScheduledAppointmentCreateDto ret=new ScheduledAppointmentCreateDto();

        ret.setNote(devRandomString.getString(10));
        ret.setPrescriptionId(prescriptionId);
        ret.setPatientAdmission(devRandomTimestamp.getFromRandom());

        return ret;
    }

}
