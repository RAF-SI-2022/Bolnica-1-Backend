package raf.bolnica1.employees.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Shift;
import raf.bolnica1.employees.domain.ShiftSchedule;
import raf.bolnica1.employees.dto.shift.*;
import raf.bolnica1.employees.mappers.ShiftMapper;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.ShiftRepository;
import raf.bolnica1.employees.repository.ShiftScheduleRepository;
import raf.bolnica1.employees.services.impl.ShiftServiceImpl;
import raf.bolnica1.employees.services.ShiftService;
import raf.bolnica1.employees.validation.ClassJsonComparator;

import java.sql.Date;
import java.sql.Time;
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

        Employee employee=new Employee();
        employee.setLbz("lbz");
        employee.setName("ime");
        employee.setSurname("prezime");

        when(employeeRepository.findByLbz("lbz")).thenReturn(Optional.of(employee));

        ShiftScheduleCreateDto shiftScheduleCreateDto=new ShiftScheduleCreateDto();
        shiftScheduleCreateDto.setLbz("lbz");
        shiftScheduleCreateDto.setDate(new Date(System.currentTimeMillis()));
        shiftScheduleCreateDto.setShiftId(1L);

        Shift shift=new Shift();
        shift.setShiftNum(1);
        when(shiftRepository.findById(1l)).thenReturn(Optional.of(shift));


        ShiftSchedule shiftSchedule=new ShiftSchedule();
        shiftSchedule.setDate(shiftScheduleCreateDto.getDate());
        shiftSchedule.setEmployee(employee);
        shiftSchedule.setShift(shift);

        when(shiftScheduleRepository.save(any())).thenReturn(shiftSchedule);

        ShiftScheduleDto result=shiftService.createShiftSchedule(shiftScheduleCreateDto);

        assertTrue(classJsonComparator.compareCommonFields(result,shiftScheduleCreateDto));

    }


    @Test
    public void removeShiftScheduleTest(){

        ShiftSchedule shiftSchedule=new ShiftSchedule();
        when(shiftScheduleRepository.findById(1L)).thenReturn(Optional.of(shiftSchedule));

        shiftService.removeShiftSchedule(1L);

        verify(shiftScheduleRepository,times(1)).delete(shiftSchedule);
    }


    @Test
    public void scheduleAllTest(){

        Employee employee=new Employee();
        employee.setLbz("lbz");
        employee.setName("ime");
        employee.setSurname("prezime");

        Shift shift=new Shift();
        shift.setShiftNum(1);


        ShiftSchedule shiftSchedule=new ShiftSchedule();
        shiftSchedule.setDate(new Date(System.currentTimeMillis()));
        shiftSchedule.setEmployee(employee);
        shiftSchedule.setShift(shift);

        List<ShiftSchedule>lista=new ArrayList<>();
        lista.add(shiftSchedule);
        Page<ShiftSchedule> page=new PageImpl<>(lista);

        Date startDate=new Date(System.currentTimeMillis());
        Date endDate=new Date(System.currentTimeMillis());

        when(shiftScheduleRepository.findByDate(eq(startDate),eq(endDate),any())).thenReturn(page);

        Page<ShiftScheduleDto> result=shiftService.scheduleAll(startDate,endDate,0,10);


        assertTrue(page.getContent().get(0).getDate().equals(result.getContent().get(0).getDate()));


    }

    @Test
    public void scheduleEmployeeTest(){

        Employee employee=new Employee();
        employee.setLbz("lbz");
        employee.setName("ime");
        employee.setSurname("prezime");

        Shift shift=new Shift();
        shift.setShiftNum(1);


        ShiftSchedule shiftSchedule=new ShiftSchedule();
        shiftSchedule.setDate(new Date(System.currentTimeMillis()));
        shiftSchedule.setEmployee(employee);
        shiftSchedule.setShift(shift);

        List<ShiftSchedule>lista=new ArrayList<>();
        lista.add(shiftSchedule);
        Page<ShiftSchedule> page=new PageImpl<>(lista);

        Date startDate=new Date(System.currentTimeMillis());
        Date endDate=new Date(System.currentTimeMillis());

        when(shiftScheduleRepository.findByEmployeeAndDate(eq("lbz"),eq(startDate),eq(endDate),any())).thenReturn(page);

        Page<ShiftScheduleDto> result=shiftService.scheduleEmployee("lbz",startDate,endDate,0,10);


        assertTrue(page.getContent().get(0).getDate().equals(result.getContent().get(0).getDate()));


    }


    @Test
    public void isDoctorWorkingTest(){

        String time="00:00:01";


    }

    @Test
    public void testIsDoctorWorking() {
        // Given
        String lbz = "some_lbz";
        String time = "08:00:00"; // Assuming "time" format is "HH:mm:ss"
        Date date = new Date(System.currentTimeMillis());

        Time time1 = Time.valueOf(time);
        Shift shift = new Shift(); // Assuming Shift constructor takes shiftNum and time
        shift.setShiftNum(1);
        shift.setStartTime(time1);

        // Create a ShiftSchedule object with the same shift as the one found above
        ShiftSchedule shiftSchedule = new ShiftSchedule();
        shiftSchedule.setShift(shift);

        // When
        when(shiftRepository.findByTime(time1)).thenReturn(shift);
        when(shiftScheduleRepository.findForEmployee(lbz, date)).thenReturn(Optional.of(shiftSchedule));

        // Execute the method to be tested
        Boolean result = shiftService.isDoctorWorking(lbz, time, date);

        // Then
        assertTrue(result);

        verify(shiftRepository, times(1)).findByTime(time1);
        verify(shiftScheduleRepository, times(1)).findForEmployee(lbz, date);
    }

}
