package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

public interface ParameterService {

    ParameterDto createParameter(ParameterDto parameterDto);

    ParameterDto updateParameter(ParameterDto parameterDto);

    MessageDto deleteParameter(Long id);

    ParameterDto getParameter(Long id);

}
