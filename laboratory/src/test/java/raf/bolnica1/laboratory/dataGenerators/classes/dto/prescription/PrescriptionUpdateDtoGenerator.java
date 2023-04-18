package raf.bolnica1.laboratory.dataGenerators.classes.dto.prescription;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.dataGenerators.classes.dto.prescriptionAnalysis.PrescriptionAnalysisDtoGenerator;
import raf.bolnica1.laboratory.dataGenerators.primitives.*;
import raf.bolnica1.laboratory.domain.constants.PrescriptionStatus;
import raf.bolnica1.laboratory.domain.constants.PrescriptionType;
import raf.bolnica1.laboratory.domain.lab.Prescription;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionCreateDto;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionUpdateDto;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.repository.PrescriptionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionUpdateDtoGenerator {
    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;

    @Autowired
    private final PrescriptionAnalysisDtoGenerator prescriptionAnalysisDtoGenerator;


    public static PrescriptionUpdateDtoGenerator getInstance(){
        return new PrescriptionUpdateDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance(),
                PrescriptionAnalysisDtoGenerator.getInstance());
    }

    public PrescriptionUpdateDto getPrescriptionUpdateDto(PrescriptionRepository prescriptionRepository,
                                                          AnalysisParameterRepository analysisParameterRepository){

        PrescriptionUpdateDto ret=new PrescriptionUpdateDto();

        ret.setCreationDateTime(randomTimestamp.getFromRandom());
        ret.setDepartmentToId(randomLong.getLong(4L));
        ret.setComment(randomString.getString(10));
        ret.setDepartmentFromId(randomLong.getLong(4L));

        List<PrescriptionAnalysisDto> list=new ArrayList<>();
        long prescriptionAnalysisCount= randomLong.getLong(5L)+1;
        HashSet<Long> mapa=new HashSet<>();
        for(int i=0;i<prescriptionAnalysisCount;i++) {
            PrescriptionAnalysisDto pom=prescriptionAnalysisDtoGenerator.getPrescriptionAnalysisDto(analysisParameterRepository);
            if(!mapa.contains(pom.getAnalysisId())) {
                list.add(pom);
                mapa.add(pom.getAnalysisId());
            }
        }
        ret.setPrescriptionAnalysisDtos(list);

        List<Prescription> pom=prescriptionRepository.findAll();
        ret.setId(pom.get( randomLong.getLong((long)pom.size() ).intValue() ).getId());

        return ret;
    }
}
