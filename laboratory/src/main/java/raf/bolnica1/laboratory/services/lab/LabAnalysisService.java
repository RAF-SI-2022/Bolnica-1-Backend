package raf.bolnica1.laboratory.services.lab;

import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;

public interface LabAnalysisService {

    LabAnalysisDto createLabAnalysis(Object dto);

    LabAnalysisDto updateLabAnalysis(Object dto);

    Object deleteLabAnalysis(Long id);

    LabAnalysisDto getLabAnalysis(Long id);

}
