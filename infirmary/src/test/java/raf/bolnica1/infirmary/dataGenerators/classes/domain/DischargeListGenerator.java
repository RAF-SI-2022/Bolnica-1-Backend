package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList.DischargeListDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.DischargeList;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;

@Component
@AllArgsConstructor
public class DischargeListGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final RandomTimestamp randomTimestamp;


    public static DischargeListGenerator getInstance(){
        return new DischargeListGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance(),RandomTimestamp.getInstance());
    }

    public DischargeList getDischargeList(Hospitalization hospitalization){

        DischargeList ret=new DischargeList();

        ret.setCourseOfDisease(randomString.getString(10));
        ret.setTherapy(randomString.getString(10));
        ret.setId(null);
        ret.setHospitalization(hospitalization);
        ret.setSummary(randomString.getString(10));
        ret.setLbzDepartment(randomString.getString(10));
        ret.setAnalysis(randomString.getString(10));
        ret.setCreation(randomTimestamp.getFromRandom());
        ret.setAnamnesis(randomString.getString(10));
        ret.setFollowingDiagnosis(randomString.getString(10));
        ret.setLbzPrescribing(randomString.getString(10));

        return ret;
    }

}
