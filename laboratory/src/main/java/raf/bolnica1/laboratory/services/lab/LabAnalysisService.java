package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;

import java.util.List;

public interface LabAnalysisService {

    LabAnalysisDto createLabAnalysis(LabAnalysisDto labAnalysisDto);

    LabAnalysisDto updateLabAnalysis(LabAnalysisDto labAnalysisDto);

    MessageDto deleteLabAnalysis(Long id);

    LabAnalysisDto getLabAnalysis(Long id);

    List<LabAnalysisDto> getAllLabAnalysis();

}
