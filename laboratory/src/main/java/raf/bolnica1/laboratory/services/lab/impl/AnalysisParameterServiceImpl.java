package raf.bolnica1.laboratory.services.lab.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.AnalysisParameterMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.services.lab.AnalysisParameterService;


@Service
@AllArgsConstructor
public class AnalysisParameterServiceImpl implements AnalysisParameterService {

    private final AnalysisParameterRepository analysisParameterRepository;
    private final AnalysisParameterMapper analysisParameterMapper;

    @Override
    public AnalysisParameterDto createAnalysisParameter(AnalysisParameterDto analysisParameterDto) {

        AnalysisParameter analysisParameter=analysisParameterMapper.toEntity(analysisParameterDto);
        analysisParameter.setId(null);

        analysisParameter=analysisParameterRepository.save(analysisParameter);

        return  analysisParameterMapper.toDto(analysisParameter);
    }

    @Override
    public AnalysisParameterDto updateAnalysisParameter(AnalysisParameterDto analysisParameterDto) {

        if(analysisParameterRepository.findAnalysisParameterById(analysisParameterDto.getId())==null)
            throw new RuntimeException();

        AnalysisParameter analysisParameter=analysisParameterMapper.toEntity(analysisParameterDto);

        analysisParameter=analysisParameterRepository.save(analysisParameter);

        return analysisParameterMapper.toDto(analysisParameter);
    }

    @Override
    public MessageDto deleteAnalysisParameter(Long id) {
        analysisParameterRepository.deleteById(id);
        return new MessageDto("AnalysisParameter with ID "+id+" deleted");
    }

    @Override
    public AnalysisParameterDto getAnalysisParameter(Long id) {
        return analysisParameterMapper.toDto(analysisParameterRepository.findAnalysisParameterById(id));
    }
}
