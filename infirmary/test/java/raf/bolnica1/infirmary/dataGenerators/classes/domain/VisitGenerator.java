package raf.bolnica1.infirmary.dataGenerators.classes.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.classes.dto.visit.VisitCreateDtoGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.Visit;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;

@Component
@AllArgsConstructor
public class VisitGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;


    public static VisitGenerator getInstance(){
        return new VisitGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance());
    }

    public Visit getVisit(Hospitalization hospitalization){

        Visit ret=new Visit();

        ret.setNote(randomString.getString(20));
        ret.setVisitTime(randomTimestamp.getFromRandom());
        ret.setHospitalization(hospitalization);
        ret.setVisitorJmbg(randomJMBG.getFromRandom());
        ret.setVisitorName(randomNames.getFromRandom());
        ret.setVisitorSurname(randomSurnames.getFromRandom());
        ret.setLbzRegister(randomString.getString(10));

        return ret;
    }
}
