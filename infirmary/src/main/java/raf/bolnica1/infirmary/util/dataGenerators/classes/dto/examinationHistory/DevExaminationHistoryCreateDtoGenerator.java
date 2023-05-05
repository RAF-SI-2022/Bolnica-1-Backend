package raf.bolnica1.infirmary.util.dataGenerators.classes.dto.examinationHistory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.infirmary.dto.externalPatientService.ExaminationHistoryCreateDto;
import raf.bolnica1.infirmary.util.dataGenerators.primitives.*;


@Component
@AllArgsConstructor
public class DevExaminationHistoryCreateDtoGenerator {
    private final DevRandomString devRandomString;
    private final DevRandomNames devRandomNames;
    private final DevRandomSurnames devRandomSurnames;
    private final DevRandomJMBG devRandomJMBG;
    private final DevRandomLong devRandomLong;
    private final DevRandomDate devRandomDate;
    private final DevRandomDouble devRandomDouble;
    private final DevRandomTime devRandomTime;
    private final DevAnamnesisDtoGenerator devAnamnesisDtoGenerator;
    private final DevDiagnosisCodeDtoGenerator devDiagnosisCodeDtoGenerator;



    public static DevExaminationHistoryCreateDtoGenerator getInstance(){
        return new DevExaminationHistoryCreateDtoGenerator(DevRandomString.getInstance(), DevRandomNames.getInstance(), DevRandomSurnames.getInstance(),
                DevRandomJMBG.getInstance(), DevRandomLong.getInstance(), DevRandomDate.getInstance(), DevRandomDouble.getInstance(),
                DevRandomTime.getInstance(), DevAnamnesisDtoGenerator.getInstance(), DevDiagnosisCodeDtoGenerator.getInstance());
    }

    public ExaminationHistoryCreateDto getExaminationHistoryCreateDto(String lbp){

        if(lbp==null)
            throw new RuntimeException();

        ExaminationHistoryCreateDto ret=new ExaminationHistoryCreateDto();

        ret.setLbp(lbp);
        ret.setExamDate(devRandomDate.getFromRandom());
        ret.setLbz(devRandomString.getString(10));
        ret.setConfidential(false);
        ret.setObjectiveFinding(devRandomString.getString(20));
        ret.setAdvice(devRandomString.getString(20));
        ret.setTherapy(devRandomString.getString(10));
        ret.setAnamnesisDto(devAnamnesisDtoGenerator.getAnamnesisDto());
        ret.setDiagnosisCodeDto(devDiagnosisCodeDtoGenerator.getDiagnosisCodeDto());


        return ret;
    }
}
