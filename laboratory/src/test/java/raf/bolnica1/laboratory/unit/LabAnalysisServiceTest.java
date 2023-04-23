package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.laboratory.domain.lab.LabAnalysis;
import raf.bolnica1.laboratory.dto.lab.labAnalysis.LabAnalysisDto;
import raf.bolnica1.laboratory.dto.response.MessageDto;
import raf.bolnica1.laboratory.mappers.LabAnalysisMapper;
import raf.bolnica1.laboratory.repository.LabAnalysisRepository;
import raf.bolnica1.laboratory.services.lab.impl.LabAnalysisServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.createLabAnalysisEntity;
import static raf.bolnica1.laboratory.unit.data.UnitTestDataGen.createLabAnalysisDto;

@ExtendWith(MockitoExtension.class)
public class LabAnalysisServiceTest {

    @Mock
    private LabAnalysisRepository labAnalysisRepository;
    @Mock
    private LabAnalysisMapper labAnalysisMapper;
    @InjectMocks
    private LabAnalysisServiceImpl labAnalysisService;

    @Test
    public void testCreateLabAnalysis() {
        LabAnalysisDto inputDto = createLabAnalysisDto();
        LabAnalysis expectedEntity = createLabAnalysisEntity();
        LabAnalysis savedEntity = createLabAnalysisEntity();

        when(labAnalysisMapper.toEntity(inputDto)).thenReturn(expectedEntity);
        when(labAnalysisRepository.save(expectedEntity)).thenReturn(savedEntity);
        when(labAnalysisMapper.toDto(savedEntity)).thenReturn(inputDto);

        LabAnalysisDto result = labAnalysisService.createLabAnalysis(inputDto);

        assertEquals(inputDto, result);
        verify(labAnalysisMapper, times(1)).toEntity(inputDto);
        verify(labAnalysisRepository, times(1)).save(expectedEntity);
        verify(labAnalysisMapper, times(1)).toDto(savedEntity);
    }

    @Test
    public void testUpdateLabAnalysis() {
        LabAnalysisDto inputDto = createLabAnalysisDto();
        LabAnalysis expectedEntity = createLabAnalysisEntity();
        LabAnalysis savedEntity = createLabAnalysisEntity();

        when(labAnalysisRepository.findLabAnalysisById(inputDto.getId())).thenReturn(expectedEntity);
        when(labAnalysisMapper.toEntity(inputDto)).thenReturn(expectedEntity);
        when(labAnalysisRepository.save(expectedEntity)).thenReturn(savedEntity);
        when(labAnalysisMapper.toDto(savedEntity)).thenReturn(inputDto);

        LabAnalysisDto result = labAnalysisService.updateLabAnalysis(inputDto);

        assertEquals(inputDto, result);
        verify(labAnalysisRepository, times(1)).findLabAnalysisById(inputDto.getId());
        verify(labAnalysisMapper, times(1)).toEntity(inputDto);
        verify(labAnalysisRepository, times(1)).save(expectedEntity);
        verify(labAnalysisMapper, times(1)).toDto(savedEntity);
    }

    @Test
    public void testDeleteLabAnalysis() {
        Long id = 1L;
        String expectedMessage = "LabAnalysis with ID " + id.toString() + " deleted";
        MessageDto expectedDto = new MessageDto(expectedMessage);

        MessageDto result = labAnalysisService.deleteLabAnalysis(id);

        assertEquals(expectedDto.getMessage(), result.getMessage());
        verify(labAnalysisRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetLabAnalysis() {
        Long id = 1L;
        LabAnalysis entity = createLabAnalysisEntity();
        LabAnalysisDto expectedDto = createLabAnalysisDto();

        when(labAnalysisRepository.findLabAnalysisById(id)).thenReturn(entity);
        when(labAnalysisMapper.toDto(entity)).thenReturn(expectedDto);

        LabAnalysisDto result = labAnalysisService.getLabAnalysis(id);

        assertEquals(expectedDto, result);
        verify(labAnalysisRepository, times(1)).findLabAnalysisById(id);
        verify(labAnalysisMapper, times(1)).toDto(entity);
    }

    @Test
    public void testGetAllLabAnalysis() {
        List<LabAnalysis> entities = Arrays.asList(createLabAnalysisEntity(), createLabAnalysisEntity());
        List<LabAnalysisDto> expectedDtos = Arrays.asList(createLabAnalysisDto(), createLabAnalysisDto());

        when(labAnalysisRepository.findAll()).thenReturn(entities);
        when(labAnalysisMapper.toDto(entities)).thenReturn(expectedDtos);

        List<LabAnalysisDto> result = labAnalysisService.getAllLabAnalysis();

        assertEquals(expectedDtos, result);
        verify(labAnalysisRepository, times(1)).findAll();
        verify(labAnalysisMapper, times(1)).toDto(entities);
    }

}
