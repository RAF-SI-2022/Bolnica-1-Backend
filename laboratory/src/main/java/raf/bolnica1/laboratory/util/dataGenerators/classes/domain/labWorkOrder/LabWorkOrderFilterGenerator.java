package raf.bolnica1.laboratory.util.dataGenerators.classes.domain.labWorkOrder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.constants.OrderStatus;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.*;

import java.util.ArrayList;

@AllArgsConstructor
@Component
public class LabWorkOrderFilterGenerator {

    private final RandomString randomString;
    private final RandomTimestamp randomTimestamp;
    private final RandomDate randomDate;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public static LabWorkOrderFilterGenerator getInstance(){
        return new LabWorkOrderFilterGenerator(RandomString.getInstance(),RandomTimestamp.getInstance(),
                RandomDate.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }

    public LabWorkOrderFilter getRandomFilter(){

        LabWorkOrderFilter f=new LabWorkOrderFilter();

        f.setStatus(new ArrayList<>());
        f.getStatus().add(OrderStatus.values()[ randomLong.getLong((long)OrderStatus.values().length).intValue() ]);
        f.setLbp(randomLBP.getFromRandom());
        f.setFromDate(randomDate.getFromRandom());
        f.setToDate(randomDate.getFromRandom());

        if(randomLong.getLong(2L)==0)f.setStatus(null);
        if(randomLong.getLong(2L)==0)f.setFromDate(null);
        if(randomLong.getLong(2L)==0)f.setToDate(null);
        if(randomLong.getLong(2L)==0)f.setLbp(null);

        return f;
    }

}
