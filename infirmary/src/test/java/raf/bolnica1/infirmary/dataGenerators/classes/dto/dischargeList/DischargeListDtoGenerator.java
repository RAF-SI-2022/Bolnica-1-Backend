package raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;

@AllArgsConstructor
@Component
public class DischargeListDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final RandomTimestamp randomTimestamp;


    public static DischargeListDtoGenerator getInstance(){
        return new DischargeListDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance(),RandomTimestamp.getInstance());
    }

    public DischargeListDto getDischargeListDto(Long hospitalizationId){

        DischargeListDto ret=new DischargeListDto();

        ret.setCourseOfDisease(randomString.getString(10));
        ret.setTherapy(randomString.getString(10));
        ret.setId(null);
        ret.setHospitalizationId(hospitalizationId);
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
