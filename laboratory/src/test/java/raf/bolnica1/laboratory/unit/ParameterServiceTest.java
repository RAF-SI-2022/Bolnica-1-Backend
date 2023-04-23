package raf.bolnica1.laboratory.unit;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import raf.bolnica1.laboratory.mappers.ParameterMapper;
import raf.bolnica1.laboratory.repository.ParameterRepository;

@SpringBootTest
public class ParameterServiceTest {

    @Mock
    private ParameterRepository parameterRepository;
    @Mock
    private ParameterMapper parameterMapper;

}
