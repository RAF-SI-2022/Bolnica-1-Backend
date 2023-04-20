package raf.bolnica1.laboratory.dataGenerators.classes.dto.scheduledLabExamination;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomDate;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomLBP;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomString;

@AllArgsConstructor
@Component
public class ScheduledLabExaminationCreateGenerator {

    private RandomLBP randomLBP;
    private RandomString randomString;
    private RandomDate randomDate;


    public ScheduledLabExaminationCreate getScheduledLabExamination(){
        ScheduledLabExaminationCreate ret=new ScheduledLabExaminationCreate();

        ret.setNote(randomString.getString(10));
        ret.setScheduledDate(randomDate.getFromRandom());
        ret.setLbp(randomLBP.getFromRandom());

        return ret;
    }

}
