package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.visit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.visit.VisitCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevVisitCreateDtoGenerator {

    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomTimestamp devRandomTimestamp;
    private final DevRandomJMBG devRandomJMBG;


    public static DevVisitCreateDtoGenerator getInstance(){
        return new DevVisitCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomTimestamp.getInstance(), DevRandomJMBG.getInstance());
    }

    public VisitCreateDto getVisitCreateDto(Long hospitalizationId){

        VisitCreateDto ret=new VisitCreateDto();

        ret.setNote(devRandomString.getString(20));
        ret.setVisitTime(devRandomTimestamp.getFromRandom());
        ret.setHospitalizationId(hospitalizationId);
        ret.setVisitorJmbg(devRandomJMBG.getFromRandom());
        ret.setVisitorName(devRandomNames.getFromRandom());
        ret.setVisitorSurname(devRandomSurnames.getFromRandom());

        return ret;
    }

}
