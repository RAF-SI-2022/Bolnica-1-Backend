package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import raf.bolnica1.laboratory.domain.lab.AnalysisParameter;
import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.analysisParameter.AnalysisParameterDto;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.AnalysisParameterMapper;
import raf.bolnica1.laboratory.mappers.ParameterMapper;
import raf.bolnica1.laboratory.repository.AnalysisParameterRepository;
import raf.bolnica1.laboratory.services.lab.impl.AnalysisParameterServiceImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.*;

@ExtendWith(MockitoExtension.class)
public class AnalysisParameterServiceTest {

    @Mock
    private AnalysisParameterRepository analysisParameterRepository;
    @Mock
    private AnalysisParameterMapper analysisParameterMapper;
    @Mock
    private ParameterMapper parameterMapper;
    @InjectMocks
    private AnalysisParameterServiceImpl analysisParameterService;

    @Test
    public void testCreateAnalysisParameter() {
        AnalysisParameterDto inputDto = createAnalysisParameterDto();
        AnalysisParameter expectedEntity = createAnalysisParameter();
        AnalysisParameter savedEntity = createAnalysisParameter();

        when(analysisParameterMapper.toEntity(any(AnalysisParameterDto.class))).thenReturn(expectedEntity);
        when(analysisParameterRepository.save(expectedEntity)).thenReturn(savedEntity);
        when(analysisParameterMapper.toDto(savedEntity)).thenReturn(inputDto);

        AnalysisParameterDto result = analysisParameterService.createAnalysisParameter(inputDto);

        assertEquals(inputDto, result);
        verify(analysisParameterMapper, times(1)).toEntity(inputDto);
        verify(analysisParameterRepository, times(1)).save(expectedEntity);
        verify(analysisParameterMapper, times(1)).toDto(savedEntity);
    }

    @Test
    public void testUpdateAnalysisParameter() {
        AnalysisParameterDto inputDto = createAnalysisParameterDto();
        AnalysisParameter expectedEntity = createAnalysisParameter();
        AnalysisParameter savedEntity = createAnalysisParameter();

        when(analysisParameterRepository.findAnalysisParameterById(inputDto.getId())).thenReturn(expectedEntity);
        when(analysisParameterMapper.toEntity(inputDto)).thenReturn(expectedEntity);
        when(analysisParameterRepository.save(expectedEntity)).thenReturn(savedEntity);
        when(analysisParameterMapper.toDto(savedEntity)).thenReturn(inputDto);

        AnalysisParameterDto result = analysisParameterService.updateAnalysisParameter(inputDto);

        assertEquals(inputDto, result);
        verify(analysisParameterRepository, times(1)).findAnalysisParameterById(inputDto.getId());
        verify(analysisParameterMapper, times(1)).toEntity(inputDto);
        verify(analysisParameterRepository, times(1)).save(expectedEntity);
        verify(analysisParameterMapper, times(1)).toDto(savedEntity);
    }

    @Test
    public void testDeleteAnalysisParameter() {
        Long id = 1L;
        String expectedMessage = "AnalysisParameter with ID " + id + " deleted";
        MessageDto expectedDto = new MessageDto(expectedMessage);

        MessageDto result = analysisParameterService.deleteAnalysisParameter(id);

        assertEquals(expectedDto.getMessage(), result.getMessage());
        verify(analysisParameterRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetAnalysisParameter() {
        Long id = 1L;
        AnalysisParameter entity = createAnalysisParameter();
        AnalysisParameterDto expectedDto = createAnalysisParameterDto();

        when(analysisParameterRepository.findAnalysisParameterById(id)).thenReturn(entity);
        when(analysisParameterMapper.toDto(entity)).thenReturn(expectedDto);

        AnalysisParameterDto result = analysisParameterService.getAnalysisParameter(id);

        assertEquals(expectedDto, result);
        verify(analysisParameterRepository, times(1)).findAnalysisParameterById(id);
        verify(analysisParameterMapper, times(1)).toDto(entity);
    }

    @Test
    public void testGetParametersByAnalysisId() {
        Long id = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Parameter> parameters = new PageImpl<>(Collections.singletonList(createParameter()));
        Page<ParameterDto> expectedDto = new PageImpl<>(Collections.singletonList(createParameterDto()));

        when(analysisParameterRepository.findParametersByAnalysisId(pageable, id)).thenReturn(parameters);
        when(parameterMapper.toDto(any(Parameter.class))).thenReturn(expectedDto.getContent().get(0));

        Page<ParameterDto> result = analysisParameterService.getParametersByAnalysisId(id, page, size);

        assertEquals(expectedDto, result);
        verify(analysisParameterRepository, times(1)).findParametersByAnalysisId(pageable, id);
        verify(parameterMapper, times(1)).toDto(any(Parameter.class));
    }

}
