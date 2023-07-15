package raf.bolnica1.employees.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllShiftsDto {

    private List<ShiftDto> shifts;
}
