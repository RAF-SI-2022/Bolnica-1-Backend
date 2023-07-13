package raf.bolnica1.laboratory.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.LabAnalysisMapper;
import raf.bolnica1.laboratory.repository.LabAnalysisRepository;
import raf.bolnica1.laboratory.services.LabAnalysisService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LabAnalysisServiceImpl implements LabAnalysisService {

    private final LabAnalysisRepository labAnalysisRepository;
    private final LabAnalysisMapper labAnalysisMapper;

    @Override
    @CacheEvict(value = "labAnals", allEntries = true)
    public LabAnalysisDto createLabAnalysis(LabAnalysisDto labAnalysisDto) {

        LabAnalysis labAnalysis = labAnalysisMapper.toEntity(labAnalysisDto);
        labAnalysis.setId(null);

        labAnalysis = labAnalysisRepository.save(labAnalysis);

        return labAnalysisMapper.toDto(labAnalysis);
    }

    @Override
    @CachePut(value = "labAnal", key = "#labAnalysisDto.id")
    @CacheEvict(value = "labAnals", allEntries = true)
    public LabAnalysisDto updateLabAnalysis(LabAnalysisDto labAnalysisDto) {

        /// ako ne postoji sa tim ID onda ne moze ni da update-uje
        if (labAnalysisRepository.findLabAnalysisById(labAnalysisDto.getId()) == null)
            throw new RuntimeException();

        LabAnalysis labAnalysis = labAnalysisMapper.toEntity(labAnalysisDto);

        labAnalysis = labAnalysisRepository.save(labAnalysis);

        return labAnalysisMapper.toDto(labAnalysis);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "labAnal", key = "#id"),
            @CacheEvict(value = "labAnals", allEntries = true)
    })
    public MessageDto deleteLabAnalysis(Long id) {
        labAnalysisRepository.deleteById(id);
        return new MessageDto("LabAnalysis with ID " + id.toString() + " deleted");
    }

    @Override
    @Cacheable(value = "labAnal", key = "#id")
    public LabAnalysisDto getLabAnalysis(Long id) {
        return labAnalysisMapper.toDto(labAnalysisRepository.findLabAnalysisById(id));
    }

    @Override
    @Cacheable(value = "labAnals")
    public List<LabAnalysisDto> getAllLabAnalysis(boolean covid) {
        List<LabAnalysis> labAnalyses = labAnalysisRepository.findAll();
        List<LabAnalysis>ret=new ArrayList<>();
        for(LabAnalysis la:labAnalyses)
            if(covid==la.isCovid())
                ret.add(la);
        return labAnalysisMapper.toDto(ret);
    }
}
