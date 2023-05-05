package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.dischargeList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.dischargeList.DischargeListDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@AllArgsConstructor
@Component
public class DevDischargeListDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;
    private final DevRandomTimestamp devRandomTimestamp;


    public static DevDischargeListDtoGenerator getInstance(){
        return new DevDischargeListDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(),
                DevRandomTime.getInstance(), DevRandomTimestamp.getInstance());
    }

    public DischargeListDto getDischargeListDto(Long hospitalizationId){

        DischargeListDto ret=new DischargeListDto();

        ret.setCourseOfDisease(devRandomString.getString(10));
        ret.setTherapy(devRandomString.getString(10));
        ret.setId(null);
        ret.setHospitalizationId(hospitalizationId);
        ret.setSummary(devRandomString.getString(10));
        ret.setLbzDepartment(devRandomString.getString(10));
        ret.setAnalysis(devRandomString.getString(10));
        ret.setCreation(devRandomTimestamp.getFromRandom());
        ret.setAnamnesis(devRandomString.getString(10));
        ret.setFollowingDiagnosis(devRandomString.getString(10));
        ret.setLbzPrescribing(devRandomString.getString(10));

        return ret;
    }

}
