package raf.bolnica1.laboratory.dataGenerators.classes.dto.result;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.dataGenerators.primitives.RandomTimestamp;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;

@AllArgsConstructor
@Component
public class ResultUpdateDtoGenerator {

    private RandomString randomString;
    private RandomTimestamp randomTimestamp;

    public ResultUpdateDto getResultUpdateDto(Long labWorkOrderId,Long analysisParameterId){

        ResultUpdateDto ret=new ResultUpdateDto();

        ret.setResult(randomString.getString(10));
        ret.setDateTime(randomTimestamp.getFromRandom());
        ret.setBiochemistLbz(randomString.getString(10));
        ret.setLabWorkOrderId(labWorkOrderId);
        ret.setAnalysisParameterId(analysisParameterId);

        return ret;
    }

}
