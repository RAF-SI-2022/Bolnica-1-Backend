package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.scheduledLabExamination.filter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomDate;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomLong;

@AllArgsConstructor
@Component
public class ScheduledLabExaminationDtoFilterGenerator {

    private RandomDate randomDate;
    private RandomLBP randomLBP;
    private RandomLong randomLong;

    public ScheduledLabExaminationDtoFilter getRandomFilter(){

        ScheduledLabExaminationDtoFilter f=new ScheduledLabExaminationDtoFilter();

        f.setStartDate(randomDate.getFromRandom());
        f.setEndDate(randomDate.getFromRandom());
        f.setLbp(randomLBP.getFromRandom());
        f.setDepartmentId(randomLong.getLong(10L));

        if(randomLong.getLong(2L)==0)f.setStartDate(null);
        if(randomLong.getLong(2L)==0)f.setEndDate(null);
        if(randomLong.getLong(2L)==0)f.setLbp(null);
        if(randomLong.getLong(2L)==0)f.setDepartmentId(null);

        return f;
    }

}
