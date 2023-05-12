package raf.bolnica1.laboratory.util.dataGenerators.classes.dto.result;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomString;
import raf.bolnica1.laboratory.util.dataGenerators.primitives.RandomTimestamp;
import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;

import java.sql.Timestamp;

@AllArgsConstructor
@Component
public class ResultUpdateDtoGenerator {

    private RandomString randomString;
    private RandomTimestamp randomTimestamp;

    public static ResultUpdateDtoGenerator getInstance(){
        return new ResultUpdateDtoGenerator(RandomString.getInstance(),RandomTimestamp.getInstance());
    }

    public ResultUpdateDto getResultUpdateDto(Long labWorkOrderId,Long analysisParameterId){

        ResultUpdateDto ret=new ResultUpdateDto();

        ret.setResult(randomString.getString(10));
        Long tmp = 1000*(randomTimestamp.getFromRandom().getTime()/1000);
        ret.setDateTime(new Timestamp(tmp));
        ret.setBiochemistLbz(randomString.getString(10));
        ret.setLabWorkOrderId(labWorkOrderId);
        ret.setAnalysisParameterId(analysisParameterId);

        return ret;
    }

}
