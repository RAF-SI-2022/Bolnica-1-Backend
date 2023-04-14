package raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.filter;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomDate;
import raf.bolnica1.infirmary.dataGenerators.primitives.RandomLong;

@AllArgsConstructor
@Component
public class VisitFilterGenerator {

    /// PRIMITIVE GENRATORS
    @Autowired
    private RandomLong randomLong;
    @Autowired
    private RandomDate randomDate;


    public VisitFilter getRandomFilter(){
        VisitFilter visitFilter=new VisitFilter();
        long maxIds=3;
        visitFilter.setHospitalRoomId(randomLong.getLong(maxIds));
        visitFilter.setDepartmentId(randomLong.getLong(maxIds));
        visitFilter.setHospitalizationId(randomLong.getLong(maxIds));
        visitFilter.setStartDate(randomDate.getFromRandom());
        visitFilter.setEndDate(randomDate.getFromRandom());

        if(randomLong.getLong(2L)==0L)visitFilter.setHospitalRoomId(null);
        if(randomLong.getLong(2L)==0L)visitFilter.setDepartmentId(null);
        if(randomLong.getLong(2L)==0L)visitFilter.setHospitalizationId(null);
        if(randomLong.getLong(2L)==0L)visitFilter.setStartDate(null);
        if(randomLong.getLong(2L)==0L)visitFilter.setEndDate(null);

        return visitFilter;
    }


}
