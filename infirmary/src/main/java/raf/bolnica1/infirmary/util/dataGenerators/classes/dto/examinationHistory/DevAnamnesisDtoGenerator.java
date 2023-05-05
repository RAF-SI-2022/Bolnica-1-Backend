package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.AnamnesisDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevAnamnesisDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;


    public static DevAnamnesisDtoGenerator getInstance(){
        return new DevAnamnesisDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(),
                DevRandomTime.getInstance());
    }

    public AnamnesisDto getAnamnesisDto(){

        AnamnesisDto ret=new AnamnesisDto();

        ret.setMainProblems(devRandomString.getString(10));
        ret.setCurrDisease(devRandomString.getString(10));
        ret.setPersonalAnamnesis(devRandomString.getString(10));
        ret.setFamilyAnamnesis(devRandomString.getString(10));
        ret.setPatientOpinion(devRandomString.getString(10));

        return ret;
    }

}
