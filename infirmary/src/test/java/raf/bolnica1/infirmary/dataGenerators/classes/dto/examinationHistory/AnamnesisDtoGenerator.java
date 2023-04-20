package raf.bolnica1.infirmary.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.externalPatientService.medicalRecord.AnamnesisDto;

@Component
@AllArgsConstructor
public class AnamnesisDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;


    public static AnamnesisDtoGenerator getInstance(){
        return new AnamnesisDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance());
    }

    public AnamnesisDto getAnamnesisDto(){

        AnamnesisDto ret=new AnamnesisDto();

        ret.setMainProblems(randomString.getString(10));
        ret.setCurrDisease(randomString.getString(10));
        ret.setPersonalAnamnesis(randomString.getString(10));
        ret.setFamilyAnamnesis(randomString.getString(10));
        ret.setPatientOpinion(randomString.getString(10));

        return ret;
    }

}
