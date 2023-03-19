package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.repository.LabAnalysisRepository;
import raf.bolnica1.laboratory.services.lab.LabAnalysisService;

@Service
@AllArgsConstructor
public class LabAnalysisServiceImpl implements LabAnalysisService {

    private final LabAnalysisRepository labAnalysisRepository;

    @Override
    public LabAnalysisDto createLabAnalysis(Object dto) {
        return null;
    }

    @Override
    public LabAnalysisDto updateLabAnalysis(Object dto) {
        return null;
    }

    @Override
    public Object deleteLabAnalysis(Long id) {
        return null;
    }

    @Override
    public LabAnalysisDto getLabAnalysis(Long id) {
        return null;
    }
}
