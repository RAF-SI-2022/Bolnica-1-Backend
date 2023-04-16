package raf.bolnica1.infirmary.dataGenerators.classes.dto.prescription.filter;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;
import raf.bolnica1.infirmary.domain.constants.PrescriptionStatus;

@AllArgsConstructor
@Component
public class PrescriptionFilterGenerator {

    private final RandomLBP randomLBP;
    private final RandomLong randomLong;


    public static PrescriptionFilterGenerator getInstance(){
        return new PrescriptionFilterGenerator(RandomLBP.getInstance(),RandomLong.getInstance());
    }

    public PrescriptionFilter getRandomFilter(){

        PrescriptionFilter f=new PrescriptionFilter();

        f.setLbp(randomLBP.getFromRandom());
        f.setDepartmentId(randomLong.getLong(10L));
        f.setPrescriptionStatus(PrescriptionStatus.values()[ randomLong.getLong(new Long(PrescriptionStatus.values().length) ).intValue() ] );

        if(randomLong.getLong(2L)==0L)f.setLbp(null);
        if(randomLong.getLong(2L)==0L)f.setDepartmentId(null);
        if(randomLong.getLong(2L)==0L)f.setPrescriptionStatus(null);

        return f;
    }

}
