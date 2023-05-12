package raf.bolnica1.infirmary.dataGenerators.classes.dto.patientState;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.classes.domain.HospitalizationGenerator;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.domain.Hospitalization;
import raf.bolnica1.infirmary.domain.PatientState;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateDto;

@AllArgsConstructor
@Component
public class PatientStateDtoGenerator {

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;



    public static PatientStateDtoGenerator getInstance(){
        return new PatientStateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance());
    }

    public PatientStateCreateDto getPatientStateCreateDto(Long hospitalizationId){

        if(hospitalizationId==null)
            throw new RuntimeException();

        PatientStateCreateDto ret=new PatientStateCreateDto();

        ret.setDescription(randomString.getString(20));
        ret.setDateExamState(randomDate.getFromRandom());
        ret.setPulse(randomLong.getLong(100L).intValue());
        ret.setHospitalizationId(hospitalizationId);
        String tmp = String.format("%.5f", randomDouble.getDouble(50.0).floatValue());
        ret.setTemperature(Float.parseFloat(tmp));
        ret.setTherapy(randomString.getString(20));
        ret.setTimeExamState(randomTime.getTimeFromRandom());
        ret.setSystolicPressure(randomLong.getLong(200L).intValue());
        ret.setDiastolicPressure(randomLong.getLong(200L).intValue());

        return ret;
    }

}
