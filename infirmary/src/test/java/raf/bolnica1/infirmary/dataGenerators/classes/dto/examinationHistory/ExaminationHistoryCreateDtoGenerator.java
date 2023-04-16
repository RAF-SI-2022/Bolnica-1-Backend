package raf.bolnica1.infirmary.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dataGenerators.primitives.*;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.dto.patientState.PatientStateCreateDto;

@Component
@AllArgsConstructor
public class ExaminationHistoryCreateDtoGenerator {
    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomDate randomDate;
    private final RandomDouble randomDouble;
    private final RandomTime randomTime;
    private final AnamnesisDtoGenerator anamnesisDtoGenerator;
    private final DiagnosisCodeDtoGenerator diagnosisCodeDtoGenerator;



    public static ExaminationHistoryCreateDtoGenerator getInstance(){
        return new ExaminationHistoryCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomJMBG.getInstance(),RandomLong.getInstance(),RandomDate.getInstance(),RandomDouble.getInstance(),
                RandomTime.getInstance(),AnamnesisDtoGenerator.getInstance(),DiagnosisCodeDtoGenerator.getInstance());
    }

    public ExaminationHistoryCreateDto getExaminationHistoryCreateDto(String lbp){

        if(lbp==null)
            throw new RuntimeException();

        ExaminationHistoryCreateDto ret=new ExaminationHistoryCreateDto();

        ret.setLbp(lbp);
        ret.setExamDate(randomDate.getFromRandom());
        ret.setLbz(randomString.getString(10));
        ret.setConfidential(false);
        ret.setObjectiveFinding(randomString.getString(20));
        ret.setAdvice(randomString.getString(20));
        ret.setTherapy(randomString.getString(10));
        ret.setAnamnesisDto(anamnesisDtoGenerator.getAnamnesisDto());
        ret.setDiagnosisCodeDto(diagnosisCodeDtoGenerator.getDiagnosisCodeDto());


        return ret;
    }
}
