package raf.bolnica1.employees.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.domain.Shift;
import raf.bolnica1.employees.domain.ShiftSchedule;
import raf.bolnica1.employees.dto.shift.ShiftCreateDto;
import raf.bolnica1.employees.dto.shift.ShiftDto;
import raf.bolnica1.employees.dto.shift.ShiftScheduleCreateDto;
import raf.bolnica1.employees.dto.shift.ShiftScheduleDto;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.repository.ShiftRepository;

import java.sql.Time;

@Component
@AllArgsConstructor
public class ShiftMapper {

    private ShiftRepository shiftRepository;
    private EmployeeRepository employeeRepository;


    public Shift dtoToEntity(ShiftCreateDto shiftDto){
        Shift shift = new Shift();
        shift.setShiftNum(shiftDto.getShift());
        shift.setActive(true);
        shift.setStartTime(Time.valueOf(shiftDto.getStartTime()));
        shift.setEndTime(Time.valueOf(shiftDto.getEndTime()));

        return shift;
    }

    public ShiftDto entityToDto(Shift shift){
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setId(shift.getId());
        shiftDto.setShift(shift.getShiftNum());
        shiftDto.setStartTime(String.valueOf(shift.getStartTime()));
        shiftDto.setEndTime(String.valueOf(shift.getEndTime()));

        return shiftDto;
    }

    public ShiftSchedule dtoToEntitySchedule(ShiftScheduleCreateDto shiftScheduleCreateDto){
        ShiftSchedule shiftSchedule = new ShiftSchedule();
        Shift shift = shiftRepository.findById(shiftScheduleCreateDto.getShiftId()).orElseThrow(() -> new RuntimeException());
        shiftSchedule.setShift(shift);
        Employee employee = employeeRepository.findByLbz(shiftScheduleCreateDto.getLbz()).orElseThrow(() -> new RuntimeException());
        shiftSchedule.setEmployee(employee);
        shiftSchedule.setDate(shiftScheduleCreateDto.getDate());

        return shiftSchedule;
    }

    public static ShiftScheduleDto entityToDtoSchedule(ShiftSchedule shiftSchedule){
        ShiftScheduleDto shiftScheduleDto = new ShiftScheduleDto();
        shiftScheduleDto.setId(shiftSchedule.getId());
        shiftScheduleDto.setShift(shiftSchedule.getShift().getShiftNum());
        shiftScheduleDto.setLbz(shiftSchedule.getEmployee().getLbz());
        shiftScheduleDto.setDoctor(shiftSchedule.getEmployee().getName() + " " + shiftSchedule.getEmployee().getSurname());
        shiftScheduleDto.setDate(shiftSchedule.getDate());

        return shiftScheduleDto;
    }
}
