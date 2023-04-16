package raf.bolnica1.infirmary.dataGenerators.classes.dto.scheduledAppointment.filter;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomDate;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.constants.AdmissionStatus;

@AllArgsConstructor
@Component
public class ScheduledAppointmentFilterGenerator {

    /// PRIMITIVE GENERATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private RandomDate randomDate;
    @Autowired
    private RandomLBP randomLBP;


    public static ScheduledAppointmentFilterGenerator getInstance(){
        return new ScheduledAppointmentFilterGenerator(RandomLong.getInstance(),RandomDate.getInstance(),RandomLBP.getInstance());
    }

    public ScheduledAppointmentFilter getRandomFilter(){

        ScheduledAppointmentFilter f=new ScheduledAppointmentFilter();

        f.setLbp(randomLBP.getFromRandom());
        f.setDepartmentId(randomLong.getLong(5L));
        f.setAdmissionStatus(AdmissionStatus.values()[ randomLong.getLong( new Long(AdmissionStatus.values().length) ).intValue() ]);
        f.setStartDate(randomDate.getFromRandom());
        f.setEndDate(randomDate.getFromRandom());

        if(randomLong.getLong(2L)==0L)f.setLbp(null);
        if(randomLong.getLong(2L)==0L)f.setDepartmentId(null);
        if(randomLong.getLong(2L)==0L)f.setAdmissionStatus(null);
        if(randomLong.getLong(2L)==0L)f.setStartDate(null);
        if(randomLong.getLong(2L)==0L)f.setEndDate(null);

        return f;
    }

}
