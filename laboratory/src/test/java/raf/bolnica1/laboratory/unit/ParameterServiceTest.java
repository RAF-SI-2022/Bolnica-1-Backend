package raf.bolnica1.laboratory.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.laboratory.domain.lab.Parameter;
import raf.bolnica1.laboratory.dto.lab.parameter.ParameterDto;
import raf.bolnica1.laboratory.mappers.ParameterMapper;
import raf.bolnica1.laboratory.repository.ParameterRepository;
import raf.bolnica1.laboratory.services.ParameterService;
import raf.bolnica1.laboratory.services.impl.ParameterServiceImpl;
import raf.bolnica1.laboratory.util.dataGenerators.classes.dto.parameter.ParameterDtoGenerator;
import raf.bolnica1.laboratory.validation.ClassJsonComparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParameterServiceTest {

    private ParameterDtoGenerator parameterDtoGenerator=ParameterDtoGenerator.getInstance();
    private ClassJsonComparator classJsonComparator=ClassJsonComparator.getInstance();

    private ParameterRepository parameterRepository;
    private ParameterMapper parameterMapper;

    private ParameterService parameterService;


    @BeforeEach
    public void prepare(){
        parameterRepository=mock(ParameterRepository.class);
        parameterMapper=new ParameterMapper();
        parameterService=new ParameterServiceImpl(parameterRepository,parameterMapper);
    }


    @Test
    public void createParameterTest(){

        ParameterDto parameterDto=parameterDtoGenerator.getParameterDto();

        Parameter parameter=parameterMapper.toEntity(parameterDto);
        parameter.setId(3L);
        given(parameterRepository.save(any())).willReturn(parameter);

        ParameterDto ret=parameterService.createParameter(parameterDto);

        parameterDto.setId(ret.getId());
        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,parameterDto));

        ArgumentCaptor<Parameter> parameterArgumentCaptor=ArgumentCaptor.forClass(Parameter.class);
        verify(parameterRepository).save(parameterArgumentCaptor.capture());

        parameterDto.setId(null);
        Assertions.assertTrue(classJsonComparator.compareCommonFields(parameterArgumentCaptor.getValue(),parameterDto));
    }


    @Test
    public void updateParameterTest(){

        given(parameterRepository.findParameterById(3L)).willReturn(new Parameter());

        ParameterDto parameterDto=parameterDtoGenerator.getParameterDto();
        parameterDto.setId(3L);

        Parameter parameter=parameterMapper.toEntity(parameterDto);
        given(parameterRepository.save(any())).willReturn(parameter);

        ParameterDto ret=parameterService.updateParameter(parameterDto);

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,parameterDto));

        ArgumentCaptor<Parameter> parameterArgumentCaptor=ArgumentCaptor.forClass(Parameter.class);
        verify(parameterRepository).save(parameterArgumentCaptor.capture());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(parameterArgumentCaptor.getValue(),parameterDto));

    }


    @Test
    public void deleteParameterTest(){

        Long id=3L;
        parameterService.deleteParameter(id);
        verify(parameterRepository).deleteById(id);

    }


    @Test
    public void getParameterTest(){

        ParameterDto parameterDto=parameterDtoGenerator.getParameterDto();
        parameterDto.setId(3L);

        Parameter parameter=parameterMapper.toEntity(parameterDto);

        given(parameterRepository.findParameterById(parameterDto.getId())).willReturn(parameter);

        ParameterDto ret=parameterService.getParameter(parameterDto.getId());

        Assertions.assertTrue(classJsonComparator.compareCommonFields(ret,parameterDto));

    }

}
