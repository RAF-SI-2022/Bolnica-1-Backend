package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.labAnalysis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;

@Component
@AllArgsConstructor
public class LabAnalysisDtoGenerator {

    private RandomString randomString;

    public LabAnalysisDto getLabAnalysisDto(){

        LabAnalysisDto ret=new LabAnalysisDto();

        ret.setAnalysisName(randomString.getString(10));
        ret.setAbbreviation(randomString.getString(4));

        return ret;
    }

}
