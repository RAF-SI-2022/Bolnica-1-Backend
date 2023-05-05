package raf.bolnica1.infirmary.dataGenerators.classes.dto.dischargeList.filter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomDate;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;

@AllArgsConstructor
@Component
public class DischargeListFilterGenerator {

    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomLBP randomLBP;

    public static DischargeListFilterGenerator getInstance() {
        return new DischargeListFilterGenerator(RandomLong.getInstance(), RandomDate.getInstance(), RandomLBP.getInstance());
    }

    public DischargeListFilter getRandomFilter(){
        DischargeListFilter filter=new DischargeListFilter();

        filter.setLbp(randomLBP.getFromRandom());
        filter.setHospitalizationId(randomLong.getLong(15L));
        filter.setStartDate(randomDate.getFromRandom());
        filter.setEndDate(randomDate.getFromRandom());

        if(randomLong.getLong(2L)==0L)filter.setLbp(null);
        if(randomLong.getLong(2L)==0L)filter.setHospitalizationId(null);
        if(randomLong.getLong(2L)==0L)filter.setStartDate(null);
        if(randomLong.getLong(2L)==0L)filter.setEndDate(null);

        return filter;
    }

}