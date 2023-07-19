package raf.bolnica1.employees.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.bolnica1.employees.domain.Shift;
import raf.bolnica1.employees.domain.ShiftSchedule;
import raf.bolnica1.employees.dto.employee.EmployeeMessageDto;
import raf.bolnica1.employees.dto.shift.*;
import raf.bolnica1.employees.mappers.ShiftMapper;
import raf.bolnica1.employees.repository.ShiftRepository;
import raf.bolnica1.employees.repository.ShiftScheduleRepository;
import raf.bolnica1.employees.services.ShiftService;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private ShiftRepository shiftRepository;
    private ShiftScheduleRepository shiftScheduleRepository;
    private ShiftMapper shiftMapper;

    @Override
    public ShiftDto createShift(ShiftCreateDto shiftCreateDto) {
        Shift shift = shiftRepository.findByShiftNumAndActive(shiftCreateDto.getShift(), true).orElse(null);
        if(shift != null){
            shift.setActive(false);
            shiftRepository.save(shift);
        }
        Shift newShift = shiftMapper.dtoToEntity(shiftCreateDto);
        newShift = shiftRepository.save(newShift);
        return shiftMapper.entityToDto(newShift);
    }

    @Override
    public ShiftDto getShift(int num) {
        Shift shift = shiftRepository.findByShiftNumAndActive(num, true).orElseThrow(() -> new RuntimeException(String.format("No shift with number %d", num)));

        return shiftMapper.entityToDto(shift);
    }

    @Override
    public AllShiftsDto all() {
        List<Shift> shifts = shiftRepository.findByActive(true);
        List<ShiftDto> shiftDtos = new ArrayList<>();
        for(Shift shift : shifts){
            shiftDtos.add(shiftMapper.entityToDto(shift));
        }
        return new AllShiftsDto(shiftDtos);
    }

    @Override
    public ShiftScheduleDto createShiftSchedule(ShiftScheduleCreateDto shiftScheduleCreateDto) {
        ShiftSchedule shiftSchedule = shiftMapper.dtoToEntitySchedule(shiftScheduleCreateDto);

        shiftSchedule = shiftScheduleRepository.save(shiftSchedule);

        return shiftMapper.entityToDtoSchedule(shiftSchedule);
    }

    @Override
    public EmployeeMessageDto removeShiftSchedule(Long id) {
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findById(id).orElseThrow(() -> new RuntimeException());

        shiftScheduleRepository.delete(shiftSchedule);
        return new EmployeeMessageDto("Uspesno uklonjeno!");
    }

    @Override
    public Page<ShiftScheduleDto> scheduleAll(Date startDate, Date endDate, int page, int size) {

        Pageable sortedByDate = PageRequest.of(page, size, Sort.by("date"));
        Page<ShiftSchedule> shiftSchedules=shiftScheduleRepository.findByDate(startDate,endDate,sortedByDate);

        return shiftSchedules.map(ShiftMapper::entityToDtoSchedule);
    }

    @Override
    public Page<ShiftScheduleDto> scheduleEmployee(String lbz, Date startDate, Date endDate, int page, int size) {

        Pageable sortedByDate = PageRequest.of(page, size, Sort.by("date"));
        Page<ShiftSchedule> shiftSchedules=shiftScheduleRepository.findByEmployeeAndDate(lbz,startDate,endDate,sortedByDate);

        return shiftSchedules.map(ShiftMapper::entityToDtoSchedule);
    }

    @Override
    public Boolean isDoctorWorking(String lbz, String time, Date date) {
        Time time1 = Time.valueOf(time);
        Shift shift = shiftRepository.findByTime(time1);
        ShiftSchedule shiftSchedule = shiftScheduleRepository.findForEmployee(lbz, date).orElse(null);

        System.out.println(shiftSchedule.getShift().getShiftNum());
        if(shiftSchedule == null)
            return shift.getShiftNum() == 0;

        return shiftSchedule.getShift().getShiftNum() == shift.getShiftNum();
    }
}
