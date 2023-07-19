package raf.bolnica1.employees.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.employees.domain.Shift;
import raf.bolnica1.employees.dto.shift.ShiftCreateDto;
import raf.bolnica1.employees.dto.shift.ShiftDto;
import raf.bolnica1.employees.mappers.ShiftMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.ShiftRepository;
import raf.bolnica1.employees.repository.ShiftScheduleRepository;
import raf.bolnica1.employees.services.impl.ShiftServiceImpl;
import raf.bolnica1.employees.services.ShiftService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {
    private ShiftRepository shiftRepository;
    private ShiftScheduleRepository shiftScheduleRepository;
    private ShiftMapper shiftMapper;
    private EmployeeRepository employeeRepository;
    private ShiftService shiftService;
    @BeforeEach
    public void prepare(){
       this.shiftRepository=mock(ShiftRepository.class);
       this.shiftScheduleRepository=mock(ShiftScheduleRepository.class);
       this.employeeRepository=mock(EmployeeRepository.class);
       this.shiftMapper=new ShiftMapper(shiftRepository,employeeRepository);
       this.shiftService=new ShiftServiceImpl(shiftRepository,shiftScheduleRepository,shiftMapper);
    }

    @Test
    public void createShiftTest() {

       /* // Arrange
        ShiftCreateDto shiftCreateDto = new ShiftCreateDto();
        // Set properties for the shiftCreateDto if needed

        Shift shift = new Shift();
        // Set properties for the shift entity if needed

        when(shiftRepository.findByShiftNumAndActive(any(), anyBoolean())).thenReturn(null);
        when(shiftMapper.dtoToEntity(any())).thenReturn(shift);
        when(shiftRepository.save(any())).thenReturn(shift);
        when(shiftMapper.entityToDto(any())).thenReturn(shiftCreateDto);

        // Act
        ShiftDto result = shiftService.createShift(shiftCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals(shiftCreateDto, result);
        verify(shiftRepository, times(1)).findByShiftNumAndActive(any(), anyBoolean());
        verify(shiftRepository, times(1)).save(any());
        verify(shiftMapper, times(1)).dtoToEntity(any());
        verify(shiftMapper, times(1)).entityToDto(any());*/
    }



}
