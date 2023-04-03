package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface LabResultService {

    MessageDto updateResults(ResultUpdateDto resultUpdateDto);

}
