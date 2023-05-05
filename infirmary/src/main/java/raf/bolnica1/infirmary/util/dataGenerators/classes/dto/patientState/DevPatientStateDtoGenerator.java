package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.patientState;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@AllArgsConstructor
@Component
public class DevPatientStateDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;



    public static DevPatientStateDtoGenerator getInstance(){
        return new DevPatientStateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(),
                DevRandomTime.getInstance());
    }

    public PatientStateCreateDto getPatientStateCreateDto(Long hospitalizationId){

        if(hospitalizationId==null)
            throw new RuntimeException();

        PatientStateCreateDto ret=new PatientStateCreateDto();

        ret.setDescription(devRandomString.getString(20));
        ret.setDateExamState(devRandomDate.getFromRandom());
        ret.setPulse(devRandomLong.getLong(100L).intValue());
        ret.setHospitalizationId(hospitalizationId);
        ret.setTemperature(devRandomDouble.getDouble(50.0).floatValue());
        ret.setTherapy(devRandomString.getString(20));
        ret.setTimeExamState(devRandomTime.getTimeFromRandom());
        ret.setSystolicPressure(devRandomLong.getLong(200L).intValue());
        ret.setDiastolicPressure(devRandomLong.getLong(200L).intValue());

        return ret;
    }

}
