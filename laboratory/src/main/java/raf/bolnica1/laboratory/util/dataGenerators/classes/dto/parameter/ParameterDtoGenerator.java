package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.parameter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.constants.ParameterValueType;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.*;

@Component
@AllArgsConstructor
public class ParameterDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;


    public static ParameterDtoGenerator getInstance(){
        return new ParameterDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }


    public ParameterDto getParameterDto(){

        ParameterDto ret=new ParameterDto();

        ret.setId(null);
        ret.setType(ParameterValueType.values()[ randomLong.getLong( (long)ParameterValueType.values().length ).intValue() ] );
        ret.setParameterName(randomString.getString(10));
        ret.setUpperLimit(randomLong.getLong(100L).doubleValue());
        ret.setLowerLimit(randomLong.getLong(100L).doubleValue());
        ret.setUnitOfMeasure(randomString.getString(10));

        return ret;
    }

}
