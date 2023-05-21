package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescription;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescriptionAnalysis.PrescriptionAnalysisDtoGenerator;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionCreateDtoGenerator{

    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;

    @Autowired
    private final PrescriptionAnalysisDtoGenerator prescriptionAnalysisDtoGenerator;


    public static PrescriptionCreateDtoGenerator getInstance(){
        return new PrescriptionCreateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance(),
                PrescriptionAnalysisDtoGenerator.getInstance());
    }

    public PrescriptionCreateDto getPrescriptionCreateDto(AnalysisParameterRepository analysisParameterRepository){

        PrescriptionCreateDto ret=new PrescriptionCreateDto();

        ret.setStatus(PrescriptionStatus.values()[randomLong.getLong(new Long(PrescriptionStatus.values().length) ).intValue()]);
        ret.setType(PrescriptionType.LABORATORIJA);
        ret.setLbp(randomLBP.getFromRandom());
        ret.setCreationDateTime(new Timestamp(1000*(randomTimestamp.getFromRandom().getTime()/1000)));
        ret.setDepartmentToId(randomLong.getLong(4L));
        ret.setDoctorLbz(randomString.getString(10));
        ret.setComment(randomString.getString(10));
        ret.setDepartmentFromId(randomLong.getLong(4L));

        List<PrescriptionAnalysisDto> list=new ArrayList<>();
        long prescriptionAnalysisCount= randomLong.getLong(5L)+1;
        HashSet<Long>mapa=new HashSet<>();
        for(int i=0;i<prescriptionAnalysisCount;i++) {
            PrescriptionAnalysisDto pom=prescriptionAnalysisDtoGenerator.getPrescriptionAnalysisDto(analysisParameterRepository);
            if(!mapa.contains(pom.getAnalysisId())) {
                list.add(pom);
                mapa.add(pom.getAnalysisId());
            }
        }
        ret.setPrescriptionAnalysisDtos(list);

        return ret;
    }

    public PrescriptionCreateDto getPrescriptionCreateDtoWithLBP(String lbp,AnalysisParameterRepository analysisParameterRepository) {
        PrescriptionCreateDto ret=getPrescriptionCreateDto(analysisParameterRepository);
        ret.setLbp(lbp);
        return ret;
    }

}
