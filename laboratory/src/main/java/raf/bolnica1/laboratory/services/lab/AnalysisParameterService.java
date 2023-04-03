package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface AnalysisParameterService {

    AnalysisParameterDto createAnalysisParameter(AnalysisParameterDto analysisParameterDto);

    AnalysisParameterDto updateAnalysisParameter(AnalysisParameterDto analysisParameterDto);

    MessageDto deleteAnalysisParameter(Long id);

    AnalysisParameterDto getAnalysisParameter(Long id);

}
