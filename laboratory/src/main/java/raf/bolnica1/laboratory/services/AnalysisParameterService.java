package raf.bolnica1.laboratory.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface AnalysisParameterService {

    AnalysisParameterDto createAnalysisParameter(AnalysisParameterDto analysisParameterDto);

    AnalysisParameterDto updateAnalysisParameter(AnalysisParameterDto analysisParameterDto);

    MessageDto deleteAnalysisParameter(Long id);

    AnalysisParameterDto getAnalysisParameter(Long id);

    Page<ParameterDto> getParametersByAnalysisId(Long id,int page,int size);

}
