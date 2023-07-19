package raf.bolnica1.employees.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raf.bolnica1.employees.domain.Shift;
import raf.bolnica1.employees.dto.shift.AllShiftsDto;
import raf.bolnica1.employees.dto.shift.ShiftCreateDto;
import raf.bolnica1.employees.dto.shift.ShiftDto;
import raf.bolnica1.employees.mappers.ShiftMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.ShiftRepository;
import raf.bolnica1.employees.repository.ShiftScheduleRepository;
import raf.bolnica1.employees.services.impl.ShiftServiceImpl;
import raf.bolnica1.employees.services.ShiftService;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private ClassJsonComparator classJsonComparator = ClassJsonComparator.getInstance();

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

        // Arrange
        ShiftCreateDto shiftCreateDto = new ShiftCreateDto();
        shiftCreateDto.setShift(0);
        shiftCreateDto.setStartTime("00:00:01");
        shiftCreateDto.setEndTime("00:00:02");

        Shift shift=shiftMapper.dtoToEntity(shiftCreateDto);
        when(shiftRepository.findByShiftNumAndActive(shiftCreateDto.getShift(),true) ).thenReturn(Optional.of(shift));
        when(shiftRepository.save(any())).thenReturn(shift);

        ShiftDto result = shiftService.createShift(shiftCreateDto);


        assertEquals(false, shift.isActive());
        verify(shiftRepository, times(1)).save(shift);
        assertEquals(shift.getShiftNum(),result.getShift());
        assertTrue(classJsonComparator.compareCommonFields(result,shiftCreateDto));

    }


    @Test
    public void getShiftTest(){

        ShiftCreateDto shiftCreateDto = new ShiftCreateDto();
        shiftCreateDto.setShift(0);
        shiftCreateDto.setStartTime("00:00:01");
        shiftCreateDto.setEndTime("00:00:02");

        Shift shift=shiftMapper.dtoToEntity(shiftCreateDto);
        when(shiftRepository.findByShiftNumAndActive(shiftCreateDto.getShift(),true) ).thenReturn(Optional.of(shift));

        ShiftDto result=shiftService.getShift(0);

        assertTrue(classJsonComparator.compareCommonFields(result,shiftCreateDto));

    }


    @Test
    public void allTest(){

        ShiftCreateDto shiftCreateDto = new ShiftCreateDto();
        shiftCreateDto.setShift(0);
        shiftCreateDto.setStartTime("00:00:01");
        shiftCreateDto.setEndTime("00:00:02");

        Shift shift=shiftMapper.dtoToEntity(shiftCreateDto);
        List<Shift> shiftList=new ArrayList<>();
        shiftList.add(shift);

        when(shiftRepository.findByActive(true)).thenReturn(shiftList);

        AllShiftsDto result=shiftService.all();

        assertTrue(classJsonComparator.compareCommonFields(shiftList.get(0),result.getShifts().get(0)));

    }


    @Test
    public void createShiftScheduleTest(){



    }

}
