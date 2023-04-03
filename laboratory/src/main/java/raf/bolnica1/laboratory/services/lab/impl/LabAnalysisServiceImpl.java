package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.LabAnalysisMapper;
import raf.bolnica1.laboratory.repository.LabAnalysisRepository;
import raf.bolnica1.laboratory.services.lab.LabAnalysisService;

@Service
@AllArgsConstructor
public class LabAnalysisServiceImpl implements LabAnalysisService {

    private final LabAnalysisRepository labAnalysisRepository;
    private final LabAnalysisMapper labAnalysisMapper;

    @Override
    public LabAnalysisDto createLabAnalysis(LabAnalysisDto labAnalysisDto) {

        LabAnalysis labAnalysis=new LabAnalysis();
        labAnalysis.setAnalysisName(labAnalysisDto.getAnalysisName());
        labAnalysis.setAbbreviation(labAnalysisDto.getAbbreviation());

        labAnalysis=labAnalysisRepository.save(labAnalysis);

        return labAnalysisMapper.toDto(labAnalysis);
    }

    @Override
    public LabAnalysisDto updateLabAnalysis(LabAnalysisDto labAnalysisDto) {

        LabAnalysis labAnalysis= labAnalysisRepository.findLabAnalysisById(labAnalysisDto.getId());

        labAnalysis.setAbbreviation(labAnalysisDto.getAbbreviation());
        labAnalysis.setAnalysisName(labAnalysisDto.getAnalysisName());

        labAnalysis=labAnalysisRepository.save(labAnalysis);

        return labAnalysisMapper.toDto(labAnalysis);
    }

    @Override
    public MessageDto deleteLabAnalysis(Long id) {
        labAnalysisRepository.deleteById(id);
        return new MessageDto("LabAnalysis with ID "+id.toString()+" deleted");
    }

    @Override
    public LabAnalysisDto getLabAnalysis(Long id) {
        return labAnalysisMapper.toDto(labAnalysisRepository.findLabAnalysisById(id));
    }
}
