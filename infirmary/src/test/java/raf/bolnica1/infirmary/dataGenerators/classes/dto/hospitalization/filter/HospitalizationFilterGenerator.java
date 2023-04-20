package raf.bolnica1.infirmary.dataGenerators.classes.dto.hospitalization.filter;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;

@AllArgsConstructor
@Component
public class HospitalizationFilterGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final RandomLBP randomLBP;


    public static HospitalizationFilterGenerator getInstance(){
        return new HospitalizationFilterGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance(),RandomLBP.getInstance());
    }

    public HospitalizationFilter getRandomFilter(){
        HospitalizationFilter filter=new HospitalizationFilter();

        filter.setName(randomNames.getFromRandom());
        filter.setJmbg(randomJMBG.getFromRandom());
        filter.setLbp(randomLBP.getFromRandom());
        filter.setHospitalRoomId(randomLong.getLong(15L));
        filter.setSurname(randomSurnames.getFromRandom());
        filter.setDepartmentId(randomLong.getLong(15L));
        filter.setStartDate(randomDate.getFromRandom());
        filter.setEndDate(randomDate.getFromRandom());

        if(randomLong.getLong(2L)==0L)filter.setName(null);
        if(randomLong.getLong(2L)==0L)filter.setJmbg(null);
        if(randomLong.getLong(2L)==0L)filter.setLbp(null);
        if(randomLong.getLong(2L)==0L)filter.setHospitalRoomId(null);
        if(randomLong.getLong(2L)==0L)filter.setSurname(null);
        if(randomLong.getLong(2L)==0L)filter.setDepartmentId(null);
        if(randomLong.getLong(2L)==0L)filter.setStartDate(null);
        if(randomLong.getLong(2L)==0L)filter.setEndDate(null);

        return filter;
    }

}
