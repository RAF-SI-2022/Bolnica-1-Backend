package raf.bolnica1.employees.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftDto {
    private Long id;
    private int shift;
    private String startTime;
    private String endTime;
}
