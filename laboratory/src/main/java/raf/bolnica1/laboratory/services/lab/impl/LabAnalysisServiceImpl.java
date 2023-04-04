package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.LabAnalysisMapper;
import raf.bolnica1.laboratory.repository.LabAnalysisRepository;
import raf.bolnica1.laboratory.services.lab.LabAnalysisService;

import java.util.List;

@Service
@AllArgsConstructor
public class LabAnalysisServiceImpl implements LabAnalysisService {

    private final LabAnalysisRepository labAnalysisRepository;
    private final LabAnalysisMapper labAnalysisMapper;

    @Override
    public LabAnalysisDto createLabAnalysis(LabAnalysisDto labAnalysisDto) {

        LabAnalysis labAnalysis=labAnalysisMapper.toEntity(labAnalysisDto);
        labAnalysis.setId(null);

        labAnalysis=labAnalysisRepository.save(labAnalysis);

        return labAnalysisMapper.toDto(labAnalysis);
    }

    @Override
    public LabAnalysisDto updateLabAnalysis(LabAnalysisDto labAnalysisDto) {

        /// ako ne postoji sa tim ID onda ne moze ni da update-uje
        if(labAnalysisRepository.findLabAnalysisById(labAnalysisDto.getId())==null)
            throw new RuntimeException();

        LabAnalysis labAnalysis=labAnalysisMapper.toEntity(labAnalysisDto);

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

    @Override
    public List<LabAnalysisDto> getAllLabAnalysis() {
        List<LabAnalysis> labAnalyses=labAnalysisRepository.findAll();
        return labAnalysisMapper.toDto(labAnalyses);
    }
}
