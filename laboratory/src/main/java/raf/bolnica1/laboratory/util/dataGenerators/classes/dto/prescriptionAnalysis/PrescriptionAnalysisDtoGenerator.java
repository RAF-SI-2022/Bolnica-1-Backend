package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.prescriptionAnalysis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.dto.prescription.PrescriptionAnalysisDto;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PrescriptionAnalysisDtoGenerator {
    private final RandomString randomString;
    private final RandomNames randomNames;
    private final RandomSurnames randomSurnames;
    private final RandomTimestamp randomTimestamp;
    private final RandomJMBG randomJMBG;
    private final RandomLong randomLong;
    private final RandomLBP randomLBP;



    public static PrescriptionAnalysisDtoGenerator getInstance(){
        return new PrescriptionAnalysisDtoGenerator(RandomString.getInstance(),RandomNames.getInstance(),RandomSurnames.getInstance(),
                RandomTimestamp.getInstance(),RandomJMBG.getInstance(),RandomLong.getInstance(),RandomLBP.getInstance());
    }

    public PrescriptionAnalysisDto getPrescriptionAnalysisDto(AnalysisParameterRepository analysisParameterRepository){

        List<AnalysisParameter> analysisParameters=analysisParameterRepository.findAll();

        PrescriptionAnalysisDto ret=new PrescriptionAnalysisDto();

        ret.setAnalysisId(analysisParameters.get(randomLong.getLong((long)analysisParameters.size()).intValue())
                .getLabAnalysis().getId());

        List<Long> parameters=new ArrayList<>();
        boolean flag=false;
        for(AnalysisParameter analysisParameter:analysisParameters)
            if(analysisParameter.getLabAnalysis().getId().equals(ret.getAnalysisId())){
                if(flag==false){
                    flag=true;
                    parameters.add(analysisParameter.getParameter().getId());
                    continue;
                }
                if(randomLong.getLong(2L)==1)
                    parameters.add(analysisParameter.getParameter().getId());
            }

        ret.setParametersIds(parameters);

        return ret;
    }


}
