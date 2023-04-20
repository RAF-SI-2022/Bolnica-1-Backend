package raf.bolnica1.infirmary.dataGenerators.classes.dto.visit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;

@Component
@AllArgsConstructor
public class VisitCreateDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;


    public static VisitCreateDtoGenerator getInstance(){
        return new VisitCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance());
    }

    public VisitCreateDto getVisitCreateDto(Long hospitalizationId){

        VisitCreateDto ret=new VisitCreateDto();

        ret.setNote(randomString.getString(20));
        ret.setVisitTime(randomTimestamp.getFromRandom());
        ret.setHospitalizationId(hospitalizationId);
        ret.setVisitorJmbg(randomJMBG.getFromRandom());
        ret.setVisitorName(randomNames.getFromRandom());
        ret.setVisitorSurname(randomSurnames.getFromRandom());

        return ret;
    }

}
