package raf.bolnica1.laboratory.services;

import raf.bolnica1.laboratory.dto.lab.parameterAnalysisResult.ResultUpdateDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface LabResultService {

    MessageDto updateResults(ResultUpdateDto resultUpdateDto);

    MessageDto commitResults(Long workOrderId);
}
