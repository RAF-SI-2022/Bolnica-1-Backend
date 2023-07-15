package raf.bolnica1.employees.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftCreateDto {
    private int shift;
    private String startTime;
    private String endTime;
}
