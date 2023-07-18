package raf.bolnica1.employees.services;

import org.springframework.data.domain.Page;
import raf.bolnica1.employees.dto.employee.EmployeeMessageDto;
import raf.bolnica1.employees.dto.shift.*;

import java.sql.Date;

public interface ShiftService {

    ShiftDto createShift(ShiftCreateDto shiftCreateDto);

    ShiftDto getShift(int num);

    AllShiftsDto all();

    ShiftScheduleDto createShiftSchedule(ShiftScheduleCreateDto shiftScheduleCreateDto);

    EmployeeMessageDto removeShiftSchedule(Long id);

    Page<ShiftScheduleDto> scheduleAll(Date startDate, Date endDate, int page, int size);

    Page<ShiftScheduleDto> scheduleEmployee(String lbz, Date startDate, Date endDate, int page, int size);

    Boolean isDoctorWorking(String lbz, String time, Date date);
}
